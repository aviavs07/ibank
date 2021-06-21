package com.jbl.ibank.rest.api.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class IbankUserAccountDetails implements UserDetails {
    private static final long serialVersionUID = 1L;
	private IbankUser user;
	private String userName;
	private int userId;
	private String password;
	private String fullName;

	public IbankUserAccountDetails(IbankUser user) {
		this.user = user;
		this.userName = user.getMobileNumber();
		this.password = user.getPassword();
		this.userId = user.getIbankUserId();
		this.fullName = user.getFullName();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public String getFullName() {
        return this.user.getFullName();
    }
}
