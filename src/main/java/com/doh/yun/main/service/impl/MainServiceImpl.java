package com.doh.yun.main.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doh.yun.main.mapper.MainMapper;
import com.doh.yun.main.service.MainService;

@Service
public class MainServiceImpl implements MainService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	MainMapper mapper;

	@Override
	public int putLacaleData(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return mapper.insertLacaleData(paramMap);
	}

	
}
