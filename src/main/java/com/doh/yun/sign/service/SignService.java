package com.doh.yun.sign.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.doh.yun.security.dto.UserDetailsImpl;

@Service
public interface SignService {

	/**
	 * 유효한 이메일인지 검증
	 * 
	 * @param email
	 * @return
	 */
	@Transactional
	int checkValidEmail(String email);

	/**
	 * 회원가입
	 * 
	 * @param email
	 * @param password
	 * @return
	 */
	@Transactional
	Map<String, Object> signUp(String email, String password);

	/**
	 * 회원가입 인증 이메일 발송
	 * 
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> sendConfirmEmail(Map<String, Object> paramMap);

	/**
	 * 사용자 상태 업데이트 (사용자고유번호 알고 있는 경우)
	 * 
	 * @param userId
	 * @param status
	 * @return
	 */
	int updateUserStatus(int userId, int status);

	/**
	 * 사용자 상태 업데이트 (사용자 이메일 알고 있는 경우)
	 * 
	 * @param userId
	 * @param status
	 * @return
	 */
	int updateUserStatus(String userEmail, int status);

	/**
	 * 이메일 인증 횟수 조회 (사용자 이메일)
	 * 
	 * @param userEmail
	 * @return
	 */
	int countEmailAuth(String userEmail);

	/**
	 * 이메일 인증 횟수 조회 (사용자 고유 번호)
	 * 
	 * @param userId
	 * @return
	 */
	int countEmailAuth(int userId);

	/**
	 * 이메일 인증 횟수 1 추가
	 * 
	 * @param email
	 */
	void updateEmailAuthCount(String email);

	/**
	 * 이메일 인증 경과 시간 체크
	 * 
	 * @param email
	 * @return
	 */
	int checkEmailAuthTime(String email);

	/**
	 * 이메일 인증 횟수 초기화
	 * 
	 * @param email
	 */
	void resetEmailAuthCount(String email);

	/**
	 * 이메일 인증 코드 조회
	 * 
	 * @param email
	 * @return
	 */
	String getEmailAuthCode(String email);

	/**
	 * 회원가입 계정 활성화
	 * 
	 * @param email
	 */
	void activateUser(String email);

	/**
	 * 회원가입계정 초기 권한 부여
	 * 
	 * @param email
	 */
	void setDefaultUserRole(String email);

	/**
	 * 로그인 객체
	 * 
	 * @param email
	 * @return
	 */
	UserDetailsImpl getUserByEmail(String email);

	/**
	 * 유저의 소유 권한 가져오기
	 * 
	 * @param userId
	 * @return
	 */
	List<HashMap<String, Object>> getUserAuthoritiesByUserId(String userId);

}