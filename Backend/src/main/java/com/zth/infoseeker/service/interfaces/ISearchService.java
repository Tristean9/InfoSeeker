/*
author: 请问
2024/1/17  21:32

*/
package com.zth.infoseeker.service.interfaces;

import com.zth.infoseeker.model.ParsedDocument;
import org.apache.lucene.queryparser.classic.ParseException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ISearchService {
    List<ParsedDocument> performSearch(Map<String, String> searchExpressions) throws IOException, ParseException;

}

