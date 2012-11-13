package com.tdam_2012_g1.entidades;
import java.util.Date;;

public class Registro {
	int _id;
	int _idContacto;
	String _fechaRegistro;
	int _idTipoRegistro;
	
	public Registro(int idContacto, String fecha, int idTipoRegistro){
		this._idContacto = idContacto;
		this._fechaRegistro = fecha;
		this._idTipoRegistro = idTipoRegistro;		
	}
	
	
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public int get_idContacto() {
		return _idContacto;
	}
	public void set_idContacto(int _idContacto) {
		this._idContacto = _idContacto;
	}
	public String get_fechaRegistro() {
		return _fechaRegistro;
	}
	public void set_fechaRegistro(String _fechaRegistro) {
		this._fechaRegistro = _fechaRegistro;
	}
	public int get_idTipoRegistro() {
		return _idTipoRegistro;
	}
	public void set_idTipoRegistro(int _idTipoRegistro) {
		this._idTipoRegistro = _idTipoRegistro;
	}

}
