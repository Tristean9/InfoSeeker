/*
author: 请问
2024/1/18  11:16

*/
package com.zth.infoseeker.model;

import javax.persistence.*;
// 假设您已经有了lombok依赖来简化这个类
import lombok.Data;
@Entity
@Data
@Table(name = "raw_documents")
public class RawDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url", unique = true)
    private String url;

    @Lob
    @Column(name = "xml_content", columnDefinition = "LONGTEXT")
    private String xmlContent; // 使用字符串类型来保存XML数据

}