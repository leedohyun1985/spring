package com.doh.yun.main.controller;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.doh.yun.main.service.MainService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class MainController {
	
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	
	@Autowired
	MainService service;
	
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
		 * List<Map<String, Object>> i18nList = new ArrayList<Map<String,Object>>();
		 * Map<String, Object> paramMap = new HashMap<String, Object>(); for (Locale
		 * locale1 : Locale.getAvailableLocales()) { if(!"".equals(locale1.getCountry())
		 * && !"".equals(locale1.getLanguage())) { System.out.println("getCountry : " +
		 * locale1.getCountry()); System.out.println("getLanguage : " +
		 * locale1.getLanguage()); System.out.println("getDisplayCountry : " +
		 * locale1.getDisplayCountry()); System.out.println("getDisplayLanguage : " +
		 * locale1.getDisplayLanguage()); System.out.println("getDisplayName : " +
		 * locale1.getDisplayName()); System.out.println("toLanguageTag : " +
		 * locale1.toLanguageTag());
		 * 
		 * Map<String,Object> i18nMap = new HashMap<String, Object>();
		 * i18nMap.put("country", locale1.getCountry()); i18nMap.put("language",
		 * locale1.getLanguage()); i18nList.add(i18nMap); } } paramMap.put("i18nList",
		 * i18nList);
		 */
		//int o = service.putLacaleData(paramMap);
		
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
