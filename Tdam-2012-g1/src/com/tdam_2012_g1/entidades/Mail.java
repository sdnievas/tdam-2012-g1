package com.tdam_2012_g1.entidades;

public class Mail {
	int _id;
	int _idUsuarioRemitente;
	String _mailDestinatario;
	String _fechaEnvio;
	
	public Mail(){
		
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public int get_idUsuarioRemitente() {
		return _idUsuarioRemitente;
	}

	public void set_idUsuarioRemitente(int _idUsuarioRemitente) {
		this._idUsuarioRemitente = _idUsuarioRemitente;
	}

	public String get_mailDestinatario() {
		return _mailDestinatario;
	}

	public void set_mailDestinatario(String _mailDestinatario) {
		this._mailDestinatario = _mailDestinatario;
	}

	public String get_fechaEnvio() {
		return _fechaEnvio;
	}

	public void set_fechaEnvio(String _fechaEnvio) {
		this._fechaEnvio = _fechaEnvio;
	}
	
	

}
