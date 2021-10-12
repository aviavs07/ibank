package com.jbl.ibank.rest.api.common.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
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

	
}
