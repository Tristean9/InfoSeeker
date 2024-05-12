package com.zth.infoseeker.service.interfaces;

import com.zth.infoseeker.model.ParsedDocument;
import java.io.IOException;
import java.util.List;

public interface IIndexService {
    void createOrUpdateIndex(List<ParsedDocument> documents) throws IOException;
}
