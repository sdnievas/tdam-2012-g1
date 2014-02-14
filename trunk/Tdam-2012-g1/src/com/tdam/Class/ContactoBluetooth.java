package com.tdam.Class;

public class ContactoBluetooth {

	int _id;
	String _nombreBluetooth;
	String _Mac;
	
	public ContactoBluetooth(int id, String nombre, String Mac){
		this._id = id;
		this._nombreBluetooth = nombre;
		this._Mac = Mac;
	}
	
	public ContactoBluetooth(){

	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String get_nombreBluetooth() {
		return _nombreBluetooth;
	}

	public void set_nombreBluetooth(String _nombreBluetooth) {
		this._nombreBluetooth = _nombreBluetooth;
	}

	public String get_Mac() {
		return _Mac;
	}

	public void set_Mac(String _Mac) {
		this._Mac = _Mac;
	}
	

}
