package com.jbl.ibank.rest.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jbl.ibank.rest.api.model.RemitterInfo;

public interface RemitterInfoRepository extends JpaRepository<RemitterInfo, Integer> {

	RemitterInfo findByRemittanceUniqueId(String remittanceUniqueId);

}
