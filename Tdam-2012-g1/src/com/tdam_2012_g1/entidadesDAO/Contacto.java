package com.tdam_2012_g1.entidadesDAO;

public class Contacto {
	
	int _id;
	String _nombreWeb;
	
	public Contacto(int id, String nombre){
		this._id = id;
		this._nombreWeb = nombre;
	}
	
	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String get_nombreWeb() {
		return _nombreWeb;
	}

	public void set_nombreWeb(String _nombreWeb) {
		this._nombreWeb = _nombreWeb;
	}

}
