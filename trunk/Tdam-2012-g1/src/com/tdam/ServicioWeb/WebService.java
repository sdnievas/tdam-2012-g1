package com.tdam.ServicioWeb;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;


public class WebService {

	private String username;
	private String password;
	private URL url;

	private final static String URL = "http://192.168.0.101:8080/MessageSender/";

	public static final int FATAL_ERROR = -1;


	public WebService(String username, String password) {
		this.username = username;
		this.password = password;
		try {
			url = new URL(URL);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public WebServiceInfo registerUser() {

		String REGISTER_REQUEST = "<action id=\"REQUEST_RANDOM_VALUE\" name=\"register-user\">"
				+ "<action-detail><user username=\""
				+ username
				+ "\" password=\"" + password + "\"/></action-detail></action>";

		WebServiceHandler handler = new WebServiceHandler();
		return getInfo(REGISTER_REQUEST, handler);
	}

	public WebServiceInfo sendMessage(com.tdam.Class.MensajeWeb msgw) {

		String SEND_REQUEST = "<action id=\"REQUEST_RANDOM_VALUE\" name=\"send-message\"><action-detail>"
				+ "<auth username=\""
				+ username
				+ "\" key=\""
				+ password
				+ "\"></auth><message to=\""
				+ msgw.get_contacto()
				+ "\"><![CDATA["
				+ msgw.get_detalle()
				+ "]]></message>" + "</action-detail></action>";

		WebServiceHandler handler = new WebServiceHandler();
		return getInfo(SEND_REQUEST, handler);
	}


	public ArrayList<ReceivedMessageInfo> getMessages(String timestamp) {

		String GET_REQUEST = "<action id=\"REQUEST_RANDOM_VALUE\" name=\"get-messages\">"
				+ "<action-detail><auth username=\""
				+ username
				+ "\" key=\""
				+ password
				+ "\"></auth>"
				+ "<filter type=\"timestamp\">"
				+ timestamp + "</filter>" + "</action-detail></action>";

		WebServiceReceivedMessageHandler handler = new WebServiceReceivedMessageHandler();
		return getReceivedMessages(GET_REQUEST, handler);
	}

	private WebServiceHandler sendRequest(String request,
			WebServiceHandler handler) {
		try {
			URLConnection c = url.openConnection();

			c.setDoOutput(true);
			if (c instanceof HttpURLConnection) {
				((HttpURLConnection) c).setRequestMethod("POST");
			}

			OutputStreamWriter out = new OutputStreamWriter(c.getOutputStream());

			out.write(request);

			out.close();

			BufferedReader in = new BufferedReader(new InputStreamReader(
					c.getInputStream()));

			String s = null;
			StringBuffer aux = new StringBuffer();

			while ((s = in.readLine()) != null) {
				aux.append(s);
			}

			SAXParser parser = XmlParserProvider.getParser();
			parser.parse(new ByteArrayInputStream(aux.toString().getBytes()),
					handler);
			XmlParserProvider.releaseParser(parser);

			in.close();

			return handler;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private WebServiceInfo getInfo(String request, WebServiceHandler handler) {
		WebServiceInfo info;
		sendRequest(request, handler);
		info = handler.getInfo();
		if (info == null) {
			info = new WebServiceInfo();
			info.setCode(WebServiceInfo.FATAL_ERROR);
			info.setType(WebServiceInfo.FATAL_ERROR_MESSAGE);

		}
		return info;
	}

	private ArrayList<ReceivedMessageInfo> getReceivedMessages(String request,
			WebServiceReceivedMessageHandler handler) {
		sendRequest(request, handler);
		return handler.getReceivedMessages();
	}

	public void setUser(String username, String password) {
		this.username = username;
		this.password = password;
	}
}
