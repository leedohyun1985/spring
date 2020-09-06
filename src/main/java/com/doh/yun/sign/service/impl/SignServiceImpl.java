package com.doh.yun.sign.service.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.doh.yun.security.dto.UserDetailsImpl;
import com.doh.yun.sign.mapper.SignMapper;
import com.doh.yun.sign.service.SignService;

@Service
public class SignServiceImpl implements SignService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("#{properties['signup.email.path']}")
	private String emailPath;

	@Autowired
	SignMapper signMapper;

	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public int checkValidEmail(String email) {
		String rtn = signMapper.selectDuplicateEmailCount(email);
		return (null == rtn) ? 0 : Integer.parseInt(rtn);
	}

	@Override
	public Map<String, Object> signUp(String email, String password) {
		// TODO Auto-generated method stub
		Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		paramMap.put("email", email);
		paramMap.put("password", bCryptPasswordEncoder.encode(password));
		signMapper.insertUserToUsers(paramMap);
		signMapper.insertUserToUserStatus(paramMap);
		return paramMap;
	}

	@Override
	public Map<String, Object> sendConfirmEmail(Map<String, Object> paramMap) {
		String email = (String) paramMap.get("email");
		resetEmailAuthCount(email);
		Random random = new Random();
		int randomCode = random.nextInt(99999999);
		paramMap.put("email_confirm_code", randomCode);
		signMapper.insertEmailCode(paramMap);
		String setfrom = "이도현 <leedohyun1985@gmail.com>";
		String tomail = email; // 받는 사람 이메일
		String title = "인증 번호"; // 제목
		String content = "<!DOCTYPE html>\n" + "귀하의 인증 번호는 :<h2><strong>" + String.valueOf(randomCode)
				+ "</strong></h2>\n" // 내용
				+ "<h2><a href='" + emailPath + "?email=" + email + "&code=" + randomCode + "'>인증하기</a></h2>\n"
		/*
		 * + "<button type='button' onclick='location.href=" + emailPath + "?email=" +
		 * email + "&code=" + randomCode + "'>이메일인증</button>"
		 */;

		// 이메일 폼 추가
		// 이메일 전송 프로퍼티로 호출하게 변경
		// 이메일 인증 폼 작성

		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

			messageHelper.setFrom(setfrom); // 보내는사람 생략하거나 하면 정상작동을 안함
			messageHelper.setTo(tomail); // 받는사람 이메일
			messageHelper.setSubject(title); // 메일제목은 생략이 가능하다
			messageHelper.setText(content, true); // 메일 내용

			javaMailSender.send(message);
		} catch (Exception e) {
			System.out.println(e);
		}
		return paramMap;
	}

	@Override
	public void resetEmailAuthCount(String email) {
		signMapper.updateEmailAuthCountToZero(email);
	}

	@Override
	public int updateUserStatus(int userId, int status) {
		Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
		paramMap.put("userId", userId);
		paramMap.put("status", status);
		return signMapper.updateUserStatusById(paramMap);
	}

	@Override
	public int updateUserStatus(String userEmail, int status) {
		Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
		paramMap.put("email", userEmail);
		paramMap.put("status", status);
		return signMapper.updateUserStatusByEmail(paramMap);
	}

	@Override
	public int countEmailAuth(String email) {
		return signMapper.selectEmailAuthCountByEmail(email);
	}

	@Override
	public int countEmailAuth(int userId) {
		return signMapper.selectEmailAuthCountById(userId);
	}

	@Override
	public void updateEmailAuthCount(String email) {
		signMapper.updateEmailAuthCountAddOne(email);
	}

	@Override
	public int checkEmailAuthTime(String email) {
		return signMapper.selectEmailAuthTimeDiff(email);
	}

	@Override
	public String getEmailAuthCode(String email) {
		return signMapper.selectEmailAuthCode(email);
	}

	@Override
	public void activateUser(String email) {
		updateUserStatus(email, 1);
		setDefaultUserRole(email);
	}

	@Override
	public void setDefaultUserRole(String email) {
		signMapper.insertDefaultUserRole(email);
	}

	@Override
	public UserDetailsImpl getUserByEmail(String email) {
		return signMapper.selectUserInfo(email);
	}

	@Override
	public List<HashMap<String, Object>> getUserAuthoritiesByUserId(String userId) {
		return signMapper.selectUserAuthoritiesByUserId(userId);
	}

}
