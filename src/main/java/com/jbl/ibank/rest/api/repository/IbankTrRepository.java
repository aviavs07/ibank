package com.jbl.ibank.rest.api.repository;

import com.jbl.ibank.rest.api.model.IbankLinkedBankAccount;
import com.jbl.ibank.rest.api.model.IbankTransactionInfo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IbankTrRepository extends JpaRepository<IbankTransactionInfo, Integer>{
    
}
