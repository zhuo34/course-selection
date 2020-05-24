package com.example.courseselectionbackend.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
public class QuerydslConfig {
	@Bean
	@Autowired
	public JPAQueryFactory queryFactory(EntityManager entityManager){
		return new JPAQueryFactory(entityManager);
	}
}
