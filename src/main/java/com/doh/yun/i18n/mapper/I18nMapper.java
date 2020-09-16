package com.doh.yun.i18n.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface I18nMapper {

	List<Map<String, String>> selectAllMessages();

}