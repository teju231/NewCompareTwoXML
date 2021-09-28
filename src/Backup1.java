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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Backup1 {

    public static void main(String args[]){
        try{
            DocumentBuilderFactory dBfactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builderSource = dBfactory.newDocumentBuilder();
            // Fetch XML File
            Document documentSource = builderSource.parse("D:/Others/Software/JavaCodingQuestions/source.xml");
            Document documentTarget = builderSource.parse("D:/Others/Software/JavaCodingQuestions/target.xml");
            documentTarget.getDocumentElement().normalize();
                /*NodeList allListTarget = documentTarget.getElementsByTagName("*");
                NodeList allListSource = documentSource.getElementsByTagName("*");
                System.out.println("Source Tags :*********");
                for (int i = 0; i < allListSource.getLength(); i++) {
                    Node allSourceNode = allListSource.item(i);
                    Element sourceElement = (Element) allSourceNode;
                    System.out.println(sourceElement.getTagName());
                }*/



            //System.out.println("Target Tags :*********"+documentTarget.getElementsByTagName("*"));

            NodeList nListTarget = documentTarget.getElementsByTagName("ns0:transaction");
            //System.out.println("-----All list is : "+documentTarget.getElementsByTagName("*"));
            NodeList nListSource = documentSource.getElementsByTagName("ns0:transaction");
            Map<String, String> sourceMap = new HashMap<String, String>();
            List<String> sourceList = new ArrayList<String>();

            //---------------------------Source For Parent-----------------------------------
            for (int i = 0; i < nListSource.getLength(); i++) {
                Node sourceNode = nListSource.item(i);
                Integer countTransactionSource = 0;
                if(sourceNode.getNodeType() == Node.ELEMENT_NODE){
                    Element sourceElement = (Element) sourceNode;
                    //System.out.println(" Element Source----------------- "+sourceElement.getTagName()+":"+sourceElement.getTextContent());
                    NodeList transDetails = sourceElement.getChildNodes();
                    //System.out.println(" Source transDetails is : "+sourceElement.getElementsByTagName("ns0:description").item(0).getTextContent());
                               /* for (int j = 0; j < transDetails.getLength(); j++) {
                                    Node childSourceNode = transDetails.item(j);
                                    if (childSourceNode.getNodeType() == Node.ELEMENT_NODE) {
                                        Element childElement = (Element) childSourceNode;
                                        System.out.println(" Source " + childElement.getTagName() + " : " + childElement.getTextContent());

                                }
                            //System.out.println(" Map is : "+sourceMap);
                        }*/


                    for (int k = 0; k < nListTarget.getLength(); k++) {
                        Node targetNode = nListTarget.item(k);
                        if (targetNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element targetElement = (Element) targetNode;
                            NodeList transTarDetails = targetElement.getChildNodes();
                            //System.out.println(" transTarDetails is : "+transTarDetails);

                            if(sourceElement.getElementsByTagName("ns0:description").item(0).getTextContent().equals(targetElement.getElementsByTagName("ns0:description").item(0).getTextContent())){
                                //System.out.println(" Transaction Matched--------- : "+sourceElement.getElementsByTagName("ns0:description").item(0).getTextContent());
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
                                                if(childElement.getTagName().equals(childTargetElement.getTagName())){
                                                    if(childElement.getTextContent().equals(childTargetElement.getTextContent())){
                                                        //System.out.println(" Transaction mAtch Details-------------: "+childElement.getTextContent());

                                                    }else{
                                                        mismatchFlag = true;
                                                        countTags++;
                                                        // System.out.println(" Transaction Mismatch Details-------------: "+childElement.getTextContent());
                                                    }

                                                }
                                                if(mismatchFlag != false){
                                                    System.out.println(" Transaction Mismatch tags of Description is "+ sourceElement.getElementsByTagName("ns0:description").item(0).getTextContent() +"---: "+childElement.getTagName()+": Expected '"+childElement.getTextContent()+"' but actual '"+childTargetElement.getTextContent()+"'");
                                                }
                                            }

                                        }


                                    }

                                }
                                if(countTags == 0){
                                    countTransactionSource++;
                                }else{

                                }




                                //break;
                            }else{
                                //System.out.println(" Transaction Mismatched--------- : "+sourceElement.getElementsByTagName("ns0:description").item(0).getTextContent());

                            }




                        }

                    }
                    if(countTransactionSource == 0){
                        System.out.println(" Transaction Not Found in Target--------- : "+sourceElement.getElementsByTagName("ns0:description").item(0).getTextContent());
                    }else if(countTransactionSource == 1){
                        System.out.println(" Unique Transaction Found--------- : "+sourceElement.getElementsByTagName("ns0:description").item(0).getTextContent());

                    }else if(countTransactionSource > 1){
                        System.out.println(" Excess Transaction Found  "+countTransactionSource+"--------- : "+sourceElement.getElementsByTagName("ns0:description").item(0).getTextContent());
                    }
                }

            }


            //---------------------------Target Loop---------------------------------------------
               /* for (int k = 0; k < nListTarget.getLength(); k++) {
                    Node targetNode = nListTarget.item(k);
                    if (targetNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element targetElement = (Element) targetNode;

                        //System.out.println(" All targetElement----------------- " + targetElement.getAttribute("name"));
                        NodeList transTarDetails = targetElement.getChildNodes();
                        for (int m = 0; m < transTarDetails.getLength(); m++) {
                            Node childTargetNode = transTarDetails.item(m);
                            if (childTargetNode.getNodeType() == Node.ELEMENT_NODE) {
                                Element childTargetElement = (Element) childTargetNode;

                            }
                        }
                        //System.out.println(" ===================== ");
                    }
                }*/

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void checkAllElementsMatching(Document sourceDoc, Document targetDoc, String tagName){
        try {
            NodeList nListSource = sourceDoc.getElementsByTagName(tagName);
            //Target Transaction Block
            NodeList nListTarget = targetDoc.getElementsByTagName(tagName);
            for (int i = 0; i < nListSource.getLength(); i++) {
                Node sourceNode = nListSource.item(i);

                for (int k = 0; k < nListTarget.getLength(); k++) {
                    Node targetNode = nListTarget.item(i);
                    if (targetNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element targetElement = (Element) targetNode;
                        NodeList transTarDetails = targetElement.getChildNodes();


                        if (sourceNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element sourceElement = (Element) sourceNode;
                            NodeList headerSourceNodes = sourceElement.getChildNodes();
                            Integer countTags = 0;
                            for (int j = 0; j < headerSourceNodes.getLength(); j++) {
                                Node srcHeader = headerSourceNodes.item(j);
                                if (srcHeader.getNodeType() == Node.ELEMENT_NODE) {
                                    Element headElementSource = (Element) srcHeader;
                                    //System.out.println(" Header Data--------- : " + headElementSource.getTagName() + " ---values :" + headElementSource.getTextContent());
                                    for (int m = 0; m < transTarDetails.getLength(); m++) {
                                        Node childTargetNode = transTarDetails.item(m);
                                        Boolean mismatchFlag = false;
                                        if (childTargetNode.getNodeType() == Node.ELEMENT_NODE) {
                                            Element childTargetElement = (Element) childTargetNode;
                                            if(headElementSource.getTagName().equals(childTargetElement.getTagName())){
                                                if(headElementSource.getTextContent().equals(childTargetElement.getTextContent())){
                                                    mismatchFlag = false;
                                                    //System.out.println(" Transaction mAtch Details-------------: "+childElement.getTextContent());
                                                }else{
                                                    mismatchFlag = true;
                                                    //countTags++;
                                                    // System.out.println(" Transaction Mismatch Details-------------: "+childElement.getTextContent());
                                                }
                                            }
                                            if(mismatchFlag != false){

                                                //System.out.println(" Mismatch tags <"+ childTargetElement.getParentNode().getNodeName() +">"+ childTargetElement.getTagName() +"---: Expected '"+headElementSource.getTextContent()+"' but actual '"+childTargetElement.getTextContent()+"'");
                                            }
                                        }
                                    }

                                }
                            }

                        }
                    }
                }
            }
        }catch (Exception e ){
            e.printStackTrace();
        }


    }
}

