package com.zth.infoseeker.repository;

import com.zth.infoseeker.model.ParsedDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParsedDocumentRepository extends JpaRepository<ParsedDocument, Long> {
    // 这里可以添加查询方法，但JPA提供了一些默认的方法，如save
    @Override
    boolean existsById(Long id);
}
