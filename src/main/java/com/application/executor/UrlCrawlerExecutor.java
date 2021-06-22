package com.application.executor;

import java.util.HashSet;
import java.util.concurrent.Callable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.application.config.PropertiesConfig;

public class UrlCrawlerExecutor implements Callable<HashSet<String>> {
	private String url;

	// URL crawl counter to break when maximum limit is reached
	private int crawled = 0;
	private HashSet<String> urlSet = new HashSet<String>();
	Logger logger = LoggerFactory.getLogger(UrlCrawlerExecutor.class);

	public UrlCrawlerExecutor(String url) {
		super();
		this.url = url;
	}

	public HashSet<String> call() throws Exception {
		// Add base url
		crawled++;

		// traverse child urls
		this.breakdownUrls(url, 0);

		return urlSet;
	}

	/**
	 * 
	 * @param url
	 * @param depth
	 * @return Set of extracted URLs
	 */
	public void breakdownUrls(String url, int depth) {
		if (!urlSet.contains(url) && (depth < PropertiesConfig.MAX_CRAWLING_DEPTH)) {
			try {
				Document document = Jsoup.connect(url).get();
				// Find all the URLs in the html
				Elements internalUrls = document.select("a[href]");
				urlSet.add(url);
				depth++;

				// Search for urls on this page
				for (Element internalUrl : internalUrls) {
					if (crawled > PropertiesConfig.MAX_URL_TO_CRAWL_PER_JOB) {
						logger.info("max crawl url limit reached");
						return;
					}

					String childUrl = internalUrl.attr("abs:href");
					urlSet.add(childUrl);
					crawled++;

					logger.info("url found: {}", childUrl);

					// Further breakdown URLs upto the defined depth
					breakdownUrls(childUrl, depth);
				}
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
		}
	}

}
