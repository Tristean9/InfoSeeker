package com.zth.infoseeker.service.interfaces;

import com.zth.infoseeker.dto.ParsedDocumentDTO;
import com.zth.infoseeker.model.ParsedDocument;

import java.io.IOException;
import java.util.List;

public interface IDataProcessingService {
    void processAndSaveData() throws IOException;
    List<ParsedDocumentDTO> processAndSaveDataForToday() throws IOException;
    boolean updateParsedDocument(ParsedDocument document);

    List<ParsedDocument> getAllDocuments();
}
