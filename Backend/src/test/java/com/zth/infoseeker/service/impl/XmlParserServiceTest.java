/*
author: 请问
2024/1/18  17:17

*/
package com.zth.infoseeker.service.impl;

import com.zth.infoseeker.model.ParsedDocument;
import com.zth.infoseeker.repository.RawDocumentRepository;
import com.zth.infoseeker.service.impl.XmlParserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class XmlParserServiceTest {

    @Mock
    private RawDocumentRepository rawDocumentRepository;

    @InjectMocks
    private XmlParserServiceImpl xmlParserService;

    private String xmlContent;

    @BeforeEach
    void setUp() {
        // 初始化一些XML内容，用于测试
        xmlContent = "<root>" +
                "<FEDREG>" +
                "<RULE>" +
                "<DATE>2024-01-17</DATE>" +
                "<SUM><P>Summary of the rule</P></SUM>" +
                "<AGENCY>Agency name</AGENCY>" +
                "<SUBJECT>The subject of the rule</SUBJECT>" +
                "<SUPLINF><P>Supplementary information</P></SUPLINF>" +
                "</RULE>" +
                // ... 其他类似的RULE, PRORULE, NOTICE等元素 ...
                "</FEDREG>" +
                "</root>";
    }

    @Test
    void testProcessXml() throws IOException {
        // 调用processXML方法
        List<ParsedDocument> result = xmlParserService.processXML(xmlContent);

        // 验证结果不应为空
        assertFalse(result.isEmpty());

        // 打印解析结果
        for (ParsedDocument parsedDocument : result) {
            System.out.println("Date: " + parsedDocument.getDate());
            System.out.println("Agency: " + parsedDocument.getAgency());
            System.out.println("Type: " + parsedDocument.getType());
            System.out.println("Subject: " + parsedDocument.getSubjectEn());
            System.out.println("Summary: " + parsedDocument.getSummaryEn());
            System.out.println("Content: " + parsedDocument.getContentEn());
            System.out.println();
        }
    }
}

