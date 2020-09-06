package com.doh.yun.sign.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.doh.yun.security.dto.UserDetailsImpl;

@Mapper

public interface SignMapper {

	String selectDuplicateEmailCount(String email);

	void insertUserToUsers(Map<String, Object> paramMap);

	void insertUserToUserStatus(Map<String, Object> paramMap);

	void insertEmailCode(Map<String, Object> paramMap);

	int updateUserStatusById(Map<String, Object> paramMap);

	int updateUserStatusByEmail(Map<String, Object> paramMap);

	int selectEmailAuthCountByEmail(String email);

	int selectEmailAuthCountById(int userId);

	void updateEmailAuthCountAddOne(String email);

	int selectEmailAuthTimeDiff(String email);

	void updateEmailAuthCountToZero(String email);

	String selectEmailAuthCode(String email);

	void insertDefaultUserRole(String email);

	UserDetailsImpl selectUserInfo(String email);

	List<HashMap<String, Object>> selectUserAuthoritiesByUserId(String userId);

}