package com.jbl.ibank.rest.api.repository;

import com.jbl.ibank.rest.api.model.BankBranch;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankBranchRepository extends JpaRepository<BankBranch, Integer>{

    BankBranch findByBranchCode(String branchCode);
}
