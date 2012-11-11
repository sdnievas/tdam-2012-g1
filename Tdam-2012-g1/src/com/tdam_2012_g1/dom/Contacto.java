package com.tdam_2012_g1.dom;

import java.io.Serializable;
import java.util.ArrayList;



public class Contacto  implements Serializable {

	private String name;
	private String id;
	private ArrayList<String> telephoneNumbers;
	private ArrayList<String> emails;

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

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
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

	@Override
	public String toString() {
		return id + " " + name;
	}
}




















