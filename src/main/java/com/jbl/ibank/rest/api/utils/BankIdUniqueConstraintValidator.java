package com.jbl.ibank.rest.api.utils;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.jbl.ibank.rest.api.annotation.BankIdUnique;
import com.jbl.ibank.rest.api.model.UserAccount;
import com.jbl.ibank.rest.api.service.UserAccountService;

import org.springframework.beans.factory.annotation.Autowired;

public class BankIdUniqueConstraintValidator implements ConstraintValidator<BankIdUnique, String> {

	// private boolean isCreate;

	@Autowired
	private UserAccountService useraccountService;

	@Override
	public void initialize(BankIdUnique bankId) {

		// isCreate = bankId.isCreate();

	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		boolean result = true;

			if (value != null) {
				try {
					UserAccount user = useraccountService.findOneByBankId(value);
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
