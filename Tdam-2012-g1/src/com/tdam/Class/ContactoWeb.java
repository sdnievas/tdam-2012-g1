package com.tdam.Class;

public class ContactoWeb {
	
	int _id;
	String _nombreWeb;
	
	public ContactoWeb(int id, String nombre){
		this._id = id;
		this._nombreWeb = nombre;
	}
	
	public ContactoWeb(){

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
