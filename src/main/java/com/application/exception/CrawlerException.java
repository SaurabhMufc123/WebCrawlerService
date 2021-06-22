package com.application.exception;

public class CrawlerException extends RuntimeException {

	public CrawlerException(Throwable t, String message) {
		super(message, t);
	}

	public CrawlerException(String message) {
		super(message);
	}

}
