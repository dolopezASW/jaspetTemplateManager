package com.asw.exceptions;

public class TemplateNotFoundException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String message = "Error. Jasper template could not be found";
	
	public TemplateNotFoundException() {
		super.getMessage();
	}

}
