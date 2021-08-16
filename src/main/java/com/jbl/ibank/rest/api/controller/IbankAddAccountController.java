package com.jbl.ibank.rest.api.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import com.jbl.ibank.rest.api.common.model.AccountInfoNotFound;
import com.jbl.ibank.rest.api.common.model.JwtErrorResponse;
import com.jbl.ibank.rest.api.custom.validation.CustomValidation;
import com.jbl.ibank.rest.api.enums.ResponseStatus;
import com.jbl.ibank.rest.api.model.IbankLinkedBankAccount;
import com.jbl.ibank.rest.api.model.IbankUser;
import com.jbl.ibank.rest.api.service.IbankLinkedBankAccountService;
import com.jbl.ibank.rest.api.service.IbankUserService;
import com.jbl.ibank.rest.api.tccUtility.TccUtility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/linked")
public class IbankAddAccountController {

    @Autowired
    private IbankLinkedBankAccountService ibankLinkedBankAccountService;


// @RequestMapping(value = "/accounts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
// public @ResponseBody List<IbankLinkedBankAccount> accounts() {
   
//     List<IbankLinkedBankAccount> list = ibankLinkedBankAccountService.findAll();
    
//     return list;
    
// }


@RequestMapping(value = "/accounts", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<?> accounts(@RequestBody IbankLinkedBankAccount ibankLinkedBankAccount,
HttpServletRequest httpServletRequest) throws Exception { 
   
    String mobileNumber = ibankLinkedBankAccount.getMobileNumber();
    // System.out.println(mobileNumber);
    Objects.requireNonNull(mobileNumber);

    List<IbankLinkedBankAccount> lists = ibankLinkedBankAccountService.findByMobileNumber(mobileNumber);
  
    return ResponseEntity.status(HttpStatus.OK).body(lists);
}

    @RequestMapping(value = "/addAccount", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> AddAccount(@RequestBody IbankLinkedBankAccount ibankLinkedBankAccount,
            HttpServletRequest httpServletRequest) throws Exception {

        String accountNo = ibankLinkedBankAccount.getAccountNumber();

        Objects.requireNonNull(accountNo);

        CustomValidation customValidation = new CustomValidation();

        // Mandatory field can't be empty or null
        if (customValidation.isBlankString(accountNo)) {
            JwtErrorResponse jwtErrorResponse = new JwtErrorResponse(HttpStatus.BAD_REQUEST,
                    ResponseStatus.FOURZ18.getText(), ResponseStatus.FOURZ18.getValue());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jwtErrorResponse);

        }

        // Account length  Check

        if (!customValidation.isAccountLengthValid(accountNo)) {
            JwtErrorResponse jwtErrorResponse = new JwtErrorResponse(HttpStatus.BAD_REQUEST,
                    ResponseStatus.FOURZ12.getText(), ResponseStatus.FOURZ12.getValue());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jwtErrorResponse);
        }
        // System.out.println(accountNo);

        IbankLinkedBankAccount ibankLinkedBankAccountSave = null;

        // check this remitter if already exist or not in database by Remittance Unique
        // Id, if exist and success just return error
        IbankLinkedBankAccount ibankLinkedBankAccountExist = ibankLinkedBankAccountService
                .findByAccountNumber(accountNo);
        System.out.println(ibankLinkedBankAccountExist);
        if (ibankLinkedBankAccountExist != null) {
            if (!ibankLinkedBankAccountExist.isStatus()) {
                AccountInfoNotFound accountInfoNotFound = new AccountInfoNotFound("Inactive", 2, false);
                // System.out.println(ibankLinkedBankAccountExist.isStatus());

                return ResponseEntity.status(HttpStatus.OK).body(accountInfoNotFound);
            } else if (ibankLinkedBankAccountExist.isStatus()) {
                AccountInfoNotFound accountInfoNotFound = new AccountInfoNotFound("Allready Exist.", 1, true);
                // System.out.println(ibankLinkedBankAccountExist.isStatus());

                return ResponseEntity.status(HttpStatus.OK).body(accountInfoNotFound);
            } else {
                JwtErrorResponse jwtErrorResponse = new JwtErrorResponse(HttpStatus.OK, ResponseStatus.TWOZ3.getText(),
                        ResponseStatus.TWOZ3.getValue());
                return ResponseEntity.status(HttpStatus.OK).body(jwtErrorResponse);
            }

        } else {
            // save remmitter data into RemitterInfo table
            // ibankLinkedBankAccountSave = ibankLinkedBankAccountExist;
            TccUtility tccUtility = new TccUtility();
            System.out.println(accountNo);
            String requestOFS = "ENQUIRY.SELECT,,,E.JBL.API,ACCOUNT.NUMBER:EQ=" + accountNo;
            String responseData = tccUtility.sendRequest(requestOFS);
            System.out.println("responseData " + responseData);
            // ibankLinkedBankAccount.setAccountNumber(accountNumber);
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
                            String[] nidPart = secondPart[7].split("\\s");
                            System.out.println(nidPart[0].trim());
                            IbankLinkedBankAccount ibankLinkedBankAccount2 = new IbankLinkedBankAccount();

                            
                            // System.out.println("ibankLinkedBankAccount2 " + ibankLinkedBankAccount2);

                            // ibankLinkedBankAccount2.setIbankLinkedBankAcId(1);
                            ibankLinkedBankAccount2.setAccountName(secondPart[5]);
                            ibankLinkedBankAccount2.setAccountNumber(secondPart[3]);
                            ibankLinkedBankAccount2.setMobileNumber(secondPart[6]);
                            ibankLinkedBankAccount2.setNid(nidPart[0].trim());
                            ibankLinkedBankAccount2.setBranchId(secondPart[8].replace("BD001", ""));
                            ibankLinkedBankAccount2.setStatus(true);
                            ibankLinkedBankAccount2.setIbankUserId(1);
                            ibankLinkedBankAccount2.setCreatedBy(1);
                            ibankLinkedBankAccount2.setCreatedDate(LocalDateTime.now());
                            ibankLinkedBankAccount2.setUpdatedBy(0);
                            ibankLinkedBankAccount2.setUpdatedDate(Timestamp.valueOf(LocalDateTime.now()));

                            ibankLinkedBankAccountService.save(ibankLinkedBankAccount2);

                            return ResponseEntity.status(HttpStatus.OK).body(ibankLinkedBankAccount2);
                        } else {
                            AccountInfoNotFound accountInfoNotFound = new AccountInfoNotFound(
                                    ResponseStatus.FOURZ13.getText(), ResponseStatus.FOURZ13.getValue(), false);
                            return ResponseEntity.status(HttpStatus.OK).body(accountInfoNotFound);
                        }
                    } else {
                        AccountInfoNotFound accountInfoNotFound = new AccountInfoNotFound(
                                ResponseStatus.FOURZ7.getText(), ResponseStatus.FOURZ7.getValue(), false);
                        return ResponseEntity.status(HttpStatus.OK).body(accountInfoNotFound);
                    }

                } catch (Exception e) {
                    AccountInfoNotFound accountInfoNotFound = new AccountInfoNotFound(ResponseStatus.FIVEZ0.getText(),
                            ResponseStatus.FIVEZ0.getValue(), false);
                    return ResponseEntity.status(HttpStatus.OK).body(accountInfoNotFound);
                }
            }

        }
        // return ResponseEntity.status(HttpStatus.OK).body(accountNo);
    }
}
