package com.tdam_2012_g1.entidades;

public class MensajeWeb {
	int _id;
	int _idContactoRemitente;
	int _idContactoDestinatario;
	String _fechaEnvio;
	String _detalle;
	
	public MensajeWeb(){
		
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public int get_idContactoRemitente() {
		return _idContactoRemitente;
	}

	public void set_idContactoRemitente(int _idContactoRemitente) {
		this._idContactoRemitente = _idContactoRemitente;
	}

	public int get_idContactoDestinatario() {
		return _idContactoDestinatario;
	}

	public void set_idContactoDestinatario(int _idContactoDestinatario) {
		this._idContactoDestinatario = _idContactoDestinatario;
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
