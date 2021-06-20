package com.jbl.ibank.rest.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.jbl.ibank.rest.api.audit.Auditable;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.envers.Audited;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "benificiary_accounts")
@NamedQuery(name = "BenificiaryAccount.findAll", query = "SELECT i FROM BenificiaryAccount i")
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor
@Audited
public class BenificiaryAccount extends Auditable implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "benificiary_id", unique = true, nullable = false)
    private int benificiaryId;

    @Column(name = "ibank_user_id", nullable = false)
    private IbankUser ibankUserId;

    @Column(name = "account_number", length = 17)
    private String accountNumber;

    @Column(name = "account_name", length = 65)
    @NotBlank(message = "Name is Required!")
    private String accountName;

    @Column(name = "branch_id", nullable = false)
    private BankBranch branchId;

    private boolean status;
}
