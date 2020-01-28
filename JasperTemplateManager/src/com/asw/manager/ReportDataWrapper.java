package com.asw.manager;

import java.util.Map;

public class ReportDataWrapper {
	
	private Map<String,Object> parameters;

	public ReportDataWrapper(Object parameters) {
		
	}
	
	public Map<String,Object> castDataSource(Object bean){
		try {
			this.parameters = (Map<String, Object>) bean;
			return this.parameters;
		}catch(Exception ex) {
			return null;
		}
	}
	
	public Map<String, Object> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}
	
	

}
