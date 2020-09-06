package com.doh.yun.error.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Handles requests for the application home page.
 */
@Controller
public class ErrorController {
	
	private static final Logger logger = LoggerFactory.getLogger(ErrorController.class);

	@RequestMapping(value = "/errors", method = RequestMethod.GET)
	public ModelAndView error(Locale locale, HttpServletRequest httpServletRequest) {
		//추후 리다이렉트 되는 부분 어떻게 수정할지 고민해보자.
		//현재 파라미터 전달시 get방식으로 전달하여야 하고, redirectView.setExposeModelAttributes(false) 설정시 파라미터 전달이 안됨
		
        
        ModelAndView modelAndView = new ModelAndView("error/error");
        		
		String errorCode;
		String errorType;
		String errorMessage;
		String errorName;
		String errorException;
		String errorUrl;
        		
		if(null != httpServletRequest.getAttribute("javax.servlet.error.status_code"))
		{
			errorCode =String.valueOf( (int) httpServletRequest.getAttribute("javax.servlet.error.status_code") );
			errorType = (String) httpServletRequest.getAttribute("javax.servlet.error.exception_type");
			errorMessage = (String) httpServletRequest.getAttribute("javax.servlet.error.message");
			errorName = (String) httpServletRequest.getAttribute("javax.servlet.error.servlet_name");
			errorException = (String) httpServletRequest.getAttribute("javax.servlet.error.exception");
			errorUrl = (String) httpServletRequest.getAttribute("javax.servlet.error.request_uri");
		}
		else
		{
			errorCode = (String) httpServletRequest.getParameter("errorCode");
			errorType = (String)httpServletRequest.getParameter("errorType");
			errorMessage = (String) httpServletRequest.getParameter("errorMessage");
			errorName = (String) httpServletRequest.getParameter("errorName");
			errorException = (String) httpServletRequest.getParameter("errorException");
			errorUrl = (String) httpServletRequest.getParameter("errorUrl");
		}
		logger.info("errorCode : " + errorCode);
		logger.info("errorType : " + errorType);
		logger.info("errorMessage : " + errorMessage);
		logger.info("errorName : " + errorName);
		logger.info("errorException : " + errorException);
		logger.info("errorUrl : " + errorUrl);
		
		modelAndView.addObject("errorCode", errorCode);
		modelAndView.addObject("errorType", errorType);
		modelAndView.addObject("errorMessage", errorMessage);
		modelAndView.addObject("errorName", errorName);
		modelAndView.addObject("errorException", errorException);
		modelAndView.addObject("errorUrl", errorUrl);
        return modelAndView;

	}

}
