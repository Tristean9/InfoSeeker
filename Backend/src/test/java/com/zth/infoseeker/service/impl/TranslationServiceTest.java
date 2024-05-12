/*
author: 请问
2024/1/17  23:57

*/
package com.zth.infoseeker.service.impl;

import com.zth.infoseeker.service.interfaces.ITranslationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class TranslationServiceTest {

    @Autowired
    private ITranslationService translationService;

    @Test
    public void testTranslateFromEnToZh() {
        // 测试从英文翻译到中文的功能
        String result = translationService.translateFromEnToZh("Hello");
        System.out.println(result);
        assertNotNull(result, "翻译结果不应该为空");
        // 注意：因为实际的翻译结果可能会根据API的更新而变化，所以你可能需要调整断言条件
        assertTrue(result.contains("你好") || result.contains("您好"), "翻译结果应该包含 '你好' 或者 '您好'");
    }

    // 其他测试方法...
}

