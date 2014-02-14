package com.tdam.ServicioWeb;

public class ReceivedMessageInfo extends WebServiceInfo{

	private String from;
	private String timestamp;
	private String message;
	
	public ReceivedMessageInfo(){
		super();
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String mensaje) {
		this.message = mensaje;
	}

}
