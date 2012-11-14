package com.tdam_2012_g1.messagesenderclient;

import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpResponse;

import com.tdam_2012_g1.entidades.MensajeWeb;
import com.tdam_2012_g1.entidades.Usuario;

public class EnviarMensajeHandler {

	private static final String SERVER_URL = "http://192.168.1.201:8080/MessageSender/";
	private boolean success = true;
	private String mensaje;
	private String request;
	private String resultado;

	public EnviarMensajeHandler() {

	}

	public String getMensaje() {
		return mensaje;
	}

	public boolean isSuccess() {
		return success;
	}

	public String getResultado() {
		return resultado;
	}

	public String enviarMensaje(MensajeWeb msj, Usuario usr) {

		request = "<action id=\"REQUEST_RANDOM_VALUE\" name=\"send-message\"><action-detail>"
				+ "<auth username=\""
				+ usr.get_nombre()
				+ "\" key=\""
				+ usr.get_contraseña()
				+ "\"></auth><message to=\""
				+ msj.get_destinatario()
				+ "\"><![CDATA["
				+ msj.get_detalle()
				+ "]]></message>" + "</action-detail></action>";

		enviarRequest();
		return resultado;

	}

	public void enviarRequest() {
		try {
			HttpEntity hr = postRequest(request);
			responseSendMessage(hr.getContent());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String[] responseSendMessage(InputStream response) {
		SAXParserFactory factory = SAXParserFactory.newInstance();

		try {
			SAXParser parser = factory.newSAXParser();
			XmlResultHandler handler = new XmlResultHandler();
			parser.parse(response, handler);
			resultado = handler.getResultInfo();
			return new String[] { String.valueOf(handler.getResult()),
					handler.getResultInfo() };
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static HttpEntity postRequest(String xml)
			throws ClientProtocolException, IOException {
		HttpPost httppost = new HttpPost(SERVER_URL);
		StringEntity se = new StringEntity(xml);

		se.setContentType("text/xml");

		httppost.setEntity(se);

		HttpClient httpclient = new DefaultHttpClient();
		BasicHttpResponse httpResponse = (BasicHttpResponse) httpclient
				.execute(httppost);
		return httpResponse.getEntity();

	}

}
