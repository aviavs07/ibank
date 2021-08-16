package com.jbl.ibank.rest.api.service;

import java.util.List;

import com.jbl.ibank.rest.api.model.IbankLinkedBankAccount;
import com.jbl.ibank.rest.api.repository.IbankLinkedBankAccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
// @Transactional(readOnly = true)
public class IbankLinkedBankAccountService {

    @Autowired
    private IbankLinkedBankAccountRepository ibankLinkedBankAccountRepository;

    // @Transactional(readOnly = false)
	public IbankLinkedBankAccount save(IbankLinkedBankAccount entity) {
		return ibankLinkedBankAccountRepository.save(entity);
	}
    
    public IbankLinkedBankAccount findByAccountNumber(String accountNumber) {
		return ibankLinkedBankAccountRepository.findByAccountNumber(accountNumber);
	}

	public List<IbankLinkedBankAccount> findAll() {
		return ibankLinkedBankAccountRepository.findAll();
	} 

	public List<IbankLinkedBankAccount> findByMobileNumber(String mobileNumber) {
		return ibankLinkedBankAccountRepository.findByMobileNumber(mobileNumber);
	} 
}
