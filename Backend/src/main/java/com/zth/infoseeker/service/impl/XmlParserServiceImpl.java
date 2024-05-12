/*
author: 请问
2024/1/17  21:25

*/
package com.zth.infoseeker.service.impl;

import com.zth.infoseeker.model.ParsedDocument;
import com.zth.infoseeker.repository.RawDocumentRepository;
import com.zth.infoseeker.service.interfaces.IXmlParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class XmlParserServiceImpl implements IXmlParserService {

    // 注入RawDocumentRepository
    @Autowired
    private RawDocumentRepository rawDocumentRepository;

    @Override
    public List<ParsedDocument> processXML(String xmlContent) throws IOException {
        List<ParsedDocument> parsedDocuments = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            // 将字符串转换为InputStream
            ByteArrayInputStream inputStream = new ByteArrayInputStream(xmlContent.getBytes(StandardCharsets.UTF_8));
            Document doc = builder.parse(inputStream);
            doc.getDocumentElement().normalize();
            Element root = doc.getDocumentElement();
            NodeList FEDREG = root.getChildNodes();

//            *******RULES*******
            // 获取<RULES>标签下的所有<RULE>标签
            NodeList ruleNodes = doc.getElementsByTagName("RULE");
            NodeList dateNodes = doc.getElementsByTagName("DATE");
            Element dateElement = (Element) dateNodes.item(0);

            String date=dateElement.getTextContent();
            for (int i = 0; i < ruleNodes.getLength(); i++) {
                ParsedDocument parsedDocument = new ParsedDocument();
                parsedDocument.setDateEn(date);
                parsedDocument.setTypeEn("Rules and Regulations");
                Element ruleElement = (Element) ruleNodes.item(i);

//                获取<RULE>标签下的所有<SUM>标签、<AGENCY>标签、<SUBJECT>
                NodeList pNodes1 = ruleElement.getElementsByTagName("P");
                NodeList agencyNodes1 = ruleElement.getElementsByTagName("AGENCY");
                NodeList subjectNodes1 = ruleElement.getElementsByTagName("SUBJECT");
                Element subjectElement1 = (Element) subjectNodes1.item(0);
                Element agencyElement1 = (Element) agencyNodes1.item(0);
                String agency1=agencyElement1.getTextContent();
                parsedDocument.setAgencyEn(agency1);
                String subject1=subjectElement1.getTextContent();
                parsedDocument.setSubjectEn(subject1);
                String content1="";
                for (int x = 0; x < pNodes1.getLength(); x++) {
                    Node pnode = pNodes1.item(x);
                    if (pnode.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) pnode;

                        if (element.getParentNode().getNodeName().equalsIgnoreCase("SUM")) {
                            String sum1=element.getTextContent();
                            parsedDocument.setSummaryEn(sum1);
                        }

                        if (element.getParentNode().getNodeName().equalsIgnoreCase("SUPLINF")) {
                            content1+=element.getTextContent();

                        }
                    }
                }
                parsedDocument.setContentEn(content1);
                        parsedDocuments.add(parsedDocument);
                    }


            //            *******PROPOSED RULES*******
            // 获取<PRORULES>标签下的所有<PRORULE>标签
            NodeList proruleNodes = doc.getElementsByTagName("PRORULE");
            for (int d = 0; d < proruleNodes.getLength(); d++) {
                ParsedDocument parsedDocument = new ParsedDocument();
                parsedDocument.setDateEn(date);
                parsedDocument.setTypeEn("Proposed Rules");
                Element proruleElement = (Element) proruleNodes.item(d);

//                获取<PRORULE>标签下的所有<SUM>标签、<AGENCY>标签、<SUBJECT>
                NodeList pNodes2 = proruleElement.getElementsByTagName("P");
                NodeList agencyNodes2 = proruleElement.getElementsByTagName("AGENCY");
                NodeList subjectNodes2 = proruleElement.getElementsByTagName("SUBJECT");
                Element subjectElement2 = (Element) subjectNodes2.item(0);
                Element agencyElement2 = (Element) agencyNodes2.item(0);
                String agency2=agencyElement2.getTextContent();
                parsedDocument.setAgencyEn(agency2);
                String subject2=subjectElement2.getTextContent();
                parsedDocument.setSubjectEn(subject2);
                String content2="";
                for (int x = 0; x < pNodes2.getLength(); x++) {
                    Node pnode = pNodes2.item(x);
                    if (pnode.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) pnode;

                        if (element.getParentNode().getNodeName().equalsIgnoreCase("SUM")) {
                            String sum2=element.getTextContent();
                            parsedDocument.setSummaryEn(sum2);
                        }

                        if (element.getParentNode().getNodeName().equalsIgnoreCase("SUPLINF")) {
                            content2+=element.getTextContent();

                        }
                    }
                }
                parsedDocument.setContentEn(content2);
                parsedDocuments.add(parsedDocument);
            }

            //            *******Notices*******
            // 获取<NOTICES>标签下的所有<NOTICE>标签
            NodeList noticeNodes = doc.getElementsByTagName("NOTICE");
            for (int c = 0; c < noticeNodes.getLength(); c++) {
                ParsedDocument parsedDocument = new ParsedDocument();
                parsedDocument.setDateEn(date);
                parsedDocument.setTypeEn("Notices");
                Element noticeElement = (Element) noticeNodes.item(c);

//                获取<Notice>标签下的所有<SUM>标签、<AGENCY>标签、<SUBJECT>
                NodeList pNodes3 = noticeElement.getElementsByTagName("P");
                NodeList agencyNodes3 = noticeElement.getElementsByTagName("AGENCY");
                NodeList subjectNodes3 = noticeElement.getElementsByTagName("SUBJECT");
                Element subjectElement3 = (Element) subjectNodes3.item(0);
                Element agencyElement3 = (Element) agencyNodes3.item(0);
                String agency3=agencyElement3.getTextContent();
                parsedDocument.setAgencyEn(agency3);
                String subject3=subjectElement3.getTextContent();
                parsedDocument.setSubjectEn(subject3);
                String content3="";
                for (int x = 0; x < pNodes3.getLength(); x++) {
                    Node pnode = pNodes3.item(x);
                    if (pnode.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) pnode;

                        if (element.getParentNode().getNodeName().equalsIgnoreCase("SUM")) {
                            String sum3=element.getTextContent();
                            parsedDocument.setSummaryEn(sum3);
                        }

                        if (element.getParentNode().getNodeName().equalsIgnoreCase("SUPLINF")) {
                            content3+=element.getTextContent();

                        }
                    }
                }
                parsedDocument.setContentEn(content3);
                parsedDocuments.add(parsedDocument);
            }


        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();}
    return parsedDocuments;
    }
}
