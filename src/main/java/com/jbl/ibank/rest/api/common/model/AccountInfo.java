package com.jbl.ibank.rest.api.common.model;

public class AccountInfo {

	private String message;
	private int responseCode;
	private boolean isValid;
	private String accountNo;
	private String leagcyAccountNo;
	private String accountTitle;
	private String coCode;
	private String coName;
	private String balance;

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

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getLeagcyAccountNo() {
		return leagcyAccountNo;
	}

	public void setLeagcyAccountNo(String leagcyAccountNo) {
		this.leagcyAccountNo = leagcyAccountNo;
	}

	public String getAccountTitle() {
		return accountTitle;
	}

	public void setAccountTitle(String accountTitle) {
		this.accountTitle = accountTitle;
	}

	public String getCoCode() {
		return coCode;
	}

	public void setCoCode(String coCode) {
		this.coCode = coCode;
	}

	public String getCoName() {
		return coName;
	}

	public void setCoName(String coName) {
		this.coName = coName;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}
}
