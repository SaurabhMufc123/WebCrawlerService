package com.application.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.application.dto.CrawlReq;
import com.application.exception.CrawlerException;
import com.application.service.WebcrawlerService;

@RestController
public class WebcrawlerController {

	@Autowired
	private WebcrawlerService service;

	Logger logger = LoggerFactory.getLogger(WebcrawlerController.class);

	@PostMapping("/text")
	public List<String> searchText(@RequestBody CrawlReq requestDto) throws CrawlerException {
		logger.info("URL to be crawled : {}, Keyword to be searched : {}", requestDto.getUrls(),
				requestDto.getKeyword());
		return service.searchText(requestDto);
	}
}
