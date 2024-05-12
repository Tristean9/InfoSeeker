package com.zth.infoseeker.repository;

import com.zth.infoseeker.model.RawDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RawDocumentRepository extends JpaRepository<RawDocument, Long> {
    // 这里可以添加查询方法，但JPA提供了一些默认的方法，如save
     boolean existsByUrl(String url);

     // 假设 RawDocument 类有一个属性名为 xmlContent
    @Query("SELECT r.xmlContent FROM RawDocument r WHERE r.url = :url")
    Optional<String> findXmlContentByUrl(@Param("url") String url);
}
