package com.tdam.Class;

import java.util.Date;

public class Mail {
	int _id;
	String _idContacto;
	String _mailDestino;
	Date _fechaEnvio;

	public Mail() {

	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String get_idContacto() {
		return _idContacto;
	}

	public void set_idContacto(String _idContacto) {
		this._idContacto = _idContacto;
	}

	public String get_mailDestino() {
		return _mailDestino;
	}

	public void set_mailDestino(String _mailDestino) {
		this._mailDestino = _mailDestino;
	}

	public Date get_fechaEnvio() {
		return _fechaEnvio;
	}

	public void set_fechaEnvio(Date _fechaEnvio) {
		this._fechaEnvio = _fechaEnvio;
	}

}
