package com.jbl.ibank.rest.api.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "remitter_info")
@Audited
public class RemitterInfo {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "remitter_id", unique = true, nullable = false)
	private int remitterId;

	@Column(name = "remittance_unique_id", length = 23)
	private String remittanceUniqueId;

	@Column(name = "remittance_no", length = 63)
	private String remittanceNo;
	
	@Column(name = "exchange_house_name", length = 128)
	private String exchangeHouseName;

	@Column(name = "pin_code", length = 63)
	private String pinCode;

	@Column(name = "remittance_type", length = 63)
	private String remittanceType;

	@Column(name = "remitter_name", length = 63)
	private String remitterName;

	@Column(name = "remitter_gender", length = 15)
	private String remitterGender;

	@Column(name = "occupation", length = 63)
	private String occupation;

	@Column(name = "date_of_birth", length = 23)
	private String dateOfBirth;

	@Column(name = "nationality", length = 63)
	private String nationality;

	@Column(name = "remitter_district", length = 63)
	private String remitterDistrict;

	@Column(name = "passport_number", length = 63)
	private String passportNumber;

	@Column(name = "employment_card", length = 63)
	private String employmentCard;

	@Column(name = "remitter_document_type", length = 63)
	private String remitterDocumentType;

	@Column(name = "remitter_document_number", length = 63)
	private String remitterDocumentNumber;

	@Column(name = "originating_country", length = 63)
	private String originatingCountry;

	@Column(name = "relationship_with_beneficiary", length = 63)
	private String relationshipWithBeneficiary;

	@Column(name = "purpose_of_remittance", length = 63)
	private String purposeOfRemittance;

	@Column(name = "covered_account", length = 63)
	private String coveredAccount;

	@Column(name = "covered_account_co_code", length = 63)
	private String coveredAccountCoCode;

	@Column(name = "beneficiary_name", length = 63)
	private String beneficiaryName;

	@Column(name = "beneficiary_gender", length = 15)
	private String beneficiaryGender;

	@Column(name = "beneficiary_district", length = 63)
	private String beneficiaryDistrict;

	@Column(name = "beneficiary_id_type", length = 63)
	private String beneficiaryIdType;

	@Column(name = "beneficiary_id_no", length = 63)
	private String beneficiaryIdNo;

	@Column(name = "account_type", length = 23)
	private String accountType;

	@Column(name = "account_no", length = 23)
	private String accountNo;

	@Column(name = "bank_code", length = 15)
	private String bankCode;

	@Column(name = "bank_name", length = 63)
	private String bankName;

	@Column(name = "branch_code", length = 15)
	private String branchCode;

	@Column(name = "branch_name", length = 63)
	private String branchName;

	@Column(name = "beneficiary_mobile_number", length = 23)
	private String beneficiaryMobileNumber;

	@Column(name = "issue_date", length = 63)
	private String issueDate;

	@Column(name = "amount_in_bdt", length = 23)
	private Double amountInBDT;

	@Column(name = "ex_rate", length = 23)
	private Double exRate;

	@Column(name = "amount_in_usd", length = 23)
	private Double amountInUSD;

	@Column(name = "sending_country_currency_name", length = 15)
	private String sendingCountryCurrencyName;

	@Column(name = "amount_in_sending_country_currency", length = 23)
	private Double amountInSendingCountryCurrency;

	@Column(name = "commission", length = 23)
	private Double commission;

	@Column(name = "name_of_payment_system", length = 63)
	private String nameOfPaymentSystem;

	@Column(name = "remarks", length = 127)
	private String remarks;
	
	@Column(name = "ofs_request", length = 1048)
    private String ofsRequest;

    @Column(name = "ofs_response", length = 2048)
    private String ofsResponse;

	@Column(name = "status", length = 2)
	private int status;

	@Column(name = "ip", length = 63)
	private String ip;

	@Column(name = "hostname", length = 127)
	private String hostName;

	@Column(name = "request_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
	private Timestamp requestAt;

	@CreationTimestamp
	@Column(name = "response_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = true)
	private Timestamp responseAt;

	public int getRemitterId() {
		return remitterId;
	}

	public void setRemitterId(int remitterId) {
		this.remitterId = remitterId;
	}

	public String getRemittanceNo() {
		return remittanceNo;
	}

	public void setRemittanceNo(String remittanceNo) {
		this.remittanceNo = remittanceNo;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getRemittanceType() {
		return remittanceType;
	}

	public void setRemittanceType(String remittanceType) {
		this.remittanceType = remittanceType;
	}

	public String getRemitterName() {
		return remitterName;
	}

	public void setRemitterName(String remitterName) {
		this.remitterName = remitterName;
	}

	public String getRemitterGender() {
		return remitterGender;
	}

	public void setRemitterGender(String remitterGender) {
		this.remitterGender = remitterGender;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getRemitterDistrict() {
		return remitterDistrict;
	}

	public void setRemitterDistrict(String remitterDistrict) {
		this.remitterDistrict = remitterDistrict;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public String getEmploymentCard() {
		return employmentCard;
	}

	public void setEmploymentCard(String employmentCard) {
		this.employmentCard = employmentCard;
	}

	public String getRemitterDocumentType() {
		return remitterDocumentType;
	}

	public void setRemitterDocumentType(String remitterDocumentType) {
		this.remitterDocumentType = remitterDocumentType;
	}

	public String getRemitterDocumentNumber() {
		return remitterDocumentNumber;
	}

	public void setRemitterDocumentNumber(String remitterDocumentNumber) {
		this.remitterDocumentNumber = remitterDocumentNumber;
	}

	public String getOriginatingCountry() {
		return originatingCountry;
	}

	public void setOriginatingCountry(String originatingCountry) {
		this.originatingCountry = originatingCountry;
	}

	public String getRelationshipWithBeneficiary() {
		return relationshipWithBeneficiary;
	}

	public void setRelationshipWithBeneficiary(String relationshipWithBeneficiary) {
		this.relationshipWithBeneficiary = relationshipWithBeneficiary;
	}

	public String getPurposeOfRemittance() {
		return purposeOfRemittance;
	}

	public void setPurposeOfRemittance(String purposeOfRemittance) {
		this.purposeOfRemittance = purposeOfRemittance;
	}

	public String getCoveredAccount() {
		return coveredAccount;
	}

	public void setCoveredAccount(String coveredAccount) {
		this.coveredAccount = coveredAccount;
	}

	public String getCoveredAccountCoCode() {
		return coveredAccountCoCode;
	}

	public void setCoveredAccountCoCode(String coveredAccountCoCode) {
		this.coveredAccountCoCode = coveredAccountCoCode;
	}

	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	public String getBeneficiaryGender() {
		return beneficiaryGender;
	}

	public void setBeneficiaryGender(String beneficiaryGender) {
		this.beneficiaryGender = beneficiaryGender;
	}

	public String getBeneficiaryDistrict() {
		return beneficiaryDistrict;
	}

	public void setBeneficiaryDistrict(String beneficiaryDistrict) {
		this.beneficiaryDistrict = beneficiaryDistrict;
	}

	public String getBeneficiaryIdType() {
		return beneficiaryIdType;
	}

	public void setBeneficiaryIdType(String beneficiaryIdType) {
		this.beneficiaryIdType = beneficiaryIdType;
	}

	public String getBeneficiaryIdNo() {
		return beneficiaryIdNo;
	}

	public void setBeneficiaryIdNo(String beneficiaryIdNo) {
		this.beneficiaryIdNo = beneficiaryIdNo;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBeneficiaryMobileNumber() {
		return beneficiaryMobileNumber;
	}

	public void setBeneficiaryMobileNumber(String beneficiaryMobileNumber) {
		this.beneficiaryMobileNumber = beneficiaryMobileNumber;
	}

	public String getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	public Double getAmountInBDT() {
		return amountInBDT;
	}

	public void setAmountInBDT(Double amountInBDT) {
		this.amountInBDT = amountInBDT;
	}

	public Double getExRate() {
		return exRate;
	}

	public void setExRate(Double exRate) {
		this.exRate = exRate;
	}

	public Double getAmountInUSD() {
		return amountInUSD;
	}

	public void setAmountInUSD(Double amountInUSD) {
		this.amountInUSD = amountInUSD;
	}

	public String getSendingCountryCurrencyName() {
		return sendingCountryCurrencyName;
	}

	public void setSendingCountryCurrencyName(String sendingCountryCurrencyName) {
		this.sendingCountryCurrencyName = sendingCountryCurrencyName;
	}

	public Double getAmountInSendingCountryCurrency() {
		return amountInSendingCountryCurrency;
	}

	public void setAmountInSendingCountryCurrency(Double amountInSendingCountryCurrency) {
		this.amountInSendingCountryCurrency = amountInSendingCountryCurrency;
	}

	public Double getCommission() {
		return commission;
	}

	public void setCommission(Double commission) {
		this.commission = commission;
	}

	public String getNameOfPaymentSystem() {
		return nameOfPaymentSystem;
	}

	public void setNameOfPaymentSystem(String nameOfPaymentSystem) {
		this.nameOfPaymentSystem = nameOfPaymentSystem;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public Timestamp getRequestAt() {
		return requestAt;
	}

	public void setRequestAt(Timestamp requestAt) {
		this.requestAt = requestAt;
	}

	public Timestamp getResponseAt() {
		return responseAt;
	}

	public void setResponseAt(Timestamp responseAt) {
		this.responseAt = responseAt;
	}

	public String getRemittanceUniqueId() {
		return remittanceUniqueId;
	}

	public void setRemittanceUniqueId(String remittanceUniqueId) {
		this.remittanceUniqueId = remittanceUniqueId;
	}

	public String getOfsRequest() {
		return ofsRequest;
	}

	public void setOfsRequest(String ofsRequest) {
		this.ofsRequest = ofsRequest;
	}

	public String getOfsResponse() {
		return ofsResponse;
	}

	public void setOfsResponse(String ofsResponse) {
		this.ofsResponse = ofsResponse;
	}

	public String getExchangeHouseName() {
		return exchangeHouseName;
	}

	public void setExchangeHouseName(String exchangeHouseName) {
		this.exchangeHouseName = exchangeHouseName;
	}
	
	

}
