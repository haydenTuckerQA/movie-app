package com.qa.util;

import com.google.gson.Gson;

public class JSONUtil {
	private Gson gson;
	
	public JSONUtil () {
		this.gson = new Gson();
	}
	
	public String getJSONForObject(Object obj) {
		return this.gson.toJson(obj);
	}
	
	public <T> T getObjectForJSON(String json, Class<T> classOfT) {
		return this.gson.fromJson(json, classOfT);
	}
}
