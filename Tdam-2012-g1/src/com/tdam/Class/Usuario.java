package com.tdam.Class;

public class Usuario {

	int _id;
	String _nombre;
	String _contraseņa;
	String _mail;
	
	public Usuario(){		
	}
	
	public Usuario(int id,String nombre, String contraseņa){
		this._id = id;
		this._nombre = nombre;
		this._contraseņa=contraseņa;
	}
	
	public int get_id() {
		return _id;
	}
	


	public void set_id(int _id) {
		this._id = _id;
	}

	public String get_nombre() {
		return _nombre;
	}

	public void set_nombre(String _nombre) {
		this._nombre = _nombre;
	}

	public String get_contraseņa() {
		return _contraseņa;
	}

	public void set_contraseņa(String _contraseņa) {
		this._contraseņa = _contraseņa;
	}

	public String get_mail() {
		return _mail;
	}

	public void set_mail(String _mail) {
		this._mail = _mail;
	}

	

}
