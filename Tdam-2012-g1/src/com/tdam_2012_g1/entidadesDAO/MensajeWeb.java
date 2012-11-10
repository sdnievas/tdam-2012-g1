package com.tdam_2012_g1.entidadesDAO;

public class MensajeWeb {
	int _id;
	int _nroRegistro;
	String _detalle;
	
	public MensajeWeb(int nroRegistro, String detalle){
		this._nroRegistro = nroRegistro;
		this._detalle = detalle;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public int get_nroRegistro() {
		return _nroRegistro;
	}

	public void set_nroRegistro(int _nroRegistro) {
		this._nroRegistro = _nroRegistro;
	}

	public String get_detalle() {
		return _detalle;
	}

	public void set_detalle(String _detalle) {
		this._detalle = _detalle;
	}
	
	

}
