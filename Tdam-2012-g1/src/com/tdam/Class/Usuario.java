package com.tdam.Class;

public class Usuario {

	int _id;
	String _nombre;
	String _contrase�a;
	String _mail;
	
	public Usuario(){		
	}
	
	public Usuario(int id,String nombre, String contrase�a){
		this._id = id;
		this._nombre = nombre;
		this._contrase�a=contrase�a;
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

	public String get_contrase�a() {
		return _contrase�a;
	}

	public void set_contrase�a(String _contrase�a) {
		this._contrase�a = _contrase�a;
	}

	public String get_mail() {
		return _mail;
	}

	public void set_mail(String _mail) {
		this._mail = _mail;
	}

	

}
