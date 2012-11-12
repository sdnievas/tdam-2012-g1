package com.tdam_2012_g1.dom;

import java.util.Date;

import android.net.Uri;

public class HistorialSms {

	
	private String threadId;
	private int type;
	private String nombre;
	private String numero;
	private Date fecha;
	private String mensaje;
	private Uri imageUri;

	public String getThreadId() {
		return threadId;
	}

	public void setThreadId(String threadId) {
		this.threadId = threadId;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public HistorialSms() {

	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Uri getImageUri() {
		return imageUri;
	}
	
	public void setImageUri(Uri imageUri) {
		this.imageUri = imageUri;
	}

}
