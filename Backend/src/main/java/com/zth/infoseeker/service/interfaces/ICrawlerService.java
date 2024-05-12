/*
author: 请问
2024/1/17  20:56

*/
package com.zth.infoseeker.service.interfaces;

import java.io.IOException;
import java.util.List;

public interface ICrawlerService {
    List<String> crawlXml() throws IOException, InterruptedException;
    List<String> crawlXmlForToday() throws IOException, InterruptedException;

}
