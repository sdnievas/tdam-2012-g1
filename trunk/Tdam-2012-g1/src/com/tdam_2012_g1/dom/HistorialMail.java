package com.tdam_2012_g1.dom;

import java.util.Date;

public class HistorialMail {
	private int _id;
	private boolean eliminar;
	private String contacto;
	private String mailContacto;
	private String mailEmisor;
	private Date fecha;

	public HistorialMail() {

	}

	public int get_id() {
		return _id;
	}

	public void set_id(int id) {
		this._id = id;
	}

	public boolean isEliminar() {
		return eliminar;
	}

	public void setEliminar(boolean eliminar) {
		this.eliminar = eliminar;
	}

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public String getMailContacto() {
		return mailContacto;
	}

	public void setMailContacto(String mailContacto) {
		this.mailContacto = mailContacto;
	}

	public String getMailEmisor() {
		return mailEmisor;
	}

	public void setMailEmisor(String mailEmisor) {
		this.mailEmisor = mailEmisor;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date date) {
		this.fecha = date;
	}


}
