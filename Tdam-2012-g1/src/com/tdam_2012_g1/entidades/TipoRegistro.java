package com.tdam_2012_g1.entidades;

public class TipoRegistro {
	int _id;
	String _nombre;
	
	public TipoRegistro(String nombre){
		this._nombre = nombre;
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
	
	
	
	
}
