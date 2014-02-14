package com.tdam.Class;

import java.io.Serializable;

public class MensajeWeb implements Serializable {
	
	int _id;
	String _usuario;
	String _contacto;
	String _fechaEnvio;
	String _detalle;
	int type;

	public MensajeWeb() {
	}
	
	public MensajeWeb(String detalle) {
		this._detalle = detalle;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String get_usuario() {
		return _usuario;
	}

	public void set_usuario(String _usuario) {
		this._usuario = _usuario;
	}

	public String get_contacto() {
		return _contacto;
	}

	public void set_contacto(String _contacto) {
		this._contacto = _contacto;
	}

	public String get_fechaEnvio() {
		return _fechaEnvio;
	}

	public void set_fechaEnvio(String _fechaEnvio) {
		this._fechaEnvio = _fechaEnvio;
	}

	public String get_detalle() {
		return _detalle;
	}

	public void set_detalle(String _detalle) {
		this._detalle = _detalle;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
