package com.jbl.ibank.rest.api.enums;

public enum CBSResponseStr {

	alreadySuccessDuplicate("ALREADY.SUCCESS.DUPLICATE.AT.UNIQUE.ID"), 
	failedDuplicate("FAILED.DUPLICATE.AT.UNIQUE.ID"),
	debitAccountMissing("DEBIT.ACCT.NO:1:1=MISSING ACCOUNT - RECORD"),
	creditAccountMissing("CREDIT.ACCT.NO:1:1=MISSING ACCOUNT - RECORD"),
	debitAccountLessBalance("POSITION.TYPE:1:1=WITHDRAWL MAKES A/C BAL LESS THAN MIN BAL"),
	accountNaturePersonal("FRD.AC.NATURE=Personal"),
	customDebitAccountMissing("CUSTOM.DEBIT.ACCOUNT.MISSING"),
	customCreditAccountMissing("CUSTOM.CREDIT.ACCOUNT.MISSING"),
	customDebitAccountCoCodeMissing("CUSTOM.DEBIT.CO.CODE.MISSING");

	private final String text;

	CBSResponseStr(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

}
