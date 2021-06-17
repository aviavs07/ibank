package com.jbl.ibank.rest.api.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MyUserAccountDetails implements UserDetails {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private UserAccount user;
	private String userName;
	private int userId;
	private String password;

	public MyUserAccountDetails(UserAccount user) {
		this.user = user;
		this.userName = user.getUserEmail();
		this.password = user.getUserPassword();
		this.userId = user.getUserId();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		List<Role> roles = user.getRoles();

		List<SimpleGrantedAuthority> authorities = new ArrayList<>();

		for (Role role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
		}

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
		return user.isAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFullName() {
        return this.user.getFirstName()+' '+this.user.getLastName();
    }

}
