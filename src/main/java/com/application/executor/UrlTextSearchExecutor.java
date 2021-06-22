package com.application.executor;

import java.util.HashSet;
import java.util.concurrent.Callable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UrlTextSearchExecutor implements Callable<String> {
	private String url;
	private String keyword;
	Logger logger = LoggerFactory.getLogger(UrlTextSearchExecutor.class);

	public UrlTextSearchExecutor(String url, String keyword) {
		super();
		this.url = url;
		this.keyword = keyword;
	}

	public String call() throws Exception {
		try {
			Document document = Jsoup.connect(url).get();
			// Extract headings from the URLs
	        Elements paragraphs = document.getElementsByTag("p");
			for (Element element : paragraphs) {
				if (element.text().matches("^.*?(" + keyword + ").*$")) {
					logger.info("text found in url: {}", url);
					return url;
				}
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		return null;
	}

}
