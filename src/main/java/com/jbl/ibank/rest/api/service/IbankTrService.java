package com.jbl.ibank.rest.api.service;


import com.jbl.ibank.rest.api.model.IbankTransactionInfo;
import com.jbl.ibank.rest.api.repository.IbankTrRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional(readOnly = true)
public class IbankTrService {
	
	// @Autowired
	// private RestTemplate restTemplate;
	
	@Autowired
    private IbankTrRepository ibankTrRepository;
    
	@Transactional(readOnly = false)
	public IbankTransactionInfo save(IbankTransactionInfo entity) {
		return ibankTrRepository.save(entity);
	}
	
	// public BeftnInwardInfo findByNarrative(String narrative) {
	// 	return ibankTrRepository.findByNarrative(narrative);
	// }
	
	// public String getTccOfsResponse(RequestOfs requestOfs) {
		
	// 	String responseData = restTemplate.postForObject("http://TCC-CLIENT/tcc/ofs/",requestOfs, String.class);
	// 	return responseData;
	// }
		

}