package com.application.executor;

import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.application.config.PropertiesConfig;

// Exector Service class
public class ThreadExecutorService {
	private ExecutorService executor = Executors.newFixedThreadPool(PropertiesConfig.MAX_THREADS);

	public Future<HashSet<String>> addUrlCrawlJob(UrlCrawlerExecutor urlCrawlerExecutor) {
		return executor.submit(urlCrawlerExecutor);
	}
	public Future<String> addTextSearchJob(UrlTextSearchExecutor urlTextSearchExecutor) {
		return executor.submit(urlTextSearchExecutor);
	}
}
