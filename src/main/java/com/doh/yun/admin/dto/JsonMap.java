package com.doh.yun.admin.dto;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

public class JsonMap {
	private Map<String, String> map = new HashMap<>();

	@JsonAnyGetter
	public Map<String, String> any(){
	    return this.map;
	}

	public Map<String, String> getMap() {
	    return this.map;
	}

	@JsonAnySetter
	public void setMap(String key, String value) {
	    map.put(key, value);
	}

	@Override
	public String toString() {
	    return "Map [map=" + map + "]";
	}
}
