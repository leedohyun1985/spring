package com.doh.yun.i18n.dto;

import java.util.HashMap;

import org.springframework.jdbc.support.JdbcUtils;

public class SnakeToCamelMap extends HashMap<String, Object> {
	
	private static final long serialVersionUID = -4031552103423959943L;

	@Override
    public Object put(String key, Object value) {
    	
        return super.put(JdbcUtils.convertUnderscoreNameToPropertyName(key), value);
        
    }
}
