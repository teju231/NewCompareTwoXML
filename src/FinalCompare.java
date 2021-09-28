import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FinalCompare {
        public static Boolean finalFlag = true;
        public static void main(String args[]){
            try{
                DocumentBuilderFactory dBfactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builderSource = dBfactory.newDocumentBuilder();
                // Fetch XML File
                Document documentSource = builderSource.parse("D:/Others/Software/JavaCodingQuestions/source.xml");
                Document documentTarget = builderSource.parse("D:/Others/Software/JavaCodingQuestions/target.xml");
                documentTarget.getDocumentElement().normalize();

                List<String> messages = checkallTransaction(documentSource, documentTarget, "ns0:transaction");
                if(finalFlag != true){
                    System.out.println("==================================");
                    System.out.println("Fail");
                    System.out.println("==================================");
                    if(messages != null){
                        System.out.println("Total Number of Error/s : "+messages.size());
                        System.out.println("----------------------------------");
                        for(String errorMsg : messages){
                            System.out.println(""+errorMsg);
                        }
                    }
                }else{
                    System.out.println("==================================");
                    System.out.println("Pass");
                    System.out.println("==================================");
                }
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    public static List<String> checkallTransaction(Document sourceDoc, Document targetDoc, String tagName) {
        List<String> errorMessage = new ArrayList<String>();
        try {
            //Source Transaction Block
            NodeList nListSource = sourceDoc.getElementsByTagName(tagName);
            //Target Transaction Block
            NodeList nListTarget = targetDoc.getElementsByTagName(tagName);

            //---------------------------Source For Parent-----------------------------------
            for (int i = 0; i < nListSource.getLength(); i++) {
                Node sourceNode = nListSource.item(i);
                Integer countTransactionSource = 0;
                if (sourceNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element sourceElement = (Element) sourceNode;
                    NodeList transDetails = sourceElement.getChildNodes();

                    for (int k = 0; k < nListTarget.getLength(); k++) {
                        Node targetNode = nListTarget.item(k);
                        if (targetNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element targetElement = (Element) targetNode;
                            NodeList transTarDetails = targetElement.getChildNodes();
                            if (sourceElement.getElementsByTagName("ns0:description").item(0).getTextContent().equals(targetElement.getElementsByTagName("ns0:description").item(0).getTextContent())) {
                                Integer countTags = 0;
                                for (int j = 0; j < transDetails.getLength(); j++) {
                                    Node childSourceNode = transDetails.item(j);

                                    if (childSourceNode.getNodeType() == Node.ELEMENT_NODE) {
                                        Element childElement = (Element) childSourceNode;
                                        //System.out.println(" Source " + childElement.getTagName() + " : " + childElement.getTextContent());
                                        for (int m = 0; m < transTarDetails.getLength(); m++) {
                                            Node childTargetNode = transTarDetails.item(m);
                                            Boolean mismatchFlag = false;
                                            if (childTargetNode.getNodeType() == Node.ELEMENT_NODE) {
                                                Element childTargetElement = (Element) childTargetNode;
                                                if (childElement.getTagName().equals(childTargetElement.getTagName())) {
                                                    if (!childElement.getTextContent().equals(childTargetElement.getTextContent())) {
                                                        mismatchFlag = true;
                                                        countTags++;
                                                    }
                                                }
                                                if (mismatchFlag != false) {
                                                    finalFlag = false;
                                                    errorMessage.add(" Transaction mismatch tag : Expected value '" + childElement.getTextContent() + "' but actual is '" + childTargetElement.getTextContent() + "' comparing.. <"+childElement.getTagName()+">"+childElement.getTextContent()+"</"+childElement.getTagName()+">  ----at "+ getXPath(transTarDetails.item(m)) );
                                                }
                                            }
                                        }
                                    }
                                }
                                if (countTags == 0) {
                                    countTransactionSource++;
                                }
                            }
                        }
                    }
                    if (countTransactionSource == 0) {
                        finalFlag = false;
                        errorMessage.add(" Transaction Not Found : "+sourceElement.getElementsByTagName("ns0:description").item(0).getTextContent());
                    } else if (countTransactionSource == 1) {
                        finalFlag = true;
                    } else if (countTransactionSource > 1) {
                        finalFlag = false;
                        errorMessage.add(" Excess Transaction Found with Description : "+sourceElement.getElementsByTagName("ns0:description").item(0).getTextContent() );
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return errorMessage;
    }
    //To get Path of specific Node (XML tag)
    private static String getXPath(Node node) {
        if(node == null) {
            throw new IllegalArgumentException("Node cannot be null");
        }
        StringBuilder pathBuilder = new StringBuilder("/");
        pathBuilder.append(node.getNodeName());

        Node currentNode = node;

        if(currentNode.getNodeType() != Node.DOCUMENT_NODE) {
            while (currentNode.getParentNode() != null) {
                currentNode = currentNode.getParentNode();

                if(currentNode.getNodeType() == Node.DOCUMENT_NODE) {
                    break;
                } else if(getIndexOfArrayNode(currentNode) != null) {
                    pathBuilder.insert(0, "/" + currentNode.getNodeName() + "[" + getIndexOfArrayNode(currentNode) + "]");
                } else {
                    pathBuilder.insert(0, "/" + currentNode.getNodeName());
                }
            }
        }
        return pathBuilder.toString();
    }
    private static Integer getIndexOfArrayNode(Node node) {
        if(isArrayNode(node)) {
            int leftCount = 0;
            Node currentNode = node.getPreviousSibling();
            while(currentNode != null) {
                leftCount++;
                currentNode = currentNode.getPreviousSibling();
            }
            return leftCount;
        } else {
            return null;
        }
    }
    private static boolean isArrayNode(Node node) {
        if (node.getNextSibling() == null && node.getPreviousSibling() == null) {
            // Node has no siblings
            return false;
        } else {
            // Check if node siblings are of the same name. If so, then we are inside an array.
            return (node.getNextSibling() != null && node.getNextSibling().getNodeName().equalsIgnoreCase(node.getNodeName()))
                    || (node.getPreviousSibling() != null && node.getPreviousSibling().getNodeName().equalsIgnoreCase(node.getNodeName()));
        }
    }


    }

