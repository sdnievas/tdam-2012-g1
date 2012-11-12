package com.tdam_2012_g1.dom;

import java.util.Date;

import android.util.Log;



public class HistorialWebSms {

	private boolean eliminar;
	private int _id;
	private String contacto;
	private long fecha;
	private String mensaje;
	private short tipo;
	private int outbox;


	public static final short TYPE_SENT = 2;
	public static final short TYPE_RECEIVED = 1;

	public static final int TYPE_INBOX = 0;
	public static final int TYPE_OUTBOX = 1;

	public static final String LOCATION_TAG = "#&locMAP&#";
	public static final String LOCATION_TAG_SLASH = ";";
	public static final String LOCATION_TAG_DOT = ":";

	public HistorialWebSms() {

	}

	public boolean isEliminar() {
		return eliminar;
	}

	public void setEliminar(boolean eliminar) {
		this.eliminar = eliminar;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public long getFecha() {
		return fecha;
	}

	public void setFecha(long fecha) {
		this.fecha = fecha;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public short getTipo() {
		return tipo;
	}

	public void setTipo(short tipo) {
		this.tipo = tipo;
	}


	public String getFilterContactValue() {
		return contacto;
	}

	
	public Date getFilterDateValue() {
		return new Date(fecha);
	}

	
	public int getFilter() {
		return tipo;
	}

	public int getOutbox() {
		return outbox;
	}

	public void setOutbox(int outbox) {
		this.outbox = outbox;
	}

		
}
