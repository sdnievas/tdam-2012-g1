package com.tdam.Class;


public class RegistroAccion {
	public static final String TIPO_LLAMADA = "Llamada";
	public static final String TIPO_SMS = "Mensaje de texto";
	public static final String TIPO_MAIL = "Mail";
	public static final String TIPO_WEB = "Mensaje web";
	public static final String TIPO_BLUETOOTH = "Mensaje bluetooth";

	private int idRegistro;
	private String fecha;
	private String idContacto;
	private String nombreContacto;
	private String tipoAccion;
	
	public RegistroAccion(){
		
	}

	public int getIdRegistro() {
		return idRegistro;
	}

	public void setIdRegistro(int idRegistro) {
		this.idRegistro = idRegistro;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getIdContacto() {
		return idContacto;
	}

	public void setIdContacto(String idContacto) {
		this.idContacto = idContacto;
	}

	public String getNombreContacto() {
		return nombreContacto;
	}

	public void setNombreContacto(String nombreContacto) {
		this.nombreContacto = nombreContacto;
	}

	public String getTipoAccion() {
		return tipoAccion;
	}

	public void setTipoAccion(String tipoAccion) {
		this.tipoAccion = tipoAccion;
	}
	
	
}
