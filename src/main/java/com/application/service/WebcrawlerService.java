package com.application.service;

import java.util.List;

import com.application.dto.CrawlReq;
import com.application.exception.CrawlerException;

public interface WebcrawlerService {
	public List<String> searchText(CrawlReq requestDto);
}
