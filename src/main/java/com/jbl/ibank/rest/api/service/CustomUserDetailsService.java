package com.jbl.ibank.rest.api.service;

import com.jbl.ibank.rest.api.model.MyUserAccountDetails;
import com.jbl.ibank.rest.api.model.UserAccount;
import com.jbl.ibank.rest.api.model.JwtUser;
import com.jbl.ibank.rest.api.repository.JwtUserRepository;
import com.jbl.ibank.rest.api.repository.UserAccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserAccountRepository userAccountRepository;


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserAccount user = userAccountRepository.findByUserEmail(username);

		if (user == null) {
			throw new UsernameNotFoundException("Not found: " + username);
		}

		return new MyUserAccountDetails(user);
	}

}
