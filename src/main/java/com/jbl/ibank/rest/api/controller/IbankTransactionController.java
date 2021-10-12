package com.jbl.ibank.rest.api.controller;


import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.jbl.ibank.rest.api.common.model.AccountInfo;
import com.jbl.ibank.rest.api.common.model.AccountInfoNotFound;
import com.jbl.ibank.rest.api.common.model.FtResponse;
import com.jbl.ibank.rest.api.common.model.JwtErrorResponse;
import com.jbl.ibank.rest.api.custom.validation.CustomValidation;
import com.jbl.ibank.rest.api.enums.AccountNature;
import com.jbl.ibank.rest.api.enums.CBSResponseStr;
import com.jbl.ibank.rest.api.enums.RemitterStatus;
import com.jbl.ibank.rest.api.enums.ResponseStatus;
import com.jbl.ibank.rest.api.model.IbankTransactionInfo;
import com.jbl.ibank.rest.api.service.IbankTrService;
import com.jbl.ibank.rest.api.tccUtility.TccUtility;

import org.slf4j.Marker;
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
import org.springframework.web.client.RestTemplate;


import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin
@RequestMapping("/ibanktr")
@Slf4j
public class IbankTransactionController {

	
	// @Autowired
	// private RestTemplate restTemplate;

    @Autowired
    private IbankTrService ibankTrService;

	@RequestMapping(value = "/account-check", method = RequestMethod.GET)
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
		
		log.info(requestOFS);
		String responseData = tccUtility.sendRequest(requestOFS);
		log.info(responseData);
		
//		System.out.println("responseData " + responseData);
//		
		//--------------------
//		RequestOfs requestOfs = new RequestOfs();
//		requestOfs.setReqOfs(requestOFS);
//		
//		String responseData = restTemplate.postForObject("http://TCC-CLIENT/tcc/ofs/",requestOfs, String.class);
		System.out.println("responseData " + responseData);
		//-------------------------------
		
		
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
		

	
	@RequestMapping(value = "/ft", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	
	public ResponseEntity<?> beftnTrasferInward(@RequestBody IbankTransactionInfo ibankTransactionInfo,
			HttpServletRequest httpServletRequest) throws Exception{

		
		
		String debitAccNo = ibankTransactionInfo.getDebitAccNo();
//		String currency = beftnInfo.getCurrency();
		String creditAccNo = ibankTransactionInfo.getCreditAccNo();
		Double debitAmount = ibankTransactionInfo.getDebitAmount();
		String narrative = ibankTransactionInfo.getNarrative();
		// Integer version = ibankTransactionInfo.getVersion();
		System.out.println("-----------------------------");
		System.out.println("dan: "+debitAccNo);
		System.out.println("can: "+creditAccNo);
		System.out.println("dam: "+debitAmount);
		System.out.println("narr" + narrative);
		System.out.println("-----------------------------");
		
		Objects.requireNonNull(debitAccNo);
//		Objects.requireNonNull(currency);
		Objects.requireNonNull(creditAccNo);
		Objects.requireNonNull(debitAmount);
		Objects.requireNonNull(narrative);
		// Objects.requireNonNull(version);
		
		CustomValidation customValidation = new CustomValidation();
			
		if (customValidation.isBlankString(debitAccNo) 
				|| customValidation.isBlankString(creditAccNo)
				|| customValidation.isBlankString(narrative)
				|| customValidation.isBlankDouble(debitAmount)) {
			
			JwtErrorResponse jwtErrorResponse = new JwtErrorResponse(HttpStatus.BAD_REQUEST,
					ResponseStatus.FOURZ18.getText(), ResponseStatus.FOURZ18.getValue());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jwtErrorResponse);

		}

		
		// Debit Account and Credit Account Length Check
		
		
		
		String	requestOFS = "FUNDS.TRANSFER,BEFTN/I/PROCESS//0,BD0010102"
					+ ",,DEBIT.CURRENCY=BDT,"
					+ "DEBIT.ACCT.NO="+debitAccNo+","
					+ "CREDIT.ACCT.NO="+creditAccNo+","
					+ "DEBIT.AMOUNT="+debitAmount+","
					+ "FT.CR.DETAILS="+narrative;
	
		
	
		IbankTransactionInfo ibankTransactionInfoSave = null;

        
		// check this beftn inward if already exist or not in database by narrative
		// Id, if exist and success just return error		
		// BeftnInwardInfo beftnInfoExist = ibankTrService.findByNarrative(narrative);
		
		// if (beftnInfoExist != null) {
		// 	int statusExist = beftnInfoExist.getStatus();
		// 	if (statusExist == RemitterStatus.SUCCESS.getValue()) {
		// 		FtResponse ftResponse = new FtResponse();
		// 		ftResponse.setStatus(HttpStatus.OK);
		// 		ftResponse.setNarrative(narrative);
		// 		String ofsResponse = beftnInfoExist.getOfsResponse();
		// 		String[] spiltDataOfs = ofsResponse.split(",");
		// 		String[] firstPart = spiltDataOfs[0].split("/");
		// 		String statusFlag = firstPart[2];
		// 		if (statusFlag.equals("1")) {
		// 			String ftRef = firstPart[0];
		// 			ftResponse.setFtRef(ftRef);
		// 			ftResponse.setMessage(ResponseStatus.TWOZ2.getText());
		// 			ftResponse.setResponseCode(ResponseStatus.TWOZ2.getValue());

		// 			if (ofsResponse.contains(CBSResponseStr.accountNaturePersonal.getText())) {
		// 				ftResponse.setCreditAccountCategory(AccountNature.PERSONAL.getValue());
		// 				ftResponse.setAdditionalInfo(AccountNature.PERSONAL.getText());
		// 			} else {
		// 				ftResponse.setCreditAccountCategory(AccountNature.OTHER.getValue());
		// 				ftResponse.setAdditionalInfo(AccountNature.OTHER.getText());
		// 			}
		// 			return ResponseEntity.status(HttpStatus.OK).body(ftResponse);
		// 		} else if (ofsResponse.contains(CBSResponseStr.alreadySuccessDuplicate.getText())) {

		// 			String duplicateSuccess[] = ofsResponse.split(",");
		// 			String[] secondPart = duplicateSuccess[1].split("-");
		// 			String ftRef = secondPart[2];
		// 			String category = secondPart[3];
		// 			if (category.equals("Personal")) {
		// 				ftResponse.setCreditAccountCategory(AccountNature.PERSONAL.getValue());
		// 			} else {
		// 				ftResponse.setCreditAccountCategory(AccountNature.OTHER.getValue());
		// 			}
		// 			ftResponse.setFtRef(ftRef);

		// 			ftResponse.setMessage(ResponseStatus.TWOZ2.getText());
		// 			ftResponse.setResponseCode(ResponseStatus.TWOZ2.getValue());
		// 			return ResponseEntity.status(HttpStatus.OK).body(ftResponse);
		// 		} else {
		// 			JwtErrorResponse jwtErrorResponse = new JwtErrorResponse(HttpStatus.OK,
		// 					ResponseStatus.TWOZ3.getText(), ResponseStatus.TWOZ3.getValue());
		// 			return ResponseEntity.status(HttpStatus.OK).body(jwtErrorResponse);
		// 		}
		// 	}
		// 	ibankTransactionInfoSave = beftnInfoExist;

		// } else 
        // {

			// save remmitter data into RemitterInfo table
            String remoteAddr = httpServletRequest.getHeader("X-FORWARDED-FOR");
			if (remoteAddr == null) {
				remoteAddr = httpServletRequest.getRemoteAddr();
			}
			
			String host = httpServletRequest.getRemoteHost();
			ibankTransactionInfo.setIp(remoteAddr);
			ibankTransactionInfo.setHostName(host);

			ibankTransactionInfo.setStatus(RemitterStatus.PENDING.getValue());
			ibankTransactionInfo.setOfsRequest(requestOFS);
			//remitterInfo.setRequestAt(new Timestamp(System.currentTimeMillis()));
			log.info(requestOFS);
			ibankTransactionInfoSave = ibankTrService.save(ibankTransactionInfo);
			log.info("Saved Data");
			// System.out.println("remitterInfoSave: " + remitterInfoSave);

		// }

		System.out.println("requestOFS: " + requestOFS);
		TccUtility tccUtility = new TccUtility();
		
		String responseData = tccUtility.sendRequest(requestOFS);
		
		//
		//--------------------
//				RequestOfs requestOfs = new RequestOfs();
//				requestOfs.setReqOfs(requestOFS);
//				String responseData = ibankTrService.getTccOfsResponse(requestOfs);
		//-------------------------------	
		//
		
		ibankTransactionInfoSave.setOfsResponse(responseData);
		//remitterInfoSave.setResponseAt(new Timestamp(System.currentTimeMillis()));
		System.out.println("responseData " + responseData);

		if (responseData.equals("400") || responseData.equals("401") || responseData.equals("402")
				|| responseData.equals("403")) {
			JwtErrorResponse jwtErrorResponse = new JwtErrorResponse(HttpStatus.OK, ResponseStatus.FIVEZ3.getText(),
					ResponseStatus.FIVEZ3.getValue());
			ibankTransactionInfoSave.setStatus(RemitterStatus.FAILED.getValue());
			ibankTrService.save(ibankTransactionInfoSave);
			return ResponseEntity.status(HttpStatus.OK).body(jwtErrorResponse);
		} else {
			FtResponse ftResponse = new FtResponse();
			ftResponse.setStatus(HttpStatus.OK);
			// ftResponse.setNarrative(narrative);

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
					ibankTransactionInfoSave.setStatus(RemitterStatus.SUCCESS.getValue()); //Later needs to be changed at Remitter Status
					ibankTrService.save(ibankTransactionInfoSave);
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
						ibankTransactionInfoSave.setStatus(RemitterStatus.SUCCESS.getValue());
						ibankTrService.save(ibankTransactionInfoSave);
					} else if (responseData.contains(CBSResponseStr.failedDuplicate.getText())) {
						ftResponse.setMessage(ResponseStatus.TWOZ3.getText());
						ftResponse.setResponseCode(ResponseStatus.TWOZ3.getValue());
						ibankTransactionInfoSave.setStatus(RemitterStatus.FAILED.getValue());
						ibankTrService.save(ibankTransactionInfoSave);
					} else if (responseData.contains(CBSResponseStr.debitAccountMissing.getText())
							|| responseData.contains(CBSResponseStr.customDebitAccountMissing.getText())) {
						ftResponse.setMessage(ResponseStatus.FOURZ10.getText());
						ftResponse.setResponseCode(ResponseStatus.FOURZ10.getValue());
						ibankTransactionInfoSave.setStatus(RemitterStatus.FAILED.getValue());
						ibankTrService.save(ibankTransactionInfoSave);
					} else if (responseData.contains(CBSResponseStr.creditAccountMissing.getText())
							|| responseData.contains(CBSResponseStr.customCreditAccountMissing.getText())) {
						ftResponse.setMessage(ResponseStatus.FOURZ7.getText());
						ftResponse.setResponseCode(ResponseStatus.FOURZ7.getValue());
						ibankTransactionInfoSave.setStatus(RemitterStatus.FAILED.getValue());
						ibankTrService.save(ibankTransactionInfoSave);
					} else if (responseData.contains(CBSResponseStr.customDebitAccountCoCodeMissing.getText())) {
						ftResponse.setMessage(ResponseStatus.FOURZ11.getText());
						ftResponse.setResponseCode(ResponseStatus.FOURZ11.getValue());
						ibankTransactionInfoSave.setStatus(RemitterStatus.FAILED.getValue());
						ibankTrService.save(ibankTransactionInfoSave);
					} else if (responseData.contains(CBSResponseStr.debitAccountLessBalance.getText())) {
						ftResponse.setMessage(ResponseStatus.FOURZ14.getText());
						ftResponse.setResponseCode(ResponseStatus.FOURZ14.getValue());
						ibankTransactionInfoSave.setStatus(RemitterStatus.FAILED.getValue());
						ibankTrService.save(ibankTransactionInfoSave);
					} else {
						ibankTransactionInfoSave.setStatus(RemitterStatus.FAILED.getValue());
						ibankTrService.save(ibankTransactionInfoSave);
						ftResponse.setMessage(ResponseStatus.TWOZ1.getText());
						ftResponse.setResponseCode(ResponseStatus.TWOZ1.getValue());
					}
					ftResponse.setAdditionalInfo(responseData);
				}
				return ResponseEntity.status(HttpStatus.OK).body(ftResponse);

			} catch (Exception e) {
				ibankTransactionInfoSave.setStatus(RemitterStatus.FAILED.getValue());
				ibankTrService.save(ibankTransactionInfoSave);
				ftResponse.setMessage(ResponseStatus.TWOZ1.getText());
				ftResponse.setResponseCode(ResponseStatus.TWOZ1.getValue());
				ftResponse.setAdditionalInfo(responseData);
				return ResponseEntity.status(HttpStatus.OK).body(ftResponse);
			}
		}
	
		
	}

}