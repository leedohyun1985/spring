package com.doh.yun.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.doh.yun.admin.service.AdminService;
import com.doh.yun.security.dto.UserDetailsImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	AdminService service;

	@Value("#{properties['admin.databaseName']}")
	private String databaseName;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView admin(HttpServletRequest httpServletRequest, Locale locale) {
		ModelAndView modelAndView = new ModelAndView();

		/*
		 * Authentication authentication =
		 * SecurityContextHolder.getContext().getAuthentication();
		 * 
		 * logger.info("name : " + authentication.getName());
		 * logger.info("credentials : " + authentication.getCredentials());
		 * logger.info("details : " + authentication.getDetails());
		 * logger.info("principal : " + authentication.getPrincipal()); UserDetailsImpl
		 * detailsImpl = (UserDetailsImpl) authentication.getPrincipal();
		 * logger.info("userId : " + detailsImpl.getUserId()); logger.info("password : "
		 * + detailsImpl.getPassword()); for (GrantedAuthority grantedAuthority :
		 * authentication.getAuthorities()) { logger.info("grantedAuthority : " +
		 * grantedAuthority.getAuthority()); }
		 */
		modelAndView.setViewName("admin/admin");
		return modelAndView;
	}
	/*
	 * @RequestMapping(value = "/languages", method = RequestMethod.GET) public
	 * ModelAndView languages(HttpServletRequest httpServletRequest, Locale locale)
	 * { ModelAndView modelAndView = new ModelAndView();
	 * modelAndView.setViewName("admin/languages"); return modelAndView; }
	 * 
	 * @RequestMapping(value = "/users", method = RequestMethod.GET) public
	 * ModelAndView users(HttpServletRequest httpServletRequest, Locale locale) {
	 * ModelAndView modelAndView = new ModelAndView();
	 * modelAndView.setViewName("admin/users"); return modelAndView; }
	 * 
	 * @ResponseBody
	 * 
	 * @RequestMapping(value = "/userinfo", method = RequestMethod.GET) public
	 * String userinfo() throws JsonProcessingException{ Map<String, Object>
	 * paramMap = new LinkedHashMap<String, Object>(); ObjectMapper mapper = new
	 * ObjectMapper(); ArrayList<Map<String, Object>> list = (ArrayList<Map<String,
	 * Object>>) service.getUserInfo(paramMap); String data =
	 * mapper.writeValueAsString(list); return data; }
	 */

	@RequestMapping(value = "/database/{tableName}", method = RequestMethod.GET)
	public ModelAndView commonAdminView(@PathVariable("tableName") String tableName,
			HttpServletRequest httpServletRequest, Locale locale) {
		// 페이지 진입시 디폴트 페이지 필요
		// Not null 처리 필요

		ModelAndView modelAndView = new ModelAndView();

		Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
		paramMap.put("tableName", tableName);
		paramMap.put("databaseName", databaseName);
		String tableComment = service.getTableComment(paramMap);

		ArrayList<Map<String, Object>> tableList = (ArrayList<Map<String, Object>>) service
				.getTableNamesAndComments(paramMap);

		// 데이터베이스의 칼럼규칙인 snake_case를 따르는 값을 Map의 value값으로 가지고 있음
		ArrayList<Map<String, Object>> columnList = (ArrayList<Map<String, Object>>) service.getColumnData(paramMap);
		// key값은 camelCase를 따르고 있음
		ArrayList<Map<String, Object>> dataList = (ArrayList<Map<String, Object>>) service.getTableData(paramMap);

		ArrayList<Map<String, Object>> constraintList = (ArrayList<Map<String, Object>>) service
				.getColumnConstraintInfo(paramMap);

		ArrayList<String> exceptionColumnList = new ArrayList<String>();
		ArrayList<String> autoIncrementColumnList = new ArrayList<String>();
		ArrayList<String> primaryKeyList = new ArrayList<String>();
		// 외래키와 외래키의 값들을 담은 리스트
		ArrayList<Map<String, Object>> foreignKeyList = new ArrayList<Map<String, Object>>();

		for (Map<String, Object> map : columnList) {
			// 타임스탬프, user_id 입력 제외
			if (String.valueOf(map.get("dataType")).contains("timestamp")
					|| String.valueOf(map.get("columnName")).contains("c_user_id")
					|| String.valueOf(map.get("columnName")).contains("m_user_id")) {
				exceptionColumnList.add(String.valueOf(map.get("columnName")));
			}
		}

		for (int i = 0; i < constraintList.size(); i++) {
			Map<String, Object> map = constraintList.get(i);

			if ("PRIMARY KEY".equalsIgnoreCase((String) map.get("constraint_type"))) {
				primaryKeyList.add((String) map.get("column_name"));
			}

			if ("auto_increment".equalsIgnoreCase((String) map.get("extra"))) {
				autoIncrementColumnList.add((String) map.get("column_name"));
			}

			if ("FOREIGN KEY".equalsIgnoreCase((String) map.get("constraint_type"))) {
				// 외래키의 테이블 카탈로그, 스키마, 테이블, 칼럼 값
				// referenced_table_schema referenced_table_name referenced_column_name
				Map<String, Object> fkParamMap = new LinkedHashMap<String, Object>();
				fkParamMap.put("referenced_table_schema", map.get("referenced_table_schema"));
				fkParamMap.put("referenced_table_name", map.get("referenced_table_name"));
				fkParamMap.put("referenced_column_name", map.get("referenced_column_name"));
				Map<String, Object> fkResultMap = new LinkedHashMap<String, Object>();
				fkResultMap.put((String) map.get("referenced_column_name"),
						(ArrayList<String>) service.getReferencedColumnData(fkParamMap));
				foreignKeyList.add(fkResultMap);
			}
		}

		modelAndView.addObject("primaryKeyList", primaryKeyList);
		modelAndView.addObject("tableList", tableList);
		modelAndView.addObject("columnList", columnList);
		modelAndView.addObject("foreignKeyList", foreignKeyList);
		modelAndView.addObject("exceptionColumnList", exceptionColumnList);
		modelAndView.addObject("autoIncrementColumnList", autoIncrementColumnList);
		modelAndView.addObject("dataList", dataList);
		modelAndView.addObject("tableName", tableName);
		modelAndView.addObject("tableComment", tableComment);

		modelAndView.setViewName("admin/common");

		return modelAndView;
	}

	@ResponseBody
	@RequestMapping(value = "/database/select/{tableName}", method = RequestMethod.POST)
	public String commonAdminSelect(@PathVariable("tableName") String tableName, Model model)
			throws JsonProcessingException {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		ArrayList<Map<String, Object>> list = (ArrayList<Map<String, Object>>) service.getUserInfo(paramMap);
		String data = mapper.writeValueAsString(list);
		return data;
	}

	@ResponseBody
	@RequestMapping(value = "/database/insert/{tableName}", method = RequestMethod.POST)
	public String commonAdminInsert(@PathVariable("tableName") String tableName, Model model, @RequestBody String data)
			throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> dataList = mapper.readValue(data, List.class);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();

		Map<String, Object> userMap = new HashMap<String, Object>();
		userMap.put("name", "m_user_id");
		userMap.put("value", userDetailsImpl.getUserId());
		dataList.add(userMap);
		userMap = new HashMap<String, Object>();
		userMap.put("name", "c_user_id");
		userMap.put("value", userDetailsImpl.getUserId());
		dataList.add(userMap);

		paramMap.put("tableName", tableName);
		paramMap.put("dataList", dataList);
		int result = service.insertTableDataCommon(paramMap);
		return String.valueOf(result);
	}

	@ResponseBody
	@RequestMapping(value = "/database/update/{tableName}", method = RequestMethod.POST)
	public String commonAdminUpdate(@PathVariable("tableName") String tableName, Model model, @RequestBody String data)
			throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		ArrayList<Map<String, Object>> primaryKeyList = new ArrayList<Map<String, Object>>();
		ArrayList<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		List<String> primaryKeyStringList = new ArrayList<String>();

		ObjectMapper mapper = new ObjectMapper();
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> mapList = mapper.readValue(data, List.class);

		for (int i = 0; i < mapList.size(); i++) {
			Map<String, Object> map = mapList.get(i);
			boolean flag = true;
			Iterator<String> keys = map.keySet().iterator();
			while (keys.hasNext()) {
				String key = keys.next();
				if (key.equalsIgnoreCase("name") && String.valueOf(map.get(key)).contains("primaryKey")) {
					primaryKeyList.add(map);
					flag = false;
				}
			}
			if (flag) {
				dataList.add(map);
			}
		}

		for (int i = 0; i < primaryKeyList.size(); i++) {
			Map<String, Object> map = primaryKeyList.get(i);

			Iterator<String> keys = map.keySet().iterator();
			while (keys.hasNext()) {
				String key = keys.next();
				if (key.equalsIgnoreCase("value")) {
					primaryKeyStringList.add(String.valueOf(map.get(key)));
				}
			}
		}

		primaryKeyList = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < primaryKeyStringList.size(); i++) {
			String primaryKey = primaryKeyStringList.get(i);
			for (int j = 0; j < mapList.size(); j++) {
				Map<String, Object> map = mapList.get(j);
				Iterator<String> keys = map.keySet().iterator();
				while (keys.hasNext()) {
					String key = keys.next();
					if (key.equalsIgnoreCase("name") && String.valueOf(map.get(key)).contains(primaryKey)) {
						primaryKeyList.add(map);
					}
				}
			}
		}

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
		Map<String, Object> userMap = new HashMap<String, Object>();
		userMap.put("name", "m_user_id");
		userMap.put("value", userDetailsImpl.getUserId());
		dataList.add(userMap);
		paramMap.put("tableName", tableName);
		paramMap.put("primaryKeyList", primaryKeyList);
		paramMap.put("dataList", dataList);
		int result = service.updateTableDataCommon(paramMap);
		return String.valueOf(result);
	}

	@ResponseBody
	@RequestMapping(value = "/database/delete/{tableName}", method = RequestMethod.POST)
	public String commonAdminDelete(@PathVariable("tableName") String tableName, @RequestBody String data)
			throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<Map<String, Object>> primaryKeyList = new ArrayList<Map<String, Object>>();
		List<String> primaryKeyStringList = new ArrayList<String>();
		ObjectMapper mapper = new ObjectMapper();
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> mapList = mapper.readValue(data, List.class);

		for (int i = 0; i < mapList.size(); i++) {
			Map<String, Object> map = mapList.get(i);
			Iterator<String> keys = map.keySet().iterator();
			while (keys.hasNext()) {
				String key = keys.next();
				if (key.equalsIgnoreCase("name") && String.valueOf(map.get(key)).contains("primaryKey")) {
					primaryKeyList.add(map);
				}
			}
		}

		for (int i = 0; i < primaryKeyList.size(); i++) {
			Map<String, Object> map = primaryKeyList.get(i);

			Iterator<String> keys = map.keySet().iterator();
			while (keys.hasNext()) {
				String key = keys.next();
				if (key.equalsIgnoreCase("value")) {
					primaryKeyStringList.add(String.valueOf(map.get(key)));
				}
			}
		}

		primaryKeyList = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < primaryKeyStringList.size(); i++) {
			String primaryKey = primaryKeyStringList.get(i);
			for (int j = 0; j < mapList.size(); j++) {
				Map<String, Object> map = mapList.get(j);
				Iterator<String> keys = map.keySet().iterator();
				while (keys.hasNext()) {
					String key = keys.next();
					if (key.equalsIgnoreCase("name") && String.valueOf(map.get(key)).contains(primaryKey)) {
						primaryKeyList.add(map);
					}
				}
			}
		}

		paramMap.put("tableName", tableName);
		paramMap.put("primaryKeyList", primaryKeyList);

		int result = service.deleteTableDataCommon(paramMap);
		return String.valueOf(result);
	}

}
