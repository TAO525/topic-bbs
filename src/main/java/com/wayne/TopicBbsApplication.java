package com.wayne;

import com.wayne.common.lucene.LuceneUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
@EnableCaching
public class TopicBbsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TopicBbsApplication.class, args);
	}

	@Bean(name = "luceneUtil")
	public LuceneUtil luceneUtil(Environment env){
		LuceneUtil luceneUtil = new LuceneUtil();
		luceneUtil.setIndexDer(env.getProperty("lucene.indexder"));
		return luceneUtil;
	}
}
