package com.application.dto;

import java.util.List;

public class CrawlReq {
	private List<String> urls;
	private String keyword;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public List<String> getUrls() {
		return urls;
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
	}

	@Override
	public String toString() {
		return "CrawlReq [urls=" + urls + ", keyword=" + keyword + "]";
	}

}
