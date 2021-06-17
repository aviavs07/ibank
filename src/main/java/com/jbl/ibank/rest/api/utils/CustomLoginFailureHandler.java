package com.jbl.ibank.rest.api.utils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jbl.ibank.rest.api.model.UserAccount;
import com.jbl.ibank.rest.api.service.UserAccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;


@Component
public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
     
    @Autowired
    private UserAccountService userService;
     
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {

        String email = request.getParameter("username");
        UserAccount user = userService.findOneByEmail(email);

        if (user != null) {
            if (user.isEnabled() && user.isAccountNonLocked()) {
                if (user.getFailedAttempt() < userService.MAX_FAILED_ATTEMPTS - 1) {
                    userService.increaseFailedAttempts(user);
                    exception = new LockedException("Invalid Email/Password.");
                } else {
                    userService.increaseFailedAttempts(user);
                    userService.lock(user);
                    exception = new LockedException("Your account has been locked due to 5 failed attempts."
                            + " It will be unlocked after 15 min. Or, Please Contact with Administrator.");
                }
            } else if (!user.isAccountNonLocked()) {
                if (userService.unlockWhenTimeExpired(user)) {
                    exception = new LockedException("Your account has been unlocked. Please try to login again.");
                }
            }
        }
        super.setDefaultFailureUrl("/login?error");
        super.onAuthenticationFailure(request, response, exception);
    }
}

