package com.ata.elastic;

import com.ata.elastic.entity.Article;
import com.ata.elastic.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticApplicationTests {


	@Test
	public void contextLoads() {
	}

	@Test
	public void testArticlePost() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        RestTemplate restTemplate = new RestTemplate();
        Article article = new Article();
        article.setContent("咋哇鲁朵，wryyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");
        article.setTitle("咋哇鲁朵");
        User user = new User();
        user.setUsername("zawarudo");
        article.setUser(user);
        final Object result = restTemplate.postForObject("http://localhost:8080/api/articles", article, Article.class);
        System.out.println(objectMapper.writeValueAsString(result));
    }
}
