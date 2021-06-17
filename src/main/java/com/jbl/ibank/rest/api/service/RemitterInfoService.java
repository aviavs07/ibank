package com.jbl.ibank.rest.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jbl.ibank.rest.api.model.JwtUser;
import com.jbl.ibank.rest.api.model.RemitterInfo;
import com.jbl.ibank.rest.api.repository.RemitterInfoRepository;

@Service
@Transactional(readOnly = true)
public class RemitterInfoService {

	@Autowired
	private RemitterInfoRepository remitterInfoRepository;

	@Transactional(readOnly = false)
	public RemitterInfo save(RemitterInfo entity) {
		return remitterInfoRepository.save(entity);
	}
		
	public RemitterInfo findByRemittanceUniqueId(String remittanceUniqueId) {
		return remitterInfoRepository.findByRemittanceUniqueId(remittanceUniqueId);
	}
	
	
	

}
