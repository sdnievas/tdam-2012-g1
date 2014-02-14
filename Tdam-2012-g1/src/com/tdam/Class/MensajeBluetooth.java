package com.tdam.Class;

import java.io.Serializable;

public class MensajeBluetooth implements Serializable {
	
	int _id;
	
	String _usuario;
	
	String _contactoMAC;
	
	String _fechaEnvio;
	
	String _Mensaje;
	
	int _Type;

	

	public MensajeBluetooth () {
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	
	public String get_fechaEnvio() {
		return _fechaEnvio;
	}

	public void set_fechaEnvio(String _fechaEnvio) {
		this._fechaEnvio = _fechaEnvio;
	}

	public String get_Mensaje() {
		return _Mensaje;
	}

	public void set_Mensaje(String _Mensaje) {
		this._Mensaje = _Mensaje;
	}

	
	public String get_usuario() {
		return _usuario;
	}

	public void set_usuario(String _usuario) {
		this._usuario = _usuario;
	}

	public String get_contactoMAC() {
		return _contactoMAC;
	}

	public void set_contactoMAC(String _contactoMAC) {
		this._contactoMAC = _contactoMAC;
	}

	public int get_Type() {
		return _Type;
	}

	public void set_Type(int _Type) {
		this._Type = _Type;
	}

	
}
