package com.doh.yun.admin.dto;

import java.util.HashMap;

import org.springframework.jdbc.support.JdbcUtils;

public class CamelMap extends HashMap<String, Object> {

	private static final long serialVersionUID = 4754075503920309398L;
	
    @Override
    public Object put(String key, Object value) {
    	
        return super.put(JdbcUtils.convertUnderscoreNameToPropertyName(key), value);
        
    }
}
