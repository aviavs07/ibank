package com.jbl.ibank.rest.api.service;

import com.jbl.ibank.rest.api.model.BankBranch;
import com.jbl.ibank.rest.api.repository.BankBranchRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankBranchService {

  @Autowired
  private BankBranchRepository bankBranchRepository;

  public BankBranch save(BankBranch entity) {
    return bankBranchRepository.save(entity);
  }

  public BankBranch findByBranchCode(String branchCode) {
		return bankBranchRepository.findByBranchCode(branchCode);
	}

}
