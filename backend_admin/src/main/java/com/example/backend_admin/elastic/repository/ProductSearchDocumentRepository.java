package com.example.backend_admin.elastic.repository;

import com.example.backend_admin.elastic.model.entity.ProductSearchDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductSearchDocumentRepository extends ElasticsearchRepository<ProductSearchDocument, String> {

}
