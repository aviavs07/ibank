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
@Table(name = "bank_lists")
@NamedQuery(name = "BankList.findAll", query = "SELECT b FROM BankList b")
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor
@Audited
public class BankList extends Auditable implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bank_id", unique = true, nullable = false)
    private int bankId;

    @Column(name = "bank_name", length = 30)
	@NotBlank(message = "Name is Required!")
	private String bankName;
}
