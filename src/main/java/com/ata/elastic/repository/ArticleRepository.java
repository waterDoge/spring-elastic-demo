package com.ata.elastic.repository;

import com.ata.elastic.entity.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ArticleRepository extends ElasticsearchRepository<Article, String> {

    List<String> findContentByTitle(String title);
}
