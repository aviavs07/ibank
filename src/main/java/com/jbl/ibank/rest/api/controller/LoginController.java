package com.jbl.ibank.rest.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jbl.ibank.rest.api.model.UserAccount;
import com.jbl.ibank.rest.api.service.UserAccountService;
import com.jbl.ibank.rest.api.utils.RequestIp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = { "/login", "/" })
public class LoginController {

	@Autowired
	private UserAccountService userAccountService;

	@Autowired
	public RequestIp requestIp;

	@GetMapping
	public String index(HttpServletRequest request, Model model) {

		// stop visiting login page after logged in
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return "login";
		}

		// Check user ip address is allowed or not
		String userName = authentication.getName();
		String remoteAddr = requestIp.getClientIp(request);

		boolean status = this.checkRequestIP(userName, remoteAddr);
		if (!status) {
			HttpSession httpSession = request.getSession();
			httpSession.invalidate();
			String errorMessage = "Request IP Address is not Allowed for this User. Please Contact with Administrator";
			model.addAttribute("errorMessage", errorMessage);
			model.addAttribute("remoteAddr", remoteAddr);
			return "login";
		}

		return "redirect:/dashboard";

	}

	private boolean checkRequestIP(String username, String remoteAddr) {
		try {
			UserAccount userAccount = userAccountService.findOneByEmail(username);
			String allowIP = userAccount.getWhitelistIp().trim();
			String[] spiltData = allowIP.split(",");

			String[] splitRemoteAddrs = remoteAddr.split(",");

			for (String remoteIpValue : splitRemoteAddrs) {
				for (String ipValue : spiltData) {
					remoteIpValue = remoteIpValue.trim();
					if (remoteIpValue.equals(ipValue)) {
						return true;
					}
				}
			}

			return false;
		} catch (Exception e) {

		}
		return false;
	}

}
