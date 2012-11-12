package com.tdam_2012_g1.dom;

public class Conectividad {

	private int _id;
	private String connection;
	private String status;
	private String fecha;
	private String hora;
	
	public static final String STATUS_CONNECTED = "CONNECTED";
	public static final String STATUS_DISCONNECTED = "DISCONNECTED";

	public Conectividad() {

	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String getConnection() {
		return connection;
	}

	public void setConnection(String connection) {
		this.connection = connection;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

}
