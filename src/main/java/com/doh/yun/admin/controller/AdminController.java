package com.doh.yun.admin.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.doh.yun.security.dto.UserDetailsImpl;

@Controller
public class AdminController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public ModelAndView admin(HttpServletRequest httpServletRequest, Locale locale, Model model) {
		ModelAndView modelAndView = new ModelAndView();

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		logger.info("name : " + authentication.getName());
		logger.info("credentials : " + authentication.getCredentials());
		logger.info("details : " + authentication.getDetails());
		logger.info("principal : " + authentication.getPrincipal());
		UserDetailsImpl detailsImpl = (UserDetailsImpl) authentication.getPrincipal();
		logger.info("userId : " + detailsImpl.getUserId());
		logger.info("password : " + detailsImpl.getPassword());
		for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
			logger.info("grantedAuthority : " + grantedAuthority.getAuthority());
		}
		
		modelAndView.setViewName("admin/admin");
		return modelAndView;
	}
}
