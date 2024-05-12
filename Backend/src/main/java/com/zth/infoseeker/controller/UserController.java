/*
author: 请问
2024/1/17  20:52

*/
package com.zth.infoseeker.controller;

import com.zth.infoseeker.dto.ParsedDocumentDTO;
import com.zth.infoseeker.model.ParsedDocument;
import com.zth.infoseeker.service.interfaces.ISearchService;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {
    private final ISearchService searchService;

    // 使用构造函数注入SearchService
    @Autowired
    public UserController(ISearchService searchService) {
        this.searchService = searchService;
    }

    // 检索服务
    @PostMapping("/search")
    public ResponseEntity<List<ParsedDocumentDTO>> search(@RequestBody Map<String, String> searchExpressions) {
    try {
        List<ParsedDocument> results = searchService.performSearch(searchExpressions);
        //System.out.println("ParsedDocument: " + results.get(0).getDateEn());
        // 将ParsedDocument转换为ParsedDocumentDTO
        List<ParsedDocumentDTO> dtoResults = results.stream()
                                                    .map(ParsedDocumentDTO::new)
                                                    .collect(Collectors.toList());
        //System.out.println("ParsedDocumentDTO:" + dtoResults.get(0).getDateZh());
        return ResponseEntity.ok(dtoResults);
    }      catch (IOException | ParseException e) {
        e.printStackTrace();
        // 修改此处，以使其返回与方法签名相匹配的类型
        return ResponseEntity.internalServerError().body(new ArrayList<ParsedDocumentDTO>());
    }
    }

    // 可能还有其他用户功能
}

