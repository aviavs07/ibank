package com.jbl.ibank.rest.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.jbl.ibank.rest.api.audit.Auditable;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.envers.Audited;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ibank_linked_bank_account")
@NamedQuery(name = "IbankLinkedBankAccount.findAll", query = "SELECT i FROM IbankLinkedBankAccount i")
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor
@Audited
public class IbankLinkedBankAccount extends Auditable implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ibank_linked_bank_ac_id", unique = true, nullable = false)
    private int ibankLinkedBankAcId;

    @Column(name = "ibank_user_id", nullable = false)
    private Integer ibankUserId;

    @Column(name = "account_number", length = 17)
    private String accountNumber;

    @Column(name = "account_name", length = 65)
    @NotBlank(message = "Name is Required!")
    private String accountName;

    @Column(name = "mobile_number", length = 11)
	@NotBlank(message="Please enter your mobile number")
	@Size(min = 11, max = 11, message = "Mobile number must be 11 digit.")
	@Pattern(regexp="(^$|[0][1][0-9]{9})", message = "Please enter valid mobile number." )
	private String mobileNumber;

    @Column(name = "nid", length = 20)
    private String nid;

    @Column(name = "branch_id", nullable = false)
    private String branchId;
    // private BankBranch branchId;

    private boolean status;

    @Lob
    byte[] accountPicture;

    public byte[] getContent() {
        return null;
    }
    

   

   


    
}
