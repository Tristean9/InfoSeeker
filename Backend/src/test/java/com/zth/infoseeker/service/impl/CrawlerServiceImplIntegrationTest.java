/*
author: 请问
2024/1/18  13:41

*/
package com.zth.infoseeker.service.impl;

import com.zth.infoseeker.model.RawDocument;
import com.zth.infoseeker.model.ParsedDocument;
import com.zth.infoseeker.repository.RawDocumentRepository;
import com.zth.infoseeker.service.interfaces.ICrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
public class CrawlerServiceImplIntegrationTest {

    @Autowired
    private ICrawlerService crawlerService;

    @Autowired
    private RawDocumentRepository rawDocumentRepository;

    @Test
    //@Transactional
    public void crawlActualXmlTest() throws IOException, InterruptedException {
        // 实际爬取操作
        List<String> savedUrls = crawlerService.crawlXmlForToday();

        // 验证URL列表不为空
        //assertFalse(savedUrls.isEmpty());
        // 打印爬取的URL，仅供测试时查看
        System.out.println("Crawled URLs: ");
        for (String url : savedUrls) {
            System.out.println(url);
        }
        // 验证数据库中是否存储了文档
        for (String url : savedUrls) {
            boolean exists = rawDocumentRepository.existsByUrl(url);
            assertTrue(exists, "The document with URL " + url + " should be saved in the database.");
        }


    }
}
