/*
author: 请问
2024/1/18  16:32

*/
package com.zth.infoseeker.service.impl;

import com.zth.infoseeker.model.ParsedDocument;
import com.zth.infoseeker.repository.ParsedDocumentRepository;
import com.zth.infoseeker.repository.RawDocumentRepository;
import com.zth.infoseeker.service.interfaces.IDataProcessingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class DataProcessingServiceImplIntegrationTest {

    @Autowired
    private IDataProcessingService dataProcessingService;

    @Autowired
    private RawDocumentRepository rawDocumentRepository;

    @Autowired
    private ParsedDocumentRepository parsedDocumentRepository;

    @Test
    public void testProcessAndSaveData() throws IOException {
        // 假设爬虫服务已经运行，并且RawDocumentRepository已经被填充

        // 运行数据处理服务
        dataProcessingService.processAndSaveData();
        // 验证 RawDocumentRepository 是否不为空
        assertFalse(rawDocumentRepository.findAll().isEmpty());

        // 验证解析后的文档是否已经存储在数据库中
        List<ParsedDocument> parsedDocuments = parsedDocumentRepository.findAll();
        assertFalse(parsedDocuments.isEmpty(), "Parsed documents should be saved in the database.");

        // 此处可以加入更多的断言，例如检查翻译是否正确、字段是否完整等等
        // ...
        //assertTrue(parsedDocuments.stream().allMatch(doc -> doc.getSubjectEn() != null && !doc.getSubjectEn().isEmpty()), "All documents should have a non-empty subject in English.");
        //assertTrue(parsedDocuments.stream().allMatch(doc -> doc.getSubjectZh() != null && !doc.getSubjectZh().isEmpty()), "All documents should have a non-empty subject in Chinese.");

    }
}
