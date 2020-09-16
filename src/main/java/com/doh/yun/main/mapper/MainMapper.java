package com.doh.yun.main.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MainMapper {

	int insertLacaleData(Map<String, Object> paramMap);

}