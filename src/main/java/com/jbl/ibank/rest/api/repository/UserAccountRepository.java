package com.jbl.ibank.rest.api.repository;

import java.sql.Timestamp;

import com.jbl.ibank.rest.api.model.UserAccount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {
    
    UserAccount findByUserEmail(String userEmail);
	UserAccount findByUserBankId(String userBankId);


    @Query("UPDATE UserAccount u SET u.failedAttempt = ?1 WHERE u.userEmail = ?2")
    @Modifying
    public void updateFailedAttempts(int failAttempts, String userEmail);

	@Query("UPDATE UserAccount u SET u.accountNonLocked = ?1 WHERE u.userEmail = ?2")
    @Modifying
    public void updateAccountLocked(Boolean accountNonLocked, String userEmail);

	@Query("UPDATE UserAccount u SET u.lockTime = ?1 WHERE u.userEmail = ?2")
    @Modifying
    public void updateLockeDate(Timestamp lockTime, String userEmail);
}
