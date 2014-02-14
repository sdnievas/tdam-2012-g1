package com.tdam.Class;

import java.sql.Date;

import android.provider.ContactsContract.Contacts.Data;

public class Mail {
	int _id;
	String _idUsuarioRemitente;
	String _mailDestinatario;
	String _fechaEnvio;
	String _asunto;
	
	public Mail(){
		
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String get_idUsuarioRemitente() {
		return _idUsuarioRemitente;
	}

	public void set_idUsuarioRemitente(String _idUsuarioRemitente) {
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

	public String get_asunto() {
		return _asunto;
	}

	public void set_asunto(String _asunto) {
		this._asunto = _asunto;
	}
	
	

}
