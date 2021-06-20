package com.jbl.ibank.rest.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.jbl.ibank.rest.api.annotation.NameUnique;
import com.jbl.ibank.rest.api.annotation.ValidPassword;
import com.jbl.ibank.rest.api.audit.Auditable;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.envers.Audited;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "jwt_user")
@NamedQuery(name = "JwtUser.findAll", query = "SELECT j FROM JwtUser j")
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor
@Audited
public class JwtUser extends Auditable implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "jwt_user_id", unique = true, nullable = false)
    private int jwtUserId;


	 @NameUnique(groups = OnCreate.class)
    @Column(name = "username", length = 45, unique = true)
    private String username;

	@ValidPassword(groups = OnCreate.class)
    @Column(length = 255)
    private String password;

	@Transient
    private String confirmPassword;

	@Column(name = "basic_token", length = 255)
    private String basicToken;

	private Boolean status;

	@Column(name = "session_time", length = 11)
    private Integer SessionTime;	

	@Column(name = "whitelist_ip", length = 255)
	private String whitelistIp;

	// public long getJwtUserId() {
	// 	return jwtUserId;
	// }

	// public void setJwtUserId(long jwtUserId) {
	// 	this.jwtUserId = jwtUserId;
	// }

	// public Boolean getStatus() {
	// 	return status;
	// }

	// public void setStatus(Boolean status) {
	// 	this.status = status;
	// }

	// // need default constructor for JSON Parsing
	// public JwtUser() {

	// }

	// public JwtUser(String username, String password) {
	// 	this.setUsername(username);
	// 	this.setPassword(password);
	// }

	// public String getUsername() {
	// 	return this.username;
	// }

	// public void setUsername(String username) {
	// 	this.username = username;
	// }

	// public String getPassword() {
	// 	return this.password;
	// }

	// public void setPassword(String password) {
	// 	this.password = password;
	// }

	// public String getBasic_token() {
	// 	return basic_token;
	// }

	// public void setBasic_token(String basic_token) {
	// 	this.basic_token = basic_token;
	// }

	// public String getWhitelistIp() {
	// 	return whitelistIp;
	// }

	// public void setWhitelistIp(String whitelistIp) {
	// 	this.whitelistIp = whitelistIp;
	// }

}