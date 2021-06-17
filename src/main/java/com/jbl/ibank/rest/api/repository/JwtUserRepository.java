package com.jbl.ibank.rest.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jbl.ibank.rest.api.model.JwtUser;

public interface JwtUserRepository extends JpaRepository<JwtUser, Long> {
	JwtUser findByUsername(String username);
}
