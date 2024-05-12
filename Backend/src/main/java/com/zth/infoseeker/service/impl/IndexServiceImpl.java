/*
author: 请问
2024/1/19  20:29

*/
package com.zth.infoseeker.service.impl;

import com.zth.infoseeker.model.ParsedDocument;
import com.zth.infoseeker.service.interfaces.IIndexService;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.io.File;

@Service
public class IndexServiceImpl implements IIndexService {
    private static final String INDEX_DIR = System.getProperty("user.dir") + File.separator + "index";

    @Override
    public void createOrUpdateIndex(List<ParsedDocument> documents) throws IOException {
        Directory directory = FSDirectory.open(Paths.get(INDEX_DIR));
        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig conf = new IndexWriterConfig(analyzer);
        conf.setOpenMode(IndexWriterConfig.OpenMode.CREATE); // 设置为CREATE模式，覆盖旧的索引
        IndexWriter indexWriter = new IndexWriter(directory, conf);

        for (ParsedDocument doc : documents) {
            Document luceneDoc = new Document();
            if (doc.getId() != null) {
                luceneDoc.add(new LongPoint("id", doc.getId())); // 用于查询
                luceneDoc.add(new StoredField("id", doc.getId())); // 用于存储和检索

                addFieldToDocument(luceneDoc, "dateEn", doc.getDateEn(), StringField.TYPE_STORED);
                addFieldToDocument(luceneDoc, "dateZh", doc.getDateZh(), StringField.TYPE_STORED);
                addFieldToDocument(luceneDoc, "agencyEn", doc.getAgencyEn(), StringField.TYPE_STORED);
                addFieldToDocument(luceneDoc, "agencyZh", doc.getAgencyZh(), StringField.TYPE_STORED);
                addFieldToDocument(luceneDoc, "typeEn", doc.getTypeEn(), StringField.TYPE_STORED);
                addFieldToDocument(luceneDoc, "typeZh", doc.getTypeZh(), StringField.TYPE_STORED);
                addFieldToDocument(luceneDoc, "subjectZh", doc.getSubjectZh(), TextField.TYPE_STORED);
                addFieldToDocument(luceneDoc, "subjectEn", doc.getSubjectEn(), TextField.TYPE_STORED);
                addFieldToDocument(luceneDoc, "summaryZh", doc.getSummaryZh(), TextField.TYPE_STORED);
                addFieldToDocument(luceneDoc, "summaryEn", doc.getSummaryEn(), TextField.TYPE_STORED);
                addFieldToDocument(luceneDoc, "contentZh", doc.getContentZh(), TextField.TYPE_STORED);
                addFieldToDocument(luceneDoc, "contentEn", doc.getContentEn(), TextField.TYPE_STORED);

                Term idTerm = new Term("id", String.valueOf(doc.getId()));
                indexWriter.updateDocument(idTerm, luceneDoc);
            }else{
                indexWriter.addDocument(luceneDoc); // 没有ID，则添加新文档
            }
        }

        indexWriter.commit();
        indexWriter.close();
        analyzer.close();
        directory.close();
    }

    private void addFieldToDocument(Document doc, String fieldName, String value, FieldType type) {
        if (value != null) {
            doc.add(new Field(fieldName, value, type));
        }
    }

}

