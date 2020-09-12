package com.doh.yun.admin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface AdminService {

	/**
	 * 전체 사용자정보 조회
	 * 
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> getUserInfo(Map<String, Object> paramMap);

	/**
	 * 테이블 코멘트 조회
	 * 
	 * @param databaseName
	 * @param tableName
	 * @return
	 */
	String getTableComment(Map<String, Object> paramMap);

	/**
	 * 전체 테이블 명, 코멘트 조
	 * 
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> getTableNamesAndComments(Map<String, Object> paramMap);

	/**
	 * 테이블 명을 기준으로 데이터 조회
	 * 
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> getTableData(Map<String, Object> paramMap);

	/**
	 * 테이블내의 칼럼들 조회
	 * 
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> getColumnData(Map<String, Object> paramMap);

	/**
	 * 테이블내 칼럼 중 Constraint 조회 (pk, fk, index)
	 * 
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> getColumnConstraintInfo(Map<String, Object> paramMap);

	/**
	 * 외래키 데이터 조회
	 * 
	 * @param fkParamMap
	 * @return
	 */
	List<String> getReferencedColumnData(Map<String, Object> paramMap);

	/**
	 * 파라미터를 받아 테이블에 데이터 입력
	 * @param paramMap
	 * @return
	 */
	int insertTableDataCommon(Map<String, Object> paramMap);

	/**
	 * 파라미터를 받아 테이블의 데이터 갱신
	 * @param paramMap
	 * @return
	 */
	int updateTableDataCommon(Map<String, Object> paramMap);

	/**
	 * 파라미터를 받아 테이블의 데이터 삭제
	 * @param paramMap
	 * @return
	 */
	int deleteTableDataCommon(Map<String, Object> paramMap);

}