package com.tdam.Class;

import java.io.Serializable;
import java.util.ArrayList;

import android.net.Uri;



public class Contacto implements Serializable {

	
	private String id;
	
	private String name;
	
	private ArrayList<String> telephoneNumbers;
	
	private ArrayList<String> emails;
	
	private Uri Imagen;
	
	private String nomUserWeb;
	
	private String nomUserBluetooth;
	
	private String MACBluetooth;
		

	public Contacto() {
		telephoneNumbers = new ArrayList<String>(0);
		emails = new ArrayList<String>(0);
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public void setImagen(Uri Imagen) {
		this.Imagen = Imagen;
	}

	public void setUserWeb(String userWeb) {
		this.nomUserWeb = userWeb;
	}
	
	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}
	
	public String getuserWeb() {
		return nomUserWeb;
	}
	
	public Uri getImagen(){
		return Imagen;
	}

	public void addTelephoneNumber(String number) {
		telephoneNumbers.add(number);
	}

	public ArrayList<String> getTelephoneNumbers() {
		return telephoneNumbers;
	}

	public void addEmail(String email) {
		emails.add(email);
	}

	public ArrayList<String> getEmails() {
		return emails;
	}
	
	public String getNomUserBluetooth() {
		return nomUserBluetooth;
	}

	public void setNomUserBluetooth(String nomUserBluetooth) {
		this.nomUserBluetooth = nomUserBluetooth;
	}

	public String getMACBluetooth() {
		return MACBluetooth;
	}

	public void setMACBluetooth(String mACBluetooth) {
		MACBluetooth = mACBluetooth;
	}

	@Override
	public String toString() {
		return id + " " + name;
	}
}




















