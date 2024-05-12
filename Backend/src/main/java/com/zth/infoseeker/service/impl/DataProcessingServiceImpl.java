/*
author: 请问
2024/1/18  12:55

*/
package com.zth.infoseeker.service.impl;

import com.zth.infoseeker.model.ParsedDocument;
import com.zth.infoseeker.repository.ParsedDocumentRepository;
import com.zth.infoseeker.repository.RawDocumentRepository;
import com.zth.infoseeker.service.interfaces.ICrawlerService;
import com.zth.infoseeker.service.interfaces.IDataProcessingService;
import com.zth.infoseeker.service.interfaces.ITranslationService;
import com.zth.infoseeker.service.interfaces.IXmlParserService;
import com.zth.infoseeker.service.interfaces.IIndexService;
import com.zth.infoseeker.dto.ParsedDocumentDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataProcessingServiceImpl implements IDataProcessingService {

    @Autowired
    private ICrawlerService crawlerService;

    @Autowired
    private IXmlParserService xmlParserService;

    @Autowired
    private ITranslationService translationService;

    @Autowired
    private RawDocumentRepository rawDocumentRepository;

    @Autowired
    private ParsedDocumentRepository parsedDocumentRepository;

    @Autowired
    private IIndexService indexService;

    @Override
    public void processAndSaveData() {
        // 爬取数据，并立即存入数据库
        List<String> urls;
        try {
            urls = crawlerService.crawlXml();
            //System.out.println("urls:"+urls);
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
        for (String url : urls) {
            //System.out.println(url);
            // 直接使用URL来获取XML内容字符串
            rawDocumentRepository.findXmlContentByUrl(url).ifPresent(xmlContent -> {
                // 解析数据库中的 XML 数据，并存入数据库
                List<ParsedDocument> parsedDocuments;
                try {
                    parsedDocuments = xmlParserService.processXML(xmlContent);
                    // System.out.println("parsedDocuments: " + parsedDocuments);
                } catch (IOException e) {
                    throw new RuntimeException("Error parsing XML content from URL: " + url, e);
                }

                for (ParsedDocument parsedDocument : parsedDocuments) {
                    // 翻译数据
                    // System.out.println("翻译");
                    parsedDocument.setDateZh(translationService.translateFromEnToZh(parsedDocument.getDateEn()));
                    System.out.println(parsedDocument.getDateZh());
                    parsedDocument.setTypeZh(translationService.translateFromEnToZh(parsedDocument.getTypeEn()));
                    System.out.println(parsedDocument.getTypeZh());
                    parsedDocument.setAgencyZh(translationService.translateFromEnToZh(parsedDocument.getAgencyEn()));
                    System.out.println(parsedDocument.getTypeZh());
                    parsedDocument.setSubjectZh(translationService.translateFromEnToZh(parsedDocument.getSubjectEn()));
                    parsedDocument.setSummaryZh(translationService.translateFromEnToZh(parsedDocument.getSummaryEn()));
                    parsedDocument.setContentZh(translationService.translateFromEnToZh(parsedDocument.getContentEn()));

                    // System.out.println(parsedDocument.getContentZh());
                    // 将解析和翻译后的数据存入数据库
                    parsedDocumentRepository.save(parsedDocument);
                }
            });
        }
    }

    @Override
    public List<ParsedDocumentDTO>processAndSaveDataForToday() {
        List<ParsedDocumentDTO> results = new ArrayList<>();
        // 爬取数据，并立即存入数据库
        List<String> urls;
        try {
            urls = crawlerService.crawlXmlForToday();
            // System.out.println("urls:"+urls);
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
        for (String url : urls) {
            // 直接使用URL来获取XML内容字符串
            rawDocumentRepository.findXmlContentByUrl(url).ifPresent(xmlContent -> {
                // 解析数据库中的 XML 数据，并存入数据库
                List<ParsedDocument> parsedDocuments;

                try {
                    parsedDocuments = xmlParserService.processXML(xmlContent);
                    // System.out.println("parsedDocuments: " + parsedDocuments);
                } catch (IOException e) {
                    throw new RuntimeException("Error parsing XML content from URL: " + url, e);
                }

                for (ParsedDocument parsedDocument : parsedDocuments) {
                    // 翻译数据
                    //System.out.println(parsedDocument);
                    // System.out.println("ContentEn:" + parsedDocument.getContentEn());
                    parsedDocument.setDateZh(translationService.translateFromEnToZh(parsedDocument.getDateEn()));
                    //System.out.println(parsedDocument.getDateZh());
                    parsedDocument.setTypeZh(translationService.translateFromEnToZh(parsedDocument.getTypeEn()));
                    //System.out.println(parsedDocument.getTypeZh());
                    parsedDocument.setAgencyZh(translationService.translateFromEnToZh(parsedDocument.getAgencyEn()));
                    //System.out.println(parsedDocument.getTypeZh());
                    parsedDocument.setSubjectZh(translationService.translateFromEnToZh(parsedDocument.getSubjectEn()));
                    parsedDocument.setSummaryZh(translationService.translateFromEnToZh(parsedDocument.getSummaryEn()));
                    parsedDocument.setContentZh(translationService.translateFromEnToZh(parsedDocument.getContentEn()));

                    // System.out.println("ContentZh:" + parsedDocument.getContentZh());
                    // 将解析和翻译后的数据存入数据库
                    parsedDocumentRepository.save(parsedDocument);
                    // 在此处，解析和翻译后的数据存入数据库之后，
                    // 调用创建索引服务来为这些新数据创建或更新索引


                    ParsedDocumentDTO dto = new ParsedDocumentDTO(parsedDocument);
                    results.add(dto); // 添加到结果列表
                }
            });
        }
        List<ParsedDocument> allDocuments = getAllDocuments();
        // 调用创建索引服务来为这些新数据创建或更新索引
        try {
            indexService.createOrUpdateIndex(allDocuments);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create or update index for updated document.", e);
        }
        return results; // 返回结果列表
    }

     @Override
    public boolean updateParsedDocument(ParsedDocument parsedDocument) {

        if (parsedDocument == null || parsedDocument.getId() == null) {
            // 如果document为null或者没有ID，无法更新
            return false;
        }

        // 检查数据库中是否存在该记录
        if (!parsedDocumentRepository.existsById(parsedDocument.getId())) {
            // 如果不存在该记录，无法更新
            return false;
        }

        // 保存更新的文档实体到数据库
        parsedDocumentRepository.save(parsedDocument);

        List<ParsedDocument> allDocuments = getAllDocuments();
        // 调用创建索引服务来为这些新数据创建或更新索引
        try {
            indexService.createOrUpdateIndex(allDocuments);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create or update index for updated document.", e);
        }
        return true;
    }

    @Override
    public List<ParsedDocument> getAllDocuments() {
    // 获取数据库中所有解析过的文档
    return parsedDocumentRepository.findAll();
}
}
