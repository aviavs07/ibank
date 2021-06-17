package com.jbl.ibank.rest.api.utils;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.jbl.ibank.rest.api.annotation.NameUnique;
import com.jbl.ibank.rest.api.model.JwtUser;
import com.jbl.ibank.rest.api.service.JwtUserService;

import org.springframework.beans.factory.annotation.Autowired;

public class NameUniqueConstraintValidator implements ConstraintValidator<NameUnique, String> {

	// private String emailAddress;

	@Autowired
	private JwtUserService jwtUserService;

	@Override
	public void initialize(NameUnique valueObject) {
		// emailAddress = theEmail.value();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		boolean result = true;

		if (value != null) {
			try {
				JwtUser user = jwtUserService.findByUserName(value);
				if (user == null) {
					result = true;
				} else {
					result = false;
				}
			} catch (NullPointerException exception) {

			}
		}
		return result;
	}

}
