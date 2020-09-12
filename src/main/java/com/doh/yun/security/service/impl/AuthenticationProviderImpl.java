package com.doh.yun.security.service.impl;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.doh.yun.security.dto.UserDetailsImpl;

@Configuration
public class AuthenticationProviderImpl implements AuthenticationProvider {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String rawPassword = (String) authentication.getCredentials();
		String hashedPassword;
		UserDetailsImpl userDetailsImpl;
		Collection<? extends GrantedAuthority> authorities;
		
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		try {
			userDetailsImpl = userDetailsServiceImpl.loadUserByUsername(username);
			hashedPassword = bCryptPasswordEncoder.encode(rawPassword);
			if (!bCryptPasswordEncoder.matches(rawPassword, userDetailsImpl.getPassword()))
				throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
			authorities = userDetailsImpl.getAuthorities();
		} catch (UsernameNotFoundException e) {
			logger.info(e.toString());
			throw new UsernameNotFoundException(e.getMessage());
		} catch (BadCredentialsException e) {
			logger.info(e.toString());
			throw new BadCredentialsException(e.getMessage());
		} catch (Exception e) {
			logger.info(e.toString());
			throw new RuntimeException(e.getMessage());
		}
		return new UsernamePasswordAuthenticationToken(userDetailsImpl, hashedPassword, authorities);

	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}

}
