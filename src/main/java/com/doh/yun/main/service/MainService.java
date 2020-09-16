package com.doh.yun.main.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface MainService {
	/**
	 * 로캘 데이터 입력 추후 위치 바꾸기
	 * 
	 * @param paramMap
	 * @return
	 */
	int putLacaleData(Map<String, Object> paramMap);

}