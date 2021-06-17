package com.jbl.ibank.rest.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jbl.ibank.rest.api.model.JwtUser;
import com.jbl.ibank.rest.api.repository.JwtUserRepository;

@Service
@Transactional(readOnly = true)
public class JwtUserService {

	@Autowired
	private JwtUserRepository jwtuserRepository;

	public List<JwtUser> findAll() {
		return jwtuserRepository.findAll();
	}

	public JwtUser findOne(Long id) {
		return jwtuserRepository.getOne(id);
	}
	
	@Transactional(readOnly = false)
	public JwtUser save(JwtUser entity) {
		return jwtuserRepository.save(entity);
	}

	@Transactional(readOnly = false)
	public void delete(JwtUser entity) {
		jwtuserRepository.delete(entity);
	}
	
	public JwtUser findByUserName(String userName) {
		return jwtuserRepository.findByUsername(userName);
	}

}
	
