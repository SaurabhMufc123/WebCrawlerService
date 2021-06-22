package com.application.controller.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.application.exception.CrawlerException;

@ControllerAdvice
public class ControllerAdvise extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { CrawlerException.class })
	protected ResponseEntity<Object> handleCrawException(RuntimeException ex, WebRequest request) {
		return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR,
				request);
	}

	@ExceptionHandler(value = { Exception.class })
	protected ResponseEntity<Object> handleEception(RuntimeException ex, WebRequest request) {
		String bodyOfResponse = "unknown error";
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR,
				request);
	}
}
