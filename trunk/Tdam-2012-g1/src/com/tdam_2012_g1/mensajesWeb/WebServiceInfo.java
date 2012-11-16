package com.tdam_2012_g1.mensajesWeb;

public class WebServiceInfo {

	private int code;
	private String type;
	private String description;

	public static final int INTERNAL_SERVER_ERROR = -1;
	public static final int FATAL_ERROR = 0;
	public static final int SUCCESS = 1;
	public static final int ERROR = 2;
	public static final int USER_EXIST_CODE=5;
	public static final String SUCCESS_MESSAGE = "success";
	public static final String ERROR_MESSAGE = "error";
	public static final String FATAL_ERROR_MESSAGE = "internal error";
	public static final String INTERNAL_SERVER_ERROR_MESSAGE = "internal server error";

	public WebServiceInfo() {

	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		StringBuffer info = new StringBuffer();
		info.append("code: "+this.code);
		info.append("\ntype: "+this.type);
		info.append("\ndescription: "+this.description);
		return info.toString();
	}
}
