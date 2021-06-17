package com.jbl.ibank.rest.api.utils;

import javax.servlet.http.HttpServletRequest;

public interface RequestIp {

    String getClientIp(HttpServletRequest request);

 
}
