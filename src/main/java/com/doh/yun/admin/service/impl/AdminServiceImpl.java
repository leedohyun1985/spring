package com.doh.yun.admin.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doh.yun.admin.mapper.AdminMapper;
import com.doh.yun.admin.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	AdminMapper mapper;

	@Override
	public List<Map<String, Object>> getUserInfo(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return mapper.selectUserInfo(paramMap);
	}
	
	@Override
	public String getTableComment(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return mapper.selectTableComment(paramMap);
	}
	
	@Override
	public List<Map<String, Object>> getTableNamesAndComments(Map<String, Object> paramMap) {
		return mapper.selectTableNamesAndComments(paramMap);
	}

	@Override
	public List<Map<String, Object>> getTableData(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return mapper.selectTableData(paramMap);
	}

	@Override
	public List<Map<String, Object>> getColumnData(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return mapper.selectColumnData(paramMap);
	}

	@Override
	public List<Map<String, Object>> getColumnConstraintInfo(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return mapper.selectColumnConstraintInfo(paramMap);
	}

	@Override
	public List<String> getReferencedColumnData(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return mapper.selectReferencedColumnData(paramMap);
	}

	@Override
	public int insertTableDataCommon(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return mapper.insertTableDataCommon(paramMap);
	}

	@Override
	public int updateTableDataCommon(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return mapper.updateTableDataCommon(paramMap);
	}

	@Override
	public int deleteTableDataCommon(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return mapper.deleteTableDataCommon(paramMap);
	}
	
}
