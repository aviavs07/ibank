package com.jbl.ibank.rest.api.utils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jbl.ibank.rest.api.model.MyUserAccountDetails;
import com.jbl.ibank.rest.api.model.UserAccount;
import com.jbl.ibank.rest.api.service.UserAccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserAccountService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        MyUserAccountDetails userDetails = (MyUserAccountDetails) authentication.getPrincipal();
        UserAccount user = userService.findOneByEmail(userDetails.getUsername());

        if (user.getFailedAttempt() > 0) {
            userService.resetFailedAttempts(user.getUserEmail());
        }

        super.setDefaultTargetUrl("/dashboard");
        super.onAuthenticationSuccess(request, response, authentication);

        
    }

}
