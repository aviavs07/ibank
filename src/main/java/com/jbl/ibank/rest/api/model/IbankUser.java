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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.jbl.ibank.rest.api.annotation.EmailUnique;
import com.jbl.ibank.rest.api.annotation.ValidPassword;
import com.jbl.ibank.rest.api.audit.Auditable;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.envers.Audited;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ibank_users")
@NamedQuery(name = "IbankUser.findAll", query = "SELECT i FROM IbankUser i")
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor
@Audited
public class IbankUser extends Auditable implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ibank_user_id", unique = true, nullable = false)
    private int ibankUserId;

    @EmailUnique(groups = OnCreate.class)
	@NotBlank(message = "Email is Required!")
	@Email(message = "Email is invalid")
	@Column(name = "iabnk_user_email", length = 63, unique = true)
	private String ibankUserEmail;

    @Column(name = "full_name", length = 65)
	@NotBlank(message = "Name is Required!")
	private String fullName;

    @Column(name = "whitelist_ip", length = 255)
    private String whitelistIp;

    @Column(name = "jwt_token", length = 255)
    private String jwtToken;

    @Column(name = "last_otp", length = 10)
    private String lastOtp;

    @Column(name = "mac_address", length = 32)
    private String macAddress;

    @Column(name = "mobile_number", length = 11)
	@NotBlank(message="Please enter your mobile number")
	@Size(min = 11, max = 11, message = "Mobile number must be 11 digit.")
	@Pattern(regexp="(^$|[0][1][0-9]{9})", message = "Please enter valid mobile number." )
	private String mobileNumber;

    @Column(name = "nid", length = 20)
    private String nid;

    @Column(name = "user_picture", length = 65)
    private String userPicture;

    @ValidPassword(groups = OnCreate.class)
    @Column(length = 255)
    private String password;

    @Transient
    private String confirmPassword;

    private boolean status;
}
