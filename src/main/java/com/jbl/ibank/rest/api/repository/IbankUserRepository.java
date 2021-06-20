package com.jbl.ibank.rest.api.repository;



import com.jbl.ibank.rest.api.model.IbankUser;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IbankUserRepository extends JpaRepository<IbankUser, Integer> {
   
}
