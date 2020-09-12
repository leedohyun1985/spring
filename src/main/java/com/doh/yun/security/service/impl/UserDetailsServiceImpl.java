package com.doh.yun.security.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.doh.yun.security.dto.GrantedAuthorityImpl;
import com.doh.yun.security.dto.UserDetailsImpl;
import com.doh.yun.sign.service.SignService;

@Configuration
public class UserDetailsServiceImpl implements UserDetailsService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SignService signService;

	@Override
	public UserDetailsImpl loadUserByUsername(String email) throws UsernameNotFoundException {

		UserDetailsImpl user = signService.getUserByEmail(email);

		List<GrantedAuthorityImpl> authorities = new ArrayList<GrantedAuthorityImpl>();

		List<HashMap<String, Object>> authorityList = signService.getUserAuthoritiesByUserId(user.getUserId());

		for (HashMap<String, Object> hashMap : authorityList) {
			GrantedAuthorityImpl grantedAuthorityImpl = new GrantedAuthorityImpl();
			grantedAuthorityImpl.setName((String) hashMap.get("authority"));
			authorities.add(grantedAuthorityImpl);
		}
		
		user.setAuthorities(authorities);

		return user;

	}

}
