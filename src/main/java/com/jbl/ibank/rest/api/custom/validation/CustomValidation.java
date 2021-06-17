package com.jbl.ibank.rest.api.custom.validation;

import org.springframework.beans.factory.annotation.Autowired;

import com.jbl.ibank.rest.api.model.JwtUser;
import com.jbl.ibank.rest.api.service.JwtUserService;

public class CustomValidation {

	@Autowired
	private JwtUserService jwtUserService;

	public boolean isBlankString(String string) {
		return string == null || string.trim().isEmpty();
	}
	
	public boolean isBlankDouble(Double doubleValue) {
		return doubleValue == null || doubleValue <= 0;
	}

	public boolean isRequiredLength(String string, int length) {

		String trimString = string.trim();

		return (trimString.length() == length);
	}

	public boolean isAccountLengthValid(String string) {

		String trimString = string.trim();

		int strLength = trimString.length();

		if (strLength == 12 || strLength == 13 || strLength == 16) {
			return true;
		} else {
			return false;
		}

	}

	public boolean checkRequestIP(String username, String remoteAddr) {
		try {
			JwtUser jwtUser = jwtUserService.findByUserName(username);
			String allowIP = jwtUser.getWhitelistIp().trim();
			if (allowIP.equals(remoteAddr)) {
				return true;
			}
			return false;
		} catch (Exception e) {

		}
		return false;
	}
}
