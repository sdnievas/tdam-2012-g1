package com.tdam_2012_g1.messagesenderclient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.tdam_2012_g1.entidades.Usuario;

public class RegistrarUsuarioHandler extends DefaultHandler {

	private boolean success = true;
	private String mensaje;
	private String request;

	public RegistrarUsuarioHandler() {
		super();
	}

	public String getMensaje() {
		return mensaje;
	}

	public boolean isSuccess() {
		return success;
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		super.startElement(uri, localName, qName, attributes);
		if (localName.equals("result")
				&& attributes.getValue("type").equals("error"))
			success = false;
		if (localName.equals("detail"))
			mensaje = attributes.getValue("description");

	}

	public void registrarUsuario(Usuario usr) {

		String SEND_REQUEST = "<action id=\"REQUEST_RANDOM_VALUE\" name=\"register-user\"><action-detail>"
				+ "<user username=\""
				+ usr.get_nombre()
				+ "\" password=\""
				+ usr.get_contraseña() + "\"/></action-detail></action>";

		request = SEND_REQUEST;
		enviarRequest();

	}

	public void enviarRequest() {
		try {

			URL u = new URL("http://192.168.0.104:8080/MessageSender/");
			URLConnection c = u.openConnection();

			c.setDoOutput(true);
			if (c instanceof HttpURLConnection) {
				((HttpURLConnection) c).setRequestMethod("POST");
			}

			OutputStreamWriter out = new OutputStreamWriter(c.getOutputStream());

			// sendFileData(out, request);

			out.write(request);
			out.close();

			BufferedReader in = new BufferedReader(new InputStreamReader(
					c.getInputStream()));

			String s = null;
			while ((s = in.readLine()) != null) {
				System.out.println(s);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}