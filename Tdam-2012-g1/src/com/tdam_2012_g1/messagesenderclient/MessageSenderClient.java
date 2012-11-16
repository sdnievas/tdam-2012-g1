package com.tdam_2012_g1.messagesenderclient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import com.tdam_2012_g1.entidades.MensajeWeb;
import com.tdam_2012_g1.entidades.Usuario;

public class MessageSenderClient {

	public static void enviarMensajeWeb(String REQUEST) {

		try {
			URL u = new URL("http://10.16.0.36:8080/MessageSender/");
			URLConnection c = u.openConnection();

			c.setDoOutput(true);
			if (c instanceof HttpURLConnection) {
				((HttpURLConnection) c).setRequestMethod("POST");
			}

			OutputStreamWriter out = new OutputStreamWriter(c.getOutputStream());

			sendFileData(out, REQUEST);

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

	private static void sendFileData(OutputStreamWriter out, String path) {
		try {
			System.out.println((new File(path)).exists());
			BufferedReader in = new BufferedReader(new FileReader(path));
			String str;
			while ((str = in.readLine()) != null) {
				out.write(str);
				System.out.println(str);
			}
			in.close();
		} catch (IOException e) {
		}
	}

	public static String enviarMensaje(MensajeWeb msj, Usuario usr) {

		String SEND_REQUEST = "<action id=\"REQUEST_RANDOM_VALUE\" name=\"send-message\"><action-detail>"
				+ "<auth username=\""
				+ usr.get_nombre()
				+ "\" key=\""
				+ usr.get_contraseña()
				+ "\"></auth><message to=\""
				+ msj.get_destinatario()
				+ "\"><![CDATA["
				+ msj.get_detalle()
				+ "]]></message>" + "</action-detail></action>";

		return SEND_REQUEST;

	}

}
