package com.ata.elastic.repository;

import com.ata.elastic.entity.Comment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CommentRepository extends ElasticsearchRepository<Comment, String> {
}
