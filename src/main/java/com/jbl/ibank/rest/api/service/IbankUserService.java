package com.jbl.ibank.rest.api.service;

import java.util.List;

import javax.transaction.Transactional;

import com.jbl.ibank.rest.api.model.IbankUser;
import com.jbl.ibank.rest.api.repository.IbankUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class IbankUserService {

    @Autowired
	private IbankUserRepository ibankUserRepository;

    public List<IbankUser> findAll() {
		return ibankUserRepository.findAll();
	} 

    public IbankUser findOne(Integer id) {
		return ibankUserRepository.getOne(id);
	}
	
	@Transactional
	public IbankUser save(IbankUser entity) {
		return ibankUserRepository.save(entity);
	}

	@Transactional
	public void delete(IbankUser entity) {
		ibankUserRepository.delete(entity);
	}
	public IbankUser findByUserName(String userName) {
		return ibankUserRepository.findByMobileNumber(userName);
	}
 
}
