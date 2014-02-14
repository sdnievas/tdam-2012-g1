package com.tdam.Class;

public class Usuario {

	int _id;
	String _nombre;
	String _contraseña;
	String _mail;
	
	public Usuario(){		
	}
	
	public Usuario(int id,String nombre, String contraseña){
		this._id = id;
		this._nombre = nombre;
		this._contraseña=contraseña;
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

	public String get_contraseña() {
		return _contraseña;
	}

	public void set_contraseña(String _contraseña) {
		this._contraseña = _contraseña;
	}

	public String get_mail() {
		return _mail;
	}

	public void set_mail(String _mail) {
		this._mail = _mail;
	}

	

}
