/*
author: 请问
2024/1/17  21:19

*/
package com.zth.infoseeker.service.interfaces;

import com.zth.infoseeker.model.ParsedDocument;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface IXmlParserService {
    List<ParsedDocument> processXML(String xmlContent) throws IOException;
}
