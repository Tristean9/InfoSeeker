/*
author: 请问
2024/1/17  21:43

*/
package com.zth.infoseeker.service.impl;


import com.zth.infoseeker.model.ParsedDocument;
import com.zth.infoseeker.service.interfaces.ISearchService;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class SearchServiceImpl implements ISearchService {

    private static final String INDEX_DIR = "./index"; // 这里应该是您的索引目录

    @Override
    public List<ParsedDocument> performSearch(Map<String, String> searchExpressions) throws IOException, ParseException {
        List<ParsedDocument> results = new ArrayList<>();

        Directory directory = FSDirectory.open(Paths.get(INDEX_DIR));
        IndexReader indexReader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(indexReader);
        Analyzer analyzer = new StandardAnalyzer();

        // 使用BooleanQuery.Builder构建多字段查询
        BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();
        for (Map.Entry<String, String> entry : searchExpressions.entrySet()) {
            if (searchExpressions.containsKey("id")) {
                String idStr = searchExpressions.get("id");
                long idValue = Long.parseLong(idStr); // 确保传入的 id 字符串能够转换为长整型
                Query idQuery = LongPoint.newExactQuery("id", idValue);
                booleanQuery.add(idQuery, BooleanClause.Occur.MUST); // 使用MUST表示AND逻辑
            }
            QueryParser queryParser = new QueryParser(entry.getKey(), analyzer);
            Query query = queryParser.parse(entry.getValue());
            booleanQuery.add(query, BooleanClause.Occur.MUST); // 使用MUST表示AND逻辑
        }

        // 执行搜索
        TopDocs topDocs = searcher.search(booleanQuery.build(), 10);

        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            Document luceneDoc = searcher.doc(scoreDoc.doc);
            ParsedDocument parsedDocument = new ParsedDocument();

            // 遍历文档的每个字段
            luceneDoc.getFields().forEach(field -> {
             // 使用反射来动态设置ParsedDocument对象的属性
                try {
                    //System.out.println("luceneDoc.get(field.name()): " + luceneDoc.get(field.name()));
                    //System.out.println("field.name(): " + field.name());
                    parsedDocument.setField(field.name(), luceneDoc.get(field.name()));
                } catch (NoSuchFieldException e) {
                    throw new RuntimeException(e);
                }
            });
            results.add(parsedDocument);
            //System.out.println(parsedDocument.getDateZh());
        }

        indexReader.close();
        directory.close();
        //System.out.println(results);
        return results;
    }

}