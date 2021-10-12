package com.jbl.ibank.rest.api.model;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ibank_tr_info")
//@Audited
public class IbankTransactionInfo {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ibank_tr_id", unique = true, nullable = false)
	private int ibankTrId;

	@Column(name = "narrative", unique = true, nullable = false, length = 23)
	private String narrative;

	// @Column(name = "version", nullable = false, length = 1)
	// private Integer version;

	@Column(name = "debit_account", length = 63)
	private String debitAccNo;

	@Column(name = "credit_account", length = 23)
	private String creditAccNo;

	@Column(name = "debit_amount", length = 23)
	private Double debitAmount;

	@Column(name = "issue_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
	private Timestamp issueDate;

	@Column(name = "ofs_request", length = 1048)
	private String ofsRequest;

	@Column(name = "ofs_response", length = 2048)
	private String ofsResponse;

	@Column(name = "status", length = 2)
	private int status;

	@Column(name = "ip", length = 63)
	private String ip;

	@Column(name = "currency", length = 5)
	private String currency;

	@Column(name = "hostname", length = 127)
	private String hostName;

//    @Column(name = "hostname", length = 127)
//	private String hostName;
}