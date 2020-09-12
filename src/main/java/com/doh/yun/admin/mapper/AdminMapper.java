package com.doh.yun.admin.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper {
	
	List<Map<String, Object>> selectUserInfo(Map<String, Object> paramMap);

	String selectTableComment(Map<String, Object> paramMap);
	
	List<Map<String, Object>> selectTableNamesAndComments(Map<String, Object> paramMap);

	List<Map<String, Object>> selectTableData(Map<String, Object> paramMap);

	List<Map<String, Object>> selectColumnData(Map<String, Object> paramMap);
	
	List<Map<String, Object>> selectColumnConstraintInfo(Map<String, Object> paramMap);

	List<String> selectReferencedColumnData(Map<String, Object> paramMap);

	int insertTableDataCommon(Map<String, Object> paramMap);

	int updateTableDataCommon(Map<String, Object> paramMap);

	int deleteTableDataCommon(Map<String, Object> paramMap);

}