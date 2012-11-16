package com.tdam_2012_g1.entidades;

import java.io.Serializable;

public class MensajeWeb implements Serializable {
	int _id;
	String _remitente;
	String _destinatario;
	String _fechaEnvio;
	String _detalle;

	public MensajeWeb() {
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String get_remitente() {
		return _remitente;
	}

	public void set_remitente(String _remitente) {
		this._remitente = _remitente;
	}

	public String get_destinatario() {
		return _destinatario;
	}

	public void set_destinatario(String _destinatario) {
		this._destinatario = _destinatario;
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
	

}
