package com.ata.elastic.api;

import com.ata.elastic.config.ApplicationProperties;
import com.ata.elastic.entity.Article;
import com.ata.elastic.repository.ArticleRepository;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

@RepositoryRestController
@RequestMapping("/article")
public class ArticleController {
    private ArticleRepository repository;
    private ApplicationProperties properties;

    public ArticleController(ArticleRepository repository, ApplicationProperties properties) {
        this.repository = repository;
        this.properties = properties;
    }

    @GetMapping
    public Mono<ResponseEntity> article(@RequestParam(defaultValue = "") String queryString,
                                        @RequestParam(defaultValue = "0") Integer page,
                                        @RequestParam(defaultValue = "20") Integer size) {
        QueryBuilder builder = new QueryStringQueryBuilder(queryString);
        final Page<Article> result = repository.search(builder, PageRequest.of(page, size));
//        Link link = new Link("self", "self");
//        Resources resources = new Resources<>(page);
        return Mono.just(ResponseEntity.ok(result));
    }

}
