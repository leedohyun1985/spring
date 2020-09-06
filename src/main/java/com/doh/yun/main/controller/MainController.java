package com.doh.yun.main.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class MainController {
	
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		/*
		 * Authentication authentication =
		 * SecurityContextHolder.getContext().getAuthentication();
		 * 
		 * logger.info("name : " + authentication.getName());
		 * logger.info("credentials : " + authentication.getCredentials());
		 * logger.info("details : " + authentication.getDetails());
		 * logger.info("principal : " + authentication.getPrincipal()); UserDetailsImpl
		 * detailsImpl = (UserDetailsImpl) authentication.getPrincipal();
		 * logger.info("userId : " + detailsImpl.getUserId()); logger.info("password : "
		 * + detailsImpl.getPassword()); for (GrantedAuthority grantedAuthority :
		 * authentication.getAuthorities()) { logger.info("grantedAuthority : " +
		 * grantedAuthority.getAuthority()); }
		 */
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
}
