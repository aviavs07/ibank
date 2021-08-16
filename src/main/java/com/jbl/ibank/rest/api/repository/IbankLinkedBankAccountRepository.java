package com.jbl.ibank.rest.api.repository;

import java.util.List;

import com.jbl.ibank.rest.api.model.IbankLinkedBankAccount;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IbankLinkedBankAccountRepository extends JpaRepository<IbankLinkedBankAccount, Integer>{
    
    IbankLinkedBankAccount findByAccountNumber(String accountNumber);

    List<IbankLinkedBankAccount> findByMobileNumber(String mobileNumber);
}
