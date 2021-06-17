package com.jbl.ibank.rest.api.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.jbl.ibank.rest.api.annotation.BankIdUnique;
import com.jbl.ibank.rest.api.annotation.EmailUnique;
import com.jbl.ibank.rest.api.annotation.ValidPassword;
import com.jbl.ibank.rest.api.audit.Auditable;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@PasswordValueMatch.List({
//		@PasswordValueMatch(field = "userPassword", fieldMatch = "userConfirmPassword", message = "Passwords do not match!") })
@Entity
@Table(name = "user_accounts")
@NamedQuery(name = "UserAccount.findAll", query = "SELECT u FROM UserAccount u")
@Getter
@Setter
@NoArgsConstructor
@Audited
@DynamicUpdate
public class UserAccount extends Auditable implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param firstName
	 * @param lastName
	 * @param userEmail
	 * @param userPassword
	 * @param enabled
	 * @param isUsing2fa
	 * @param secret
	 * @param userBankId
	 */
	public UserAccount(String firstName, String lastName, String userEmail, String userPassword, boolean enabled,
			boolean isUsing2fa, String secret, String userBankId) {
		this.enabled = enabled;
		this.firstName = firstName;
		this.isUsing2fa = isUsing2fa;
		this.lastName = lastName;
		this.secret = secret;
		this.userBankId = userBankId;
		this.userEmail = userEmail;
		this.userPassword = userPassword;

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", unique = true, nullable = false)
	private int userId;

	private boolean enabled;

	@Column(name = "first_name", length = 45)
	@NotBlank(message = "First Name is Required!")
	private String firstName;

	@Column(name = "mobile_number", length = 11)
	@NotBlank(message = "Please enter your mobile number")
	@Size(min = 11, max = 11, message = "Mobile number must be 11 digit.")
	@Pattern(regexp = "(^$|[0][1][0-9]{9})", message = "Please enter valid mobile number.")
	private String mobileNumber;

	@Column(name = "is_using2fa")
	private boolean isUsing2fa;

	@NotBlank(message = "Last Name is Required!")
	@Column(name = "last_name", length = 45)
	private String lastName;

	@Column(length = 55)
	private String secret;

	@Column(name = "user_bank_id", unique = true)
	@BankIdUnique(groups = OnCreate.class)
	@NotBlank(message = "Bank ID is Required!")
	@Size(min = 6, max = 6, message = "Bank ID must be 6 digit.")
	@Pattern(regexp = "(^$|[0-9]{6})", message = "Please enter valid Bank ID")
	private String userBankId;

	@EmailUnique(groups = OnCreate.class)
	@NotBlank(message = "Email is Required!")
	@Email(message = "Email is invalid")
	@Column(name = "user_email", length = 63, unique = true)
	private String userEmail;

	@ValidPassword(groups = OnCreate.class)
	@Column(name = "user_password", length = 255)
	private String userPassword;

	@Transient
	private String userConfirmPassword;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	@JoinTable(name = "users_roles", joinColumns = {
			@JoinColumn(name = "user_id", nullable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "role_id", nullable = false) })
	@NotAudited
	private List<Role> roles;

	@Column(name = "account_non_locked")
	private boolean accountNonLocked;

	@Column(name = "failed_attempt", columnDefinition = "int default 0")
	private int failedAttempt;

	@Column(name = "lock_time", columnDefinition = "timestamp NULL")
	private Timestamp lockTime;

	@Column(name = "whitelist_ip", length = 255)
	private String whitelistIp;

	@OneToMany(fetch = FetchType.LAZY) // ADD by Avijit
	@JoinColumn(name = "created_by")
	@NotAudited
	private List<UserAccount> userAccounts;

	@OneToMany(fetch = FetchType.LAZY) // ADD by Avijit
	@JoinColumn(name = "created_by")
	@NotAudited
	private List<Role> roles2;

	@OneToMany(fetch = FetchType.LAZY) // ADD by Avijit
	@JoinColumn(name = "created_by")
	@NotAudited
	private List<JwtUser> jwtUsers;

	public void addUserAccount(UserAccount theUserAccount) {
		userAccounts.add(theUserAccount);
	}

	public void addJwtUser(JwtUser theJwtUser) {
		jwtUsers.add(theJwtUser);
	}

	public void addRoles(Role theRole) { // ADD by Avijit
		this.roles.add(theRole);
	}

	public void removeRoles(Role theRole) { // ADD by Avijit
		this.roles.remove(theRole);
	}

	public void removeAllRoles(List<Role> allRole) { // ADD by Avijit
		this.roles.removeAll(allRole);
	}

}