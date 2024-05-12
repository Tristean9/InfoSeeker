/*
author: 请问
2024/1/17  22:05

*/
package com.zth.infoseeker.service.impl;

import com.zth.infoseeker.service.interfaces.ITranslationService;
import com.zth.infoseeker.integration.BaiduTranslateAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TranslationServiceImpl implements ITranslationService {

    private final BaiduTranslateAPI transApi;

    @Autowired // 使用 @Autowired 注解来自动注入依赖
    public TranslationServiceImpl(BaiduTranslateAPI transApi) {
        this.transApi = transApi;
    }

    @Override
    public String translateFromZhToEn(String text) {
        try {
            return transApi.translate(text, "zh", "en");
        } catch (Exception e) {
            e.printStackTrace();
            return "Translation error: " + e.getMessage();
        }
    }

    @Override
    public String translateFromEnToZh(String text) {
        try {
            return transApi.translate(text, "en", "zh");
        } catch (Exception e) {
            e.printStackTrace();
            return "Translation error: " + e.getMessage();
        }
    }
}

