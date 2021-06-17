package com.jbl.ibank.rest.api.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

@Service
public class RequestServiceImpl implements RequestIp {

	@Override
	public String getClientIp(HttpServletRequest request) {
		String ipAddress = request.getHeader("X-Forwarded-For");

		if (ipAddress == null || ipAddress.length() == 0 || ipAddress.equalsIgnoreCase("unknown")) {
			ipAddress = request.getHeader("Proxy-Client-IP");

		}
		if (ipAddress == null || ipAddress.length() == 0 || ipAddress.equalsIgnoreCase("unknown")) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");

		}
		if (ipAddress == null || ipAddress.length() == 0 || ipAddress.equalsIgnoreCase("unknown")) {
			ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");

		}
		if (ipAddress == null || ipAddress.length() == 0 || ipAddress.equalsIgnoreCase("unknown")) {
			ipAddress = request.getHeader("HTTP_X_FORWARDED");

		}
		if (ipAddress == null || ipAddress.length() == 0 || ipAddress.equalsIgnoreCase("unknown")) {
			ipAddress = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");

		}
		if (ipAddress == null || ipAddress.length() == 0 || ipAddress.equalsIgnoreCase("unknown")) {
			ipAddress = request.getHeader("HTTP_CLIENT_IP");

		}
		if (ipAddress == null || ipAddress.length() == 0 || ipAddress.equalsIgnoreCase("unknown")) {
			ipAddress = request.getHeader("HTTP_FORWARDED_FOR");

		}
		if (ipAddress == null || ipAddress.length() == 0 || ipAddress.equalsIgnoreCase("unknown")) {
			ipAddress = request.getHeader("HTTP_FORWARDED");

		}
		if (ipAddress == null || ipAddress.length() == 0 || ipAddress.equalsIgnoreCase("unknown")) {
			ipAddress = request.getHeader("HTTP_VIA");

		}
		if (ipAddress == null || ipAddress.length() == 0 || ipAddress.equalsIgnoreCase("unknown")) {
			ipAddress = request.getHeader("REMOTE_ADDR");

		}
		if (ipAddress == null || ipAddress.length() == 0 || ipAddress.equalsIgnoreCase("unknown")) {
			ipAddress = request.getRemoteAddr();

		}

		if (ipAddress.equals("0:0:0:0:0:0:0:1"))
			ipAddress = "172.17.6.40";

		System.out.println(ipAddress);

		return ipAddress;
	}

}
