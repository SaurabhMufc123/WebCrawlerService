package com.application.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.application.config.PropertiesConfig;
import com.application.dto.CrawlReq;
import com.application.exception.CrawlerException;
import com.application.executor.UrlCrawlerExecutor;
import com.application.executor.UrlTextSearchExecutor;
import com.application.executor.ThreadExecutorService;
import com.application.service.WebcrawlerService;

@Service
public class WebcrawlerServiceImpl implements WebcrawlerService {

	private ThreadExecutorService threadExecutorService = new ThreadExecutorService();
	Logger logger = LoggerFactory.getLogger(WebcrawlerServiceImpl.class);

	public List<String> searchText(CrawlReq crawlRequest) {
		List<String> matchesFoundList = null;
		try {
			// extract unique list of urls
			HashSet<String> extractedUrls = extractUrls(crawlRequest.getUrls());

			if (extractedUrls.isEmpty()) {
				throw new CrawlerException("unable to crawl");
			}

			// search for keyword in urls
			matchesFoundList = searchUrls(extractedUrls, crawlRequest.getKeyword());
		} catch (Exception ex) {
			logger.error("error while crawling for req {}", crawlRequest, ex);
			throw new CrawlerException("error occurred while crawling");
		}
		return matchesFoundList;
	}

	/**
	 * 
	 * @param extractedUrls
	 * @param keyword
	 * @return List of articles containing the keyword @throws Exception @throws
	 */
	private List<String> searchUrls(HashSet<String> extractedUrls, String keyword) throws Exception {
		logger.info("searching keyword {} under urls: {}", keyword, extractedUrls);
		List<String> matchList = new ArrayList<String>();
		if (!extractedUrls.isEmpty() && keyword != null) {
			for (String url : extractedUrls) {
				UrlTextSearchExecutor threadExecutor = new UrlTextSearchExecutor(url, keyword);

				// submit to thread pool
				Future<String> textSearchJob = threadExecutorService.addTextSearchJob(threadExecutor);

				// wait for result from future task
				String textSearchResult = textSearchJob.get();
				if (textSearchResult != null) {
					matchList.add(textSearchResult);
				}
			}
		}
		return matchList;
	}

	/**
	 * 
	 * @param url
	 * @return List of extracted URLs
	 * @throws Exception
	 */
	private HashSet<String> extractUrls(List<String> urls) throws Exception {
		Future<HashSet<String>> result = null;
		for (String url : urls) {
			UrlCrawlerExecutor threadExecutor = new UrlCrawlerExecutor(url);
			// submit to thread pool
			result = threadExecutorService.addUrlCrawlJob(threadExecutor);
		}
		// wait for result from future task
		return result.get();
	}

}
