package com.jbl.ibank.rest.api.utils;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.jbl.ibank.rest.api.annotation.EmailUnique;
import com.jbl.ibank.rest.api.model.UserAccount;
import com.jbl.ibank.rest.api.service.UserAccountService;

import org.springframework.beans.factory.annotation.Autowired;

public class EmailUniqueConstraintValidator implements ConstraintValidator<EmailUnique, String> {

	// private String emailAddress;

	@Autowired
	private UserAccountService useraccountService;

	@Override
	public void initialize(EmailUnique theEmail) {
		// emailAddress = theEmail.value();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		boolean result = true;

		if (value != null) {
			try {
				UserAccount user = useraccountService.findOneByEmail(value);

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