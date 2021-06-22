package com.application.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.application.dto.CrawlReq;
import com.application.service.impl.WebcrawlerServiceImpl;

@SpringBootTest
class WebcrawlerApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Test
	public void successTest() {
		
		WebcrawlerServiceImpl service = new WebcrawlerServiceImpl();
		CrawlReq crawlReq = new CrawlReq();
		crawlReq.setUrls(new ArrayList<String>());
		crawlReq.getUrls().add("https://en.wikipedia.org/wiki/Main_Page");
		crawlReq.setKeyword("Wikipedia");
		assertTrue(!service.searchText(crawlReq).isEmpty());
	}

	@Test
	public void failureTest() {
		
		WebcrawlerServiceImpl service = new WebcrawlerServiceImpl();
		CrawlReq crawlReq = new CrawlReq();
		crawlReq.setUrls(new ArrayList<String>());
		crawlReq.getUrls().add("https://junit.org/junit4/dependency-info.html");
		crawlReq.setKeyword("Einstein");
		assertTrue(service.searchText(crawlReq).isEmpty());
	}
}
