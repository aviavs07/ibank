package com.jbl.ibank.rest.api.service;



import com.jbl.ibank.rest.api.model.IbankUser;
import com.jbl.ibank.rest.api.model.IbankUserAccountDetails;
import com.jbl.ibank.rest.api.model.JwtUser;
import com.jbl.ibank.rest.api.model.JwtUserAccountDetails;
import com.jbl.ibank.rest.api.repository.IbankUserRepository;
import com.jbl.ibank.rest.api.repository.JwtUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service("IbankUser")
public class IbankUserDetailsService implements UserDetailsService {

	@Autowired
	private IbankUserRepository ibankUserRepository;


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		IbankUser user = ibankUserRepository.findByMobileNumber(username);

		if (user == null) {
			throw new UsernameNotFoundException("Not found: " + username);
		}

		return new IbankUserAccountDetails(user);
	}

}
