package com.jbl.ibank.rest.api.common.model;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

public class JwtErrorResponse {

	private HttpStatus status;
	private String message;
	private int responseCode;
	
	@JsonFormat(pattern="E, DD MMM Y HH:mm:ss z", timezone = "GMT+06")
	private Timestamp timestamp = new Timestamp(new Date().getTime());

	public JwtErrorResponse(HttpStatus status, String message, int responseCode) {
		this.status = status;
		this.message = message;
		this.responseCode = responseCode;
	}

	public JwtErrorResponse(HttpStatus status, String message, int responseCode, Timestamp timestamp) {
		this.status = status;
		this.message = message;
		this.responseCode = responseCode;
		this.timestamp = timestamp;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

}
