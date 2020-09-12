package com.doh.yun.admin.dto;

import java.util.HashMap;

public class JsonAjaxMap extends HashMap<String, Object> {

	private static final long serialVersionUID = 4754075503920309398L;
	
    @Override
    public Object put(String key, Object value) {
    	
    	System.out.println(value.getClass().getTypeName());
    	
        return super.put(key, value);
                
    }
}
