package com.jbl.ibank.rest.api.common.model;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

public class FtResponse {

	private HttpStatus status;
	private String message;
	private int responseCode;
	private String remittanceUniqueId;
	private String ftRef;
	private int creditAccountCategory;
	private String additionalInfo;

	@JsonFormat(pattern = "E, DD MMM Y HH:mm:ss z", timezone = "GMT+06")
	private Timestamp timestamp = new Timestamp(new Date().getTime());

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

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public String getRemittanceUniqueId() {
		return remittanceUniqueId;
	}

	public void setRemittanceUniqueId(String remittanceUniqueId) {
		this.remittanceUniqueId = remittanceUniqueId;
	}

	public String getFtRef() {
		return ftRef;
	}

	public void setFtRef(String ftRef) {
		this.ftRef = ftRef;
	}

	public int getCreditAccountCategory() {
		return creditAccountCategory;
	}

	public void setCreditAccountCategory(int creditAccountCategory) {
		this.creditAccountCategory = creditAccountCategory;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

}
