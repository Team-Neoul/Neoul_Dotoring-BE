package com.theZ.dotoring.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.theZ.dotoring.app.desiredField.repository.QueryDesiredFieldRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@TestConfiguration
public class TestQueryDslConfig {

    @PersistenceContext
    private EntityManager em;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(em);
    }

    @Bean
    public QueryDesiredFieldRepository queryDesiredFieldRepository(){
        return new QueryDesiredFieldRepository(jpaQueryFactory());
    }

}
