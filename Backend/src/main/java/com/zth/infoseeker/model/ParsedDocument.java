/*
author: 请问
2024/1/17  21:24

*/
package com.zth.infoseeker.model;

import javax.persistence.*;

import lombok.Data;

import java.lang.reflect.Field;

@Entity
@Data
@Table(name = "parsed_documents")
public class ParsedDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date_en")
    private String dateEn;

    @Column(name = "date_zh")
    private String dateZh;

    @Column(name = "agency_en")
    private String agencyEn;

    @Column(name = "agency_zh")
    private String agencyZh;

    @Column(name = "type_en")
    private String typeEn;

    @Column(name = "type_zh")
    private String typeZh;


    @Lob
    @Column(name = "subject_zh", columnDefinition = "MEDIUMTEXT")
    private String subjectZh;

    @Lob
    @Column(name = "subject_en", columnDefinition = "MEDIUMTEXT")
    private String subjectEn;

    @Lob
    @Column(name = "summary_zh", columnDefinition = "MEDIUMTEXT")
    private String summaryZh;

    @Lob
    @Column(name = "summary_en", columnDefinition = "MEDIUMTEXT")
    private String summaryEn;

    @Lob
    @Column(name = "content_zh", columnDefinition = "MEDIUMTEXT")
    private String contentZh;

    @Lob
    @Column(name = "content_en", columnDefinition = "MEDIUMTEXT")
    private String contentEn;

    // 使用反射设置字段值，以适应动态字段名

public void setField(String fieldName, String value) throws NoSuchFieldException {
    Field field = this.getClass().getDeclaredField(fieldName);
    try {
        field.setAccessible(true);

        // 因为id字段是Long型，我们针对id字段单独处理
        if ("id".equals(fieldName)) {
            Long IDvalue = Long.parseLong(value);
            field.set(this, IDvalue);
        }else {
            field.set(this, value);
        }

    } catch (IllegalArgumentException | IllegalAccessException e) {
        // 提供详细的异常信息
        throw new IllegalArgumentException("类型不匹配：无法将 " + value.getClass().getSimpleName() +
                " 赋值给字段 " + fieldName + "，该字段的类型为 " +
                field.getType().getSimpleName(), e);
    }
}

    // 使用反射获取字段值，以适应动态字段名
public Object getFieldValue(String fieldName) throws NoSuchFieldException {
    try {
        Field field = this.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(this); // 返回 Object 类型
    } catch (IllegalAccessException e) {
        // Handle exceptions appropriately
        e.printStackTrace();
        return null;
    }
}
}
