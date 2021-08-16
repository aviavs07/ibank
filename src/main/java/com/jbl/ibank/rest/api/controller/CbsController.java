package com.jbl.ibank.rest.api.controller;

import java.sql.Timestamp;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jbl.ibank.rest.api.common.model.AccountInfo;
import com.jbl.ibank.rest.api.common.model.AccountInfoNotFound;
import com.jbl.ibank.rest.api.common.model.CoveredAccountInfo;
import com.jbl.ibank.rest.api.common.model.FtResponse;
import com.jbl.ibank.rest.api.common.model.JwtErrorResponse;
import com.jbl.ibank.rest.api.custom.validation.CustomValidation;
import com.jbl.ibank.rest.api.enums.AccountNature;
import com.jbl.ibank.rest.api.enums.CBSResponseStr;
import com.jbl.ibank.rest.api.enums.RemitterStatus;
import com.jbl.ibank.rest.api.enums.ResponseStatus;
import com.jbl.ibank.rest.api.model.RemitterInfo;
import com.jbl.ibank.rest.api.service.RemitterInfoService;
import com.jbl.ibank.rest.api.tccUtility.TccUtility;

@RestController
@CrossOrigin
@RequestMapping("/cbs")
public class CbsController {

	@Autowired
	private RemitterInfoService remitterInfoService;

	@RequestMapping(value = "/account", method = RequestMethod.GET)
	public ResponseEntity<?> accountInfo(@Valid @NotBlank @RequestParam Map<String, String> requestParams,
			HttpServletRequest httpServletRequest) throws Exception {

				
		String accountNo = requestParams.get("accountNo");
		Objects.requireNonNull(accountNo);

		CustomValidation customValidation = new CustomValidation();

		if (customValidation.isBlankString(accountNo)) {
			JwtErrorResponse jwtErrorResponse = new JwtErrorResponse(HttpStatus.BAD_REQUEST,
					ResponseStatus.FOURZ8.getText(), ResponseStatus.FOURZ8.getValue());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jwtErrorResponse);
		}

		if (!customValidation.isAccountLengthValid(accountNo)) {
			JwtErrorResponse jwtErrorResponse = new JwtErrorResponse(HttpStatus.BAD_REQUEST,
					ResponseStatus.FOURZ12.getText(), ResponseStatus.FOURZ12.getValue());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jwtErrorResponse);
		}

		TccUtility tccUtility = new TccUtility();

		String requestOFS = "ENQUIRY.SELECT,,,E.JBL.API,ACCOUNT.NUMBER:EQ=" + accountNo;
		// String requestOFS = "ENQUIRY.SELECT,,,E.JBL.API,ACCOUNT.NUMBER:EQ=" +
		// accountNo;
		// requestOFS = "ENQUIRY.SELECT,,MONWAR1/Ss1234567*,CURRENCY.LIST";
		// ENQUIRY.SELECT,,MONWAR1/FFSEFDEEE,E.JBL.API,ACCOUNT.NUMBER = 0100001425310

		String responseData = tccUtility.sendRequest(requestOFS);
		System.out.println("responseData " + responseData);

		if (responseData.equals("400") || responseData.equals("401") || responseData.equals("402")
				|| responseData.equals("403")) {
			JwtErrorResponse jwtErrorResponse = new JwtErrorResponse(HttpStatus.OK, ResponseStatus.FIVEZ3.getText(),
					ResponseStatus.FIVEZ3.getValue());

			return ResponseEntity.status(HttpStatus.OK).body(jwtErrorResponse);
		} else {
			try {
				String[] spiltData = responseData.split(",");
				String[] secondPart = spiltData[2].split("\\*");
				String message = secondPart[0];
				String statusCode = secondPart[1];
				if (statusCode.equals("200")) {
					String flagValue = secondPart[2];
					if (flagValue.equals("1")) {
						AccountInfo accountInfo = new AccountInfo();
						accountInfo.setAccountNo(secondPart[3]);
						accountInfo.setLeagcyAccountNo(secondPart[4]);
						accountInfo.setAccountTitle(secondPart[5]);
						// accountInfo.setMobile(secondPart[6]);
						// accountInfo.setNid(secondPart[7]);
						accountInfo.setCoCode(secondPart[8].replace("BD001", ""));
						accountInfo.setCoName(secondPart[9].replace("\"", ""));
						accountInfo.setValid(true);
						accountInfo.setMessage(ResponseStatus.TWOZ0.getText());
						accountInfo.setResponseCode(ResponseStatus.TWOZ0.getValue());
						accountInfo.setBalance("0");
						return ResponseEntity.status(HttpStatus.OK).body(accountInfo);
					} else {
						AccountInfoNotFound accountInfoNotFound = new AccountInfoNotFound(
								ResponseStatus.FOURZ13.getText(), ResponseStatus.FOURZ13.getValue(), false);
						return ResponseEntity.status(HttpStatus.OK).body(accountInfoNotFound);
					}
				} else {
					AccountInfoNotFound accountInfoNotFound = new AccountInfoNotFound(ResponseStatus.FOURZ7.getText(),
							ResponseStatus.FOURZ7.getValue(), false);
					return ResponseEntity.status(HttpStatus.OK).body(accountInfoNotFound);
				}

			} catch (Exception e) {
				AccountInfoNotFound accountInfoNotFound = new AccountInfoNotFound(ResponseStatus.FIVEZ0.getText(),
						ResponseStatus.FIVEZ0.getValue(), false);
				return ResponseEntity.status(HttpStatus.OK).body(accountInfoNotFound);
			}
		}

	}

	@RequestMapping(value = "/covered/account", method = RequestMethod.GET)
	public ResponseEntity<?> coveredAccountInfo(@Valid @NotBlank @RequestParam Map<String, String> requestParams,
			HttpServletRequest httpServletRequest) throws Exception {

		String accountNo = requestParams.get("accountNo");
		Objects.requireNonNull(accountNo);

		CustomValidation customValidation = new CustomValidation();

		if (customValidation.isBlankString(accountNo)) {
			JwtErrorResponse jwtErrorResponse = new JwtErrorResponse(HttpStatus.BAD_REQUEST,
					ResponseStatus.FOURZ8.getText(), ResponseStatus.FOURZ8.getValue());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jwtErrorResponse);
		}

		if (!customValidation.isRequiredLength(accountNo, 16)) {
			JwtErrorResponse jwtErrorResponse = new JwtErrorResponse(HttpStatus.BAD_REQUEST,
					ResponseStatus.FOURZ12.getText(), ResponseStatus.FOURZ12.getValue());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jwtErrorResponse);
		}

		TccUtility tccUtility = new TccUtility();

		String requestOFS = "ENQUIRY.SELECT,,,E.JBL.API.BAL.CHECK,ACCOUNT.NUMBER:EQ=" + accountNo;

		String responseData = tccUtility.sendRequest(requestOFS);
		System.out.println("responseData " + responseData);

		if (responseData.equals("400") || responseData.equals("401") || responseData.equals("402")
				|| responseData.equals("403")) {
			JwtErrorResponse jwtErrorResponse = new JwtErrorResponse(HttpStatus.OK, ResponseStatus.FIVEZ3.getText(),
					ResponseStatus.FIVEZ3.getValue());
			return ResponseEntity.status(HttpStatus.OK).body(jwtErrorResponse);
		} else {
			try {
				String[] spiltData = responseData.split(",");
				String[] secondPart = spiltData[2].split("\\*");
				String message = secondPart[0];
				String statusCode = secondPart[1];
				if (statusCode.equals("200")) {
					CoveredAccountInfo coveredAccountInfo = new CoveredAccountInfo();
					coveredAccountInfo.setAccountNo(accountNo);
					coveredAccountInfo.setAccountTitle(secondPart[2]);
					coveredAccountInfo.setAccountBalance(secondPart[3]);
					coveredAccountInfo.setBranchCode(secondPart[4].replace("BD001", ""));
					coveredAccountInfo.setBranchName(secondPart[5].replace("\"", ""));
					coveredAccountInfo.setValid(true);
					coveredAccountInfo.setMessage(ResponseStatus.TWOZ0.getText());
					coveredAccountInfo.setResponseCode(ResponseStatus.TWOZ0.getValue());
					return ResponseEntity.status(HttpStatus.OK).body(coveredAccountInfo);
				} else {
					AccountInfoNotFound accountInfoNotFound = new AccountInfoNotFound(ResponseStatus.FOURZ10.getText(),
							ResponseStatus.FOURZ10.getValue(), false);
					return ResponseEntity.status(HttpStatus.OK).body(accountInfoNotFound);
				}

			} catch (Exception e) {
				AccountInfoNotFound accountInfoNotFound = new AccountInfoNotFound(ResponseStatus.FIVEZ0.getText(),
						ResponseStatus.FIVEZ0.getValue(), false);
				return ResponseEntity.status(HttpStatus.OK).body(accountInfoNotFound);
			}
		}

	}

	@RequestMapping(value = "/ft/frd", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> FundTrasferFRD(@RequestBody RemitterInfo remitterInfo,
			HttpServletRequest httpServletRequest) throws Exception {

		String remittanceUniqueId = remitterInfo.getRemittanceUniqueId();
		String remittanceNo = remitterInfo.getRemittanceNo();
		String coveredAccount = remitterInfo.getCoveredAccount();
		String coveredAccountCoCode = remitterInfo.getCoveredAccountCoCode();
		String accountNo = remitterInfo.getAccountNo();
		String branchCode = remitterInfo.getBranchCode();
		Double amountInBDT = remitterInfo.getAmountInBDT();
		String beneficiaryName = remitterInfo.getBeneficiaryName();
		String exchangeHouseName = remitterInfo.getExchangeHouseName();

		Objects.requireNonNull(remittanceUniqueId);
		Objects.requireNonNull(remittanceNo);
		Objects.requireNonNull(coveredAccount);
		Objects.requireNonNull(coveredAccountCoCode);
		Objects.requireNonNull(accountNo);
		Objects.requireNonNull(branchCode);
		Objects.requireNonNull(amountInBDT);
		Objects.requireNonNull(beneficiaryName);
		Objects.requireNonNull(exchangeHouseName);

		CustomValidation customValidation = new CustomValidation();

		// Mandatory field can't be empty or null
		if (customValidation.isBlankString(remittanceUniqueId) || customValidation.isBlankString(remittanceNo)
				|| customValidation.isBlankString(coveredAccount)
				|| customValidation.isBlankString(coveredAccountCoCode) || customValidation.isBlankString(accountNo)
				|| customValidation.isBlankString(branchCode) || customValidation.isBlankDouble(amountInBDT)
				|| customValidation.isBlankString(beneficiaryName)
				|| customValidation.isBlankString(exchangeHouseName)) {
			JwtErrorResponse jwtErrorResponse = new JwtErrorResponse(HttpStatus.BAD_REQUEST,
					ResponseStatus.FOURZ18.getText(), ResponseStatus.FOURZ18.getValue());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jwtErrorResponse);

		}

		// Check Remittance Unique Id Length, it should be 20 character length

		if (!customValidation.isRequiredLength(remittanceUniqueId, 20)) {
			JwtErrorResponse jwtErrorResponse = new JwtErrorResponse(HttpStatus.BAD_REQUEST,
					ResponseStatus.FOURZ9.getText(), ResponseStatus.FOURZ9.getValue());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jwtErrorResponse);
		}

		// Account length and Covered Account Length Check

		if (!customValidation.isAccountLengthValid(coveredAccount)
				|| !customValidation.isAccountLengthValid(accountNo)) {
			JwtErrorResponse jwtErrorResponse = new JwtErrorResponse(HttpStatus.BAD_REQUEST,
					ResponseStatus.FOURZ12.getText(), ResponseStatus.FOURZ12.getValue());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jwtErrorResponse);
		}

		// Covered Account Co Code Length check, should be 4 character in digit

		if (!customValidation.isRequiredLength(coveredAccountCoCode, 4)) {
			JwtErrorResponse jwtErrorResponse = new JwtErrorResponse(HttpStatus.BAD_REQUEST,
					ResponseStatus.FOURZ9.getText(), ResponseStatus.FOURZ9.getValue());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jwtErrorResponse);
		}

		// check debit account branch code equals to debit account last 4 digit code
		// or for incentive amount credit purpose branch code must be 0102 and account
		// number must be 0100193309369

		String ftCrDetails = remittanceNo + "-" + exchangeHouseName;
		ftCrDetails = (ftCrDetails.length() > 40) ? ftCrDetails.substring(0, 40) : ftCrDetails;

		// String requestOFS =
		// "FUNDS.TRANSFER,JB.API/I/PROCESS//0,JAPIUSER1/01723As*/BD001" +
		// coveredAccountCoCode +
		// ",,DEBIT.CURRENCY=BDT,DEBIT.ACCT.NO=0100056366797,CREDIT.ACCT.NO=0100001425310,DEBIT.AMOUNT=100,FT.DR.DETAILS=TOTEST,AT.UNIQUE.ID=102345678912";

		String requestOFS = "FUNDS.TRANSFER,JB.API/I/PROCESS//0,BD001" + coveredAccountCoCode
				+ ",,DEBIT.CURRENCY=BDT,DEBIT.ACCT.NO=" + coveredAccount + ",CREDIT.ACCT.NO=" + accountNo
				+ ",DEBIT.AMOUNT=" + amountInBDT + ",FT.DR.DETAILS=" + remittanceNo + ",FT.CR.DETAILS=" + ftCrDetails
				+ ",AT.UNIQUE.ID=" + remittanceUniqueId + ",ORDERING.BANK=JBL.FRD";
		// String requestOFS = "FUNDS.TRANSFER,JB.API/I/PROCESS//0,BD001" +
		// coveredAccountCoCode
		// + ",,DEBIT.CURRENCY=BDT,DEBIT.ACCT.NO=" + coveredAccount + ",CREDIT.ACCT.NO="
		// + accountNo
		// + ",DEBIT.AMOUNT=" + amountInBDT + ",FT.DR.DETAILS=" + remittanceNo +
		// ",AT.UNIQUE.ID="
		// + remittanceUniqueId + ",ORDERING.BANK=JBL.FRD";

		RemitterInfo remitterInfoSave = null;

		// check this remitter if already exist or not in database by Remittance Unique
		// Id, if exist and success just return error
		RemitterInfo remitterInfoExist = remitterInfoService.findByRemittanceUniqueId(remittanceUniqueId);

		if (remitterInfoExist != null) {
			int statusExist = remitterInfoExist.getStatus();
			if (statusExist == RemitterStatus.SUCCESS.getValue()) {
				FtResponse ftResponse = new FtResponse();
				ftResponse.setStatus(HttpStatus.OK);
				ftResponse.setRemittanceUniqueId(remittanceUniqueId);
				String ofsResponse = remitterInfoExist.getOfsResponse();
				String[] spiltDataOfs = ofsResponse.split(",");
				String[] firstPart = spiltDataOfs[0].split("/");
				String statusFlag = firstPart[2];
				if (statusFlag.equals("1")) {
					String ftRef = firstPart[0];
					ftResponse.setFtRef(ftRef);
					ftResponse.setMessage(ResponseStatus.TWOZ2.getText());
					ftResponse.setResponseCode(ResponseStatus.TWOZ2.getValue());

					if (ofsResponse.contains(CBSResponseStr.accountNaturePersonal.getText())) {
						ftResponse.setCreditAccountCategory(AccountNature.PERSONAL.getValue());
						ftResponse.setAdditionalInfo(AccountNature.PERSONAL.getText());
					} else {
						ftResponse.setCreditAccountCategory(AccountNature.OTHER.getValue());
						ftResponse.setAdditionalInfo(AccountNature.OTHER.getText());
					}
					return ResponseEntity.status(HttpStatus.OK).body(ftResponse);
				} else if (ofsResponse.contains(CBSResponseStr.alreadySuccessDuplicate.getText())) {

					String duplicateSuccess[] = ofsResponse.split(",");
					String[] secondPart = duplicateSuccess[1].split("-");
					String ftRef = secondPart[2];
					String category = secondPart[3];
					if (category.equals("Personal")) {
						ftResponse.setCreditAccountCategory(AccountNature.PERSONAL.getValue());
					} else {
						ftResponse.setCreditAccountCategory(AccountNature.OTHER.getValue());
					}
					ftResponse.setFtRef(ftRef);

					ftResponse.setMessage(ResponseStatus.TWOZ2.getText());
					ftResponse.setResponseCode(ResponseStatus.TWOZ2.getValue());
					return ResponseEntity.status(HttpStatus.OK).body(ftResponse);
				} else {
					JwtErrorResponse jwtErrorResponse = new JwtErrorResponse(HttpStatus.OK,
							ResponseStatus.TWOZ3.getText(), ResponseStatus.TWOZ3.getValue());
					return ResponseEntity.status(HttpStatus.OK).body(jwtErrorResponse);
				}
			}
			remitterInfoSave = remitterInfoExist;

		} else {

			// save remmitter data into RemitterInfo table

			String remoteAddr = httpServletRequest.getHeader("X-FORWARDED-FOR");
			if (remoteAddr == null) {
				remoteAddr = httpServletRequest.getRemoteAddr();
			}
			String host = httpServletRequest.getRemoteHost();
			remitterInfo.setIp(remoteAddr);
			remitterInfo.setHostName(host);

			remitterInfo.setStatus(RemitterStatus.PENDING.getValue());
			remitterInfo.setOfsRequest(requestOFS);
			remitterInfo.setRequestAt(new Timestamp(System.currentTimeMillis()));
			remitterInfoSave = remitterInfoService.save(remitterInfo);
			// System.out.println("remitterInfoSave: " + remitterInfoSave);

		}

		System.out.println("requestOFS: " + requestOFS);
		TccUtility tccUtility = new TccUtility();
		String responseData = tccUtility.sendRequest(requestOFS);
		remitterInfoSave.setOfsResponse(responseData);
		remitterInfoSave.setResponseAt(new Timestamp(System.currentTimeMillis()));
		System.out.println("responseData " + responseData);

		if (responseData.equals("400") || responseData.equals("401") || responseData.equals("402")
				|| responseData.equals("403")) {
			JwtErrorResponse jwtErrorResponse = new JwtErrorResponse(HttpStatus.OK, ResponseStatus.FIVEZ3.getText(),
					ResponseStatus.FIVEZ3.getValue());
			remitterInfoSave.setStatus(RemitterStatus.FAILED.getValue());
			remitterInfoService.save(remitterInfoSave);
			return ResponseEntity.status(HttpStatus.OK).body(jwtErrorResponse);
		} else {
			FtResponse ftResponse = new FtResponse();
			ftResponse.setStatus(HttpStatus.OK);
			ftResponse.setRemittanceUniqueId(remittanceUniqueId);

			try {

				String[] spiltData = responseData.split(",");
				String[] firstPart = spiltData[0].split("/");
				String statusFlag = firstPart[2];

				if (statusFlag.equals("1")) {
					String ftRef = firstPart[0];
					ftResponse.setFtRef(ftRef);
					ftResponse.setMessage(ResponseStatus.TWOZ0.getText());
					ftResponse.setResponseCode(ResponseStatus.TWOZ0.getValue());

					if (responseData.contains(CBSResponseStr.accountNaturePersonal.getText())) {
						ftResponse.setCreditAccountCategory(AccountNature.PERSONAL.getValue());
						ftResponse.setAdditionalInfo(AccountNature.PERSONAL.getText());
					} else {
						ftResponse.setCreditAccountCategory(AccountNature.OTHER.getValue());
						ftResponse.setAdditionalInfo(AccountNature.OTHER.getText());
					}
					remitterInfoSave.setStatus(RemitterStatus.SUCCESS.getValue());
					remitterInfoService.save(remitterInfoSave);
				} else {

					if (responseData.contains(CBSResponseStr.alreadySuccessDuplicate.getText())) {

						String duplicateSuccess[] = responseData.split(",");
						String[] secondPart = duplicateSuccess[1].split("-");
						String ftRef = secondPart[2];
						String category = secondPart[3];
						if (category.equals("Personal")) {
							ftResponse.setCreditAccountCategory(AccountNature.PERSONAL.getValue());
						} else {
							ftResponse.setCreditAccountCategory(AccountNature.OTHER.getValue());
						}
						ftResponse.setFtRef(ftRef);

						ftResponse.setMessage(ResponseStatus.TWOZ2.getText());
						ftResponse.setResponseCode(ResponseStatus.TWOZ2.getValue());
						remitterInfoSave.setStatus(RemitterStatus.SUCCESS.getValue());
						remitterInfoService.save(remitterInfoSave);
					} else if (responseData.contains(CBSResponseStr.failedDuplicate.getText())) {
						ftResponse.setMessage(ResponseStatus.TWOZ3.getText());
						ftResponse.setResponseCode(ResponseStatus.TWOZ3.getValue());
						remitterInfoSave.setStatus(RemitterStatus.FAILED.getValue());
						remitterInfoService.save(remitterInfoSave);
					} else if (responseData.contains(CBSResponseStr.debitAccountMissing.getText())
							|| responseData.contains(CBSResponseStr.customDebitAccountMissing.getText())) {
						ftResponse.setMessage(ResponseStatus.FOURZ10.getText());
						ftResponse.setResponseCode(ResponseStatus.FOURZ10.getValue());
						remitterInfoSave.setStatus(RemitterStatus.FAILED.getValue());
						remitterInfoService.save(remitterInfoSave);
					} else if (responseData.contains(CBSResponseStr.creditAccountMissing.getText())
							|| responseData.contains(CBSResponseStr.customCreditAccountMissing.getText())) {
						ftResponse.setMessage(ResponseStatus.FOURZ7.getText());
						ftResponse.setResponseCode(ResponseStatus.FOURZ7.getValue());
						remitterInfoSave.setStatus(RemitterStatus.FAILED.getValue());
						remitterInfoService.save(remitterInfoSave);
					} else if (responseData.contains(CBSResponseStr.customDebitAccountCoCodeMissing.getText())) {
						ftResponse.setMessage(ResponseStatus.FOURZ11.getText());
						ftResponse.setResponseCode(ResponseStatus.FOURZ11.getValue());
						remitterInfoSave.setStatus(RemitterStatus.FAILED.getValue());
						remitterInfoService.save(remitterInfoSave);
					} else if (responseData.contains(CBSResponseStr.debitAccountLessBalance.getText())) {
						ftResponse.setMessage(ResponseStatus.FOURZ14.getText());
						ftResponse.setResponseCode(ResponseStatus.FOURZ14.getValue());
						remitterInfoSave.setStatus(RemitterStatus.FAILED.getValue());
						remitterInfoService.save(remitterInfoSave);
					} else {
						remitterInfoSave.setStatus(RemitterStatus.FAILED.getValue());
						remitterInfoService.save(remitterInfoSave);
						ftResponse.setMessage(ResponseStatus.TWOZ1.getText());
						ftResponse.setResponseCode(ResponseStatus.TWOZ1.getValue());
					}
					ftResponse.setAdditionalInfo(responseData);
				}
				return ResponseEntity.status(HttpStatus.OK).body(ftResponse);

			} catch (Exception e) {
				remitterInfoSave.setStatus(RemitterStatus.FAILED.getValue());
				remitterInfoService.save(remitterInfoSave);
				ftResponse.setMessage(ResponseStatus.TWOZ1.getText());
				ftResponse.setResponseCode(ResponseStatus.TWOZ1.getValue());
				ftResponse.setAdditionalInfo(responseData);
				return ResponseEntity.status(HttpStatus.OK).body(ftResponse);
			}
		}
	}

}
