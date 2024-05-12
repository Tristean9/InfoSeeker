/*
author: 请问
2024/1/19  15:25

*/
package com.zth.infoseeker.dto;

import lombok.Data;
import com.zth.infoseeker.model.ParsedDocument;
import lombok.NoArgsConstructor;

@NoArgsConstructor // Lombok注解，用于生成一个无参构造方法
@Data
public class ParsedDocumentDTO {

    private Long id;
    private String dateEn;
    private String dateZh;
    private String agencyEn;
    private String agencyZh;
    private String typeEn;
    private String typeZh;
    private String subjectZh;
    private String subjectEn;
    private String summaryZh;
    private String summaryEn;
    private String contentZh;
    private String contentEn;

    public ParsedDocumentDTO(ParsedDocument document) {
        this.id = document.getId();
        this.dateEn = document.getDateEn();
        this.dateZh = document.getDateZh();
        this.agencyEn = document.getAgencyEn();
        this.agencyZh = document.getAgencyZh();
        this.typeEn = document.getTypeEn();
        this.typeZh = document.getTypeZh();
        this.subjectZh = document.getSubjectZh();
        this.subjectEn = document.getSubjectEn();
        this.summaryZh = document.getSummaryZh();
        this.summaryEn = document.getSummaryEn();
        this.contentZh = document.getContentZh();
        this.contentEn = document.getContentEn();
    }
}
