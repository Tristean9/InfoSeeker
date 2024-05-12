/*
author: 请问
2024/1/17  22:22

*/
package com.zth.infoseeker.controller;

import com.zth.infoseeker.dto.ParsedDocumentDTO;
import com.zth.infoseeker.model.ParsedDocument;
import com.zth.infoseeker.service.interfaces.IDataProcessingService;
import com.zth.infoseeker.service.interfaces.ISearchService;
import com.zth.infoseeker.service.interfaces.IIndexService;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
//@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final IDataProcessingService dataProcessingService;
    private final ISearchService searchService;
    private final IIndexService indexService;


    // 注入所有服务
    @Autowired
    public AdminController(IDataProcessingService dataProcessingService,
                           ISearchService searchService,
                           IIndexService indexService) {
        this.dataProcessingService = dataProcessingService;
        this.searchService = searchService;
        this.indexService = indexService;
    }

     // 数据爬取、处理和保存服务
    @PostMapping("/crawl-and-process-and-save-all")
    public ResponseEntity<String> processAndSaveData() {
        try {
            dataProcessingService.processAndSaveData();
            return ResponseEntity.ok("Data crawling , processing and saving started successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error occurred: " + e.getMessage());
        }
    }

    @PostMapping("/crawl-and-process-and-save-today")
    public ResponseEntity<List<ParsedDocumentDTO>> processAndSaveDataForToday() {
        try {
            List<ParsedDocumentDTO> documents = dataProcessingService.processAndSaveDataForToday();
            return ResponseEntity.ok(documents);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }


    // 检索服务
    @PostMapping("/search")
    public ResponseEntity<List<ParsedDocumentDTO>> search(@RequestBody Map<String, String> searchExpressions){
    try {
        List<ParsedDocument> results = searchService.performSearch(searchExpressions);
        // 将ParsedDocument转换为ParsedDocumentDTO
        List<ParsedDocumentDTO> dtoResults = results.stream()
                                                    .map(ParsedDocumentDTO::new)
                                                    .collect(Collectors.toList());
        return ResponseEntity.ok(dtoResults);
    }      catch (IOException | ParseException e) {
        e.printStackTrace();
        // 修改此处，以使其返回与方法签名相匹配的类型
        return ResponseEntity.internalServerError().body(new ArrayList<ParsedDocumentDTO>());
    }
    }

    @PutMapping("/update-document")
    public ResponseEntity<?> updateDocument(@RequestBody ParsedDocumentDTO documentDTO) {
    try {
        ParsedDocument document = new ParsedDocument(); // 实例化一个新的ParsedDocument对象

        // 将DTO中的值复制到实体对象中
        document.setId(documentDTO.getId());
        document.setDateEn(documentDTO.getDateEn());
        document.setDateZh(documentDTO.getDateZh());
        document.setAgencyEn(documentDTO.getAgencyEn());
        document.setAgencyZh(documentDTO.getAgencyZh());
        document.setTypeEn(documentDTO.getTypeEn());
        document.setTypeZh(documentDTO.getTypeZh());
        document.setSubjectZh(documentDTO.getSubjectZh());
        document.setSubjectEn(documentDTO.getSubjectEn());
        document.setSummaryZh(documentDTO.getSummaryZh());
        document.setSummaryEn(documentDTO.getSummaryEn());
        document.setContentZh(documentDTO.getContentZh());
        document.setContentEn(documentDTO.getContentEn());

        // 调用数据处理服务更新数据库记录
        boolean isUpdated = dataProcessingService.updateParsedDocument(document);

        if(isUpdated) {
            return ResponseEntity.ok("Document updated successfully.");
        } else {
            return ResponseEntity.badRequest().body("Document not found or error during the update.");
        }
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.internalServerError().body("Error occurred: " + e.getMessage());
    }
}

    @PostMapping("/build-index")
    public ResponseEntity<String> buildIndex() {
        try {
            // 从服务层获取所有文档
            List<ParsedDocument> allDocuments = dataProcessingService.getAllDocuments();
            // 使用索引服务构建或更新索引
            indexService.createOrUpdateIndex(allDocuments);
            return ResponseEntity.ok("Index built or updated successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error occurred while building index: " + e.getMessage());
        }
}


    // 其他管理员功能
}
