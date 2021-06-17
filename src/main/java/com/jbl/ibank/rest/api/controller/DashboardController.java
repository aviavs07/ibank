package com.jbl.ibank.rest.api.controller;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import com.jbl.ibank.rest.api.service.UserAccountService;
import com.jbl.ibank.rest.api.utils.RequestIp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Controller
@RequestMapping("/dashboard")
public class DashboardController implements WebMvcConfigurer {

  @PersistenceContext
  private EntityManager entityManager;

  
  @Autowired
  public UserAccountService userAccountService;


  @Autowired
  public RequestIp requestIp;

  @GetMapping
  public ModelAndView index(HttpServletRequest request) {
    ModelAndView modelAndView = new ModelAndView("dashboard/index");
    String clientIp = requestIp.getClientIp(request);
    int totalUser = userAccountService.findAll().size();
    
    modelAndView.addObject("totalUser", totalUser);
    
    modelAndView.addObject("clientIp", clientIp);

    return modelAndView;
  }


}
