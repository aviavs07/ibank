package com.jbl.ibank.rest.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.jbl.ibank.rest.api.common.model.JwtErrorResponse;
import com.jbl.ibank.rest.api.enums.ResponseStatus;

@RestControllerAdvice
public class GlobalCtlrAdvice {

	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {

		JwtErrorResponse jwtErrorResponse = new JwtErrorResponse(HttpStatus.BAD_REQUEST,
				ResponseStatus.FOURZ0.getText(), ResponseStatus.FOURZ0.getValue());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jwtErrorResponse);
	}

	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<?> globalExceptionHandlerMediaType(Exception ex, WebRequest request) {

		JwtErrorResponse jwtErrorResponse = new JwtErrorResponse(HttpStatus.FORBIDDEN, ResponseStatus.FOURZ15.getText(),
				ResponseStatus.FOURZ15.getValue());

		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(jwtErrorResponse);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<?> globalExceptionHandlerReadable(Exception ex, WebRequest request) {

		JwtErrorResponse jwtErrorResponse = new JwtErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
				ResponseStatus.FOURZ16.getText(), ResponseStatus.FOURZ16.getValue());

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(jwtErrorResponse);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<?> globalExceptionHandlerReadableRequestMethod(Exception ex, WebRequest request) {

		JwtErrorResponse jwtErrorResponse = new JwtErrorResponse(HttpStatus.FORBIDDEN, ResponseStatus.FOURZ5.getText(),
				ResponseStatus.FOURZ5.getValue());

		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(jwtErrorResponse);
	}

}
