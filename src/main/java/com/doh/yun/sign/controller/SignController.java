package com.doh.yun.sign.controller;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.doh.yun.sign.service.SignService;

@Controller
public class SignController {

	@Autowired
	SignService signService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 로그인
	 * 
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	public String signIn(Locale locale, Model model) {
		return "sign/signIn";
	}

	/**
	 * 회원가입
	 * 
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signUp(Locale locale, Model model) {
		// 회원 상태에 따라 반환되는 페이지가 달라야함
		// 이메일인증인 경우

		return "sign/signUp";
	}

	/**
	 * 회원가입
	 * 
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/signupemail", method = RequestMethod.GET)
	public String emailCheck(Locale locale, Model model) {

		return "sign/emailCheck";
	}
	
	/**
	 * 발송이메일로부터 이메일인증
	 * 로그인 권한 줄것
	 * 예외처리 보강
	 * 
	 * @param httpServletRequest
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/signupemailcheck", method = RequestMethod.GET)
	public ModelAndView emailCheckFromEamil(HttpServletRequest httpServletRequest, Locale locale, Model model) {

		ModelAndView mav = new ModelAndView();

		String email = httpServletRequest.getParameter("email");
		String code = httpServletRequest.getParameter("code");
		logger.info("emailcheckprocess 진입함");
		logger.info("email = " + email);
		logger.info("code = " + code);
		int status = signService.checkValidEmail(email);
		
		// 이메일을 기준으로 사용자 상태값이 2인지 확인 status 2 가 아니면
		if (status != 2) {
			RedirectView redirectView = new RedirectView(); // redirect url 설정
			redirectView.setUrl(httpServletRequest.getContextPath() + "/errors");
			// redirectView.setExposeModelAttributes(false);
			mav.setView(redirectView);
			mav.addObject("errorName", "이메일인증상태가 아님");
			mav.addObject("errorCode", "10004");
			mav.addObject("errorDesc", "이메일인증이 필요한 계정이 아닙니다");
			signService.updateUserStatus(email, 4); // 계정잠금
			return mav;
		}

		// 이메일의 인증 횟수 검증
		int emailCount = signService.countEmailAuth(email);

		// 추후 이메일인증 횟수 DB에서 가져오도록 수정할것
		// 이메일인증횟수 3회초과일 경우 차단
		if (emailCount > 3) {
			RedirectView redirectView = new RedirectView(); // redirect url 설정
			redirectView.setUrl(httpServletRequest.getContextPath() + "/errors");
			// redirectView.setExposeModelAttributes(false);
			mav.setView(redirectView);
			mav.addObject("errorName", "이메일인증횟수초과");
			mav.addObject("errorCode", "10002");
			mav.addObject("errorDesc", "이메일 최대 인증 횟수를 초과하였습니다.");
			signService.updateUserStatus(email, 4); // 계정잠금
			return mav;
		}

		// 이메일 인증 횟수 추가 (이메일 인증 횟수 초과 확인 후 작업해야)
		signService.updateEmailAuthCount(email);

		// 이메일의 인증 시간 검증 (분당 시간)
		int time = signService.checkEmailAuthTime(email);
		logger.info("인증후 현재 시간과 차이 (분당) = " + time);
		// 추후 인증 시간도 db에서 관리하도록 수정
		// 인증 메일 시간이 60분 초과시 인증 안됨
		if (time > 60) {
			mav.addObject("email", email);
			mav.addObject("message", "이메일 인증 시간이 초과하였습니다. 재발송 후 인증 가능합니다.");
			mav.setViewName("sign/emailCheck");
			return mav;
		}
		// 이메일의 인증값 비교
		String authCode = signService.getEmailAuthCode(email);

		if (!authCode.equalsIgnoreCase(code)) {
			// 일치하지 않으면
			mav.addObject("email", email);
			mav.addObject("message", "이메일 인증코드가 일치하지 않습니다.");
			mav.setViewName("sign/emailCheck");
			return mav;
		}

		// 검증완료후 계정 활성화
		signService.activateUser(email);
		mav.setViewName("home");
		return mav;
	}
	
	/**
	 * 이메일 인증 화면에서 이메일인증
	 * 로그인 권한 줄것
	 * 예외처리 보강
	 * 
	 * @param httpServletRequest
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/signupemailcheck", method = RequestMethod.POST)
	public ModelAndView emailCheckFromWebPage(HttpServletRequest httpServletRequest, Locale locale, Model model) {
		ModelAndView mav = new ModelAndView("home");
		String email = httpServletRequest.getParameter("email");
		String code = httpServletRequest.getParameter("code");
		logger.info("emailcheckprocess 진입함");
		logger.info("email = " + email);
		logger.info("code = " + code);
		int status = signService.checkValidEmail(email);
		
		// 이메일을 기준으로 사용자 상태값이 2인지 확인 status 2 가 아니면
		if (status != 2) {
			RedirectView redirectView = new RedirectView(); // redirect url 설정
			redirectView.setUrl(httpServletRequest.getContextPath() + "/errors");
			// redirectView.setExposeModelAttributes(false);
			mav.setView(redirectView);
			mav.addObject("errorName", "이메일인증상태가 아님");
			mav.addObject("errorCode", "10004");
			mav.addObject("errorDesc", "이메일인증이 필요한 계정이 아닙니다");
			signService.updateUserStatus(email, 4); // 계정잠금
			return mav;
		}

		// 이메일의 인증 횟수 검증
		int emailCount = signService.countEmailAuth(email);

		// 추후 이메일인증 횟수 DB에서 가져오도록 수정할것
		// 이메일인증횟수 3회초과일 경우 차단
		if (emailCount > 3) {
			RedirectView redirectView = new RedirectView(); // redirect url 설정
			redirectView.setUrl(httpServletRequest.getContextPath() + "/errors");
			// redirectView.setExposeModelAttributes(false);
			mav.setView(redirectView);
			mav.addObject("errorName", "이메일인증횟수초과");
			mav.addObject("errorCode", "10002");
			mav.addObject("errorDesc", "이메일인증횟수가 초과함");
			signService.updateUserStatus(email, 4); // 계정잠금
			return mav;
		}

		// 이메일 인증 횟수 추가 (이메일 인증 횟수 초과 확인 후 작업해야)
		signService.updateEmailAuthCount(email);

		// 이메일의 인증 시간 검증 (분당 시간)
		int time = signService.checkEmailAuthTime(email);
		logger.info("인증후 현재 시간과 차이 (분당) = " + time);
		// 추후 인증 시간도 db에서 관리하도록 수정
		// 인증 메일 시간이 60분 초과시 인증 안됨
		if (time > 60) {
			mav.addObject("email", email);
			mav.addObject("message", "이메일 인증 시간이 초과하였습니다. 재발송 후 인증 가능합니다.");
			mav.setViewName("sign/emailCheck");
			return mav;
		}
		// 이메일의 인증값 비교
		String authCode = signService.getEmailAuthCode(email);

		if (!authCode.equalsIgnoreCase(code)) {
			// 일치하지 않으면
			mav.addObject("email", email);
			mav.addObject("message", "이메일 인증코드가 일치하지 않습니다.");
			mav.setViewName("sign/emailCheck");
			return mav;
		}

		// 검증완료후 계정 활성화
		signService.activateUser(email);
		mav.setViewName("home");
		return mav;
	}

	/**
	 * 회원가입프로세스 나중에 ajax로 수정할 것, 이메일 양식 검증
	 * 
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/signupprocess", method = RequestMethod.POST)
	public ModelAndView signUpProcess(HttpServletRequest httpServletRequest, Locale locale) {

		ModelAndView mav = new ModelAndView("sign/signUp");
		Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
		String email = httpServletRequest.getParameter("email");
		email = email.trim();
		String password = httpServletRequest.getParameter("password");

		// 이메일 공백 검증
		if ((email == null) || "".equals(email)) {
			logger.info("이메일값 없음");
			RedirectView redirectView = new RedirectView(); // redirect url 설정
			redirectView.setUrl(httpServletRequest.getContextPath() + "/errors");
			// redirectView.setExposeModelAttributes(false);
			mav.setView(redirectView);
			mav.addObject("errorName", "email");
			mav.addObject("errorCode", "10001");
			mav.addObject("errorDesc", "이메일이 없음");
			return mav;
		}

		// 패스워드 공백 검증
		if ((password == null) || "".equals(password)) {
			logger.info("패스워드값 없음");
			logger.info("email : " + email);
			mav.addObject("email", email);
			mav.addObject("noPassword", "noPassword");
			mav.setViewName("sign/signUp");
			return mav;
		}

		int notValidTypeCode = signService.checkValidEmail(email);
		logger.info("notValidTypeCode : " + notValidTypeCode);
		switch (notValidTypeCode) {
		// 1 정상 -> 기 가입되어 있음 알리고 로그인화면으로 이동
		case 1:
			mav.setViewName("sign/signin");
			return mav;
		// 2 이메일인증대기 -> 이메일 인증 화면으로 이동
		case 2:
			mav.addObject("email", email);
			mav.setViewName("sign/emailCheck");
			return mav;
		// 3 계정만료 --> 계정만료안내화면으로 이동
		case 3:
			// 계정만료 화면 만들어야 함
			mav.setViewName("sign/emailCheck");
			return mav;
		// 4 계정잠금 --> 계정잠금안내화면으로 이동
		case 4:
			// 계정잠금화면 만들어야함
			logger.info("계정잠금");
			RedirectView redirectView = new RedirectView(); // redirect url 설정
			redirectView.setUrl(httpServletRequest.getContextPath() + "/errors");
			// redirectView.setExposeModelAttributes(false);
			mav.setView(redirectView);
			mav.addObject("errorName", "잠긴계정");
			mav.addObject("errorCode", "10003");
			mav.addObject("errorDesc", "계정이 잠겼습니다.");
			return mav;
		// 5 탈퇴 --> 탈퇴회원안내화면으로 이동
		case 5:
			// 탈퇴화면 만들어야함
			mav.setViewName("sign/emailCheck");
			return mav;

		default:
			break;
		}

		paramMap = signService.signUp(email, password);

		paramMap = signService.sendConfirmEmail(paramMap);
		mav.addObject("email", email);
		mav.setViewName("sign/emailCheck");
		return mav;
	}

}
