package com.jbl.ibank.rest.api.service;


import java.sql.Timestamp;
import java.util.List;

import com.jbl.ibank.rest.api.model.UserAccount;
import com.jbl.ibank.rest.api.repository.UserAccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserAccountService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    public List<UserAccount> findAll() {
        return userAccountRepository.findAll();
    }

    
    public UserAccount findOneByEmail(String userEmail) {
        return userAccountRepository.findByUserEmail(userEmail);
    }

    public UserAccount findOneByBankId(String bankId) {
        return userAccountRepository.findByUserBankId(bankId);
    }

    

    public static final int MAX_FAILED_ATTEMPTS = 5;

    private static final long LOCK_TIME_DURATION = 15 * 60 * 1000; // 24 hours

    

    public void increaseFailedAttempts(UserAccount user) {
        int newFailAttempts = user.getFailedAttempt() + 1;
        userAccountRepository.updateFailedAttempts(newFailAttempts, user.getUserEmail());
    }

    public void lock(UserAccount user) {
        boolean newAccountNonLocked = false;
        Timestamp newLockTime = new Timestamp(System.currentTimeMillis());

        userAccountRepository.updateAccountLocked(newAccountNonLocked, user.getUserEmail());
        userAccountRepository.updateLockeDate(newLockTime, user.getUserEmail());
    }

    public void resetFailedAttempts(String userEmail) {
        userAccountRepository.updateFailedAttempts(0, userEmail);
    }

    public boolean unlockWhenTimeExpired(UserAccount user) {
        long lockTimeInMillis = user.getLockTime().getTime();
        long currentTimeInMillis = System.currentTimeMillis();

        if (lockTimeInMillis + LOCK_TIME_DURATION < currentTimeInMillis) {
            boolean newAccountNonLocked = true;
            Timestamp newLockTime = null;
            int newFailAttempts = 0;

            userAccountRepository.updateAccountLocked(newAccountNonLocked, user.getUserEmail());
            userAccountRepository.updateLockeDate(newLockTime, user.getUserEmail());
            userAccountRepository.updateFailedAttempts(newFailAttempts, user.getUserEmail());

            return true;
        }

        return false;
    }
    
   

}

