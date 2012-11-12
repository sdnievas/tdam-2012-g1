package com.tdam_2012_g1.dom;

import java.util.Date;

import android.net.Uri;

public class HistorialLlamada {
	
	private int _ID;
	private Uri imagen;
	private String nombre;
	private String numero;
	private Date fecha;
	private String duracion;
	private int tipoNumero;
	private int tipoLlamada;
	private boolean eliminar;
	public final static int CONTACT_TYPE_NONE = 0;
	public final static int CONTACT_TYPE_MOBILE = 1;
	public final static int CONTACT_TYPE_HOME = 2;

	// TODO Agregar las demas opciones de tipo de numero de contacto

	public HistorialLlamada() {

	}

	public int get_ID() {
		return _ID;
	}

	public void set_ID(int id) {
		_ID = id;
	}

	public Uri getImagen() {
		return imagen;
	}

	public void setImagen(Uri imagen) {
		this.imagen = imagen;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getDuracion() {
		return duracion;
	}

	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}

	public int getTipoNumero() {
		return tipoNumero;
	}

	public void setTipoNumero(int tipoNumero) {
		this.tipoNumero = tipoNumero;
	}

	public int getTipoLlamada() {
		return tipoLlamada;
	}

	public void setTipoLlamada(int tipoLlamada) {
		this.tipoLlamada = tipoLlamada;
	}

	public boolean isEliminar() {
		return eliminar;
	}

	public void setEliminar(boolean eliminar) {
		this.eliminar = eliminar;
	}

		public String getFilterContactValue() {
		return nombre;
	}

	public Date getFilterDateValue() {
		return fecha; 
	}

	public int getFilter() {
		return tipoLlamada;
	}
}
