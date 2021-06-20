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
@Table(name = "bank_branch")
@NamedQuery(name = "BankBranch.findAll", query = "SELECT b FROM BankBranch b")
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor
@Audited
public class BankBranch extends Auditable implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "branch_id", unique = true, nullable = false)
    private int branchId;

    @Column(name = "branch_name", length = 30)
	@NotBlank(message = "Name is Required!")
	private String branchName;

    @Column(name = "branch_code", length = 10)
	private String branchCode;

    @Column(name = "routing_no", length = 10)
	private String routingNo;

    @Column(name = "swift_code", length = 10)
	private String swiftCode;

    @Column(name = "address", length = 30)
	private String address;

    private boolean status;
}
