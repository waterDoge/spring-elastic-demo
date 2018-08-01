package com.ata.elastic.api;

import com.ata.elastic.config.ApplicationProperties;
import com.ata.elastic.entity.Article;
import com.ata.elastic.repository.ArticleRepository;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RepositoryRestController
@RequestMapping("/article")
public class ArticleController {
    private ArticleRepository repository;
    private ApplicationProperties properties;
    private  ElasticsearchTemplate elasticsearchTemplate;

    public ArticleController(ArticleRepository repository, ApplicationProperties properties, Client client) {
        this.repository = repository;
        this.properties = properties;
        elasticsearchTemplate = new ElasticsearchTemplate(client);
    }

    @GetMapping("es")
    public Mono<ResponseEntity> article(@RequestParam(defaultValue = "") String queryString,
                                        @RequestParam(defaultValue = "0") Integer page,
                                        @RequestParam(defaultValue = "20") Integer size) {
            final SearchQuery query = new NativeSearchQueryBuilder()
                    .withFields()
                    .withQuery(QueryBuilders.queryStringQuery(queryString).analyzer("ik_max_word"))
                    .withHighlightFields(new HighlightBuilder.Field("content").requireFieldMatch(false))
                    .withPageable(PageRequest.of(page, size))
                    .build();
        final AggregatedPage<Article> articles = elasticsearchTemplate.queryForPage(query, Article.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
                List<Article> chunk = new ArrayList<Article>();
                final SearchHits hits = response.getHits();
                for (SearchHit searchHit : hits) {
                    if (hits.getHits().length <= 0) {
                        return null;
                    }
                    Article article = new Article();
                    article.setId(searchHit.getId());
                    article.setTitle((String) searchHit.getSource().get("title"));
                    article.setContent(searchHit.getHighlightFields().get("content").fragments()[0].toString());
                    chunk.add(article);
                    if (chunk.size() > 0) {
                        return new AggregatedPageImpl<T>((List<T>) chunk);
                    }
                    return null;
                }
                return null;
            }
        });
        final Page<Article> result = repository.search(query);
//        Link link = new Link("self", "self");
//        Resources resources = new Resources<>(page);
        return Mono.just(ResponseEntity.ok(result));
    }

}
