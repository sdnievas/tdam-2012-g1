package com.tdam_2012_g1.mensajesWeb;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import android.util.Log;

public class WebServiceReceivedMessageHandler extends WebServiceHandler{

	private ArrayList<ReceivedMessageInfo> receivedMessages;
	private ReceivedMessageInfo receivedMessage;
	private static final String MESSAGE_TAG = "message";
	private static final String TIMESTAMP_ATTRIBUTE = "timestamp";
	private static final String FROM_ATTRIBUTE = "from";

	public ReceivedMessageInfo getInfo() {
		return (ReceivedMessageInfo) super.info;
	}

	public ArrayList<ReceivedMessageInfo> getReceivedMessages() {
		return receivedMessages;
	}

	public WebServiceReceivedMessageHandler() {
		super();
		info = new ReceivedMessageInfo();
		receivedMessages = new ArrayList<ReceivedMessageInfo>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		if (localName == null) {
			localName = qName;
		}

		Log.i("TESTTYPE_LOCALNAME", localName);
		if (RESULT_TAG.equals(localName)) {
			info.setType(attributes.getValue(attributes
					.getIndex(TYPE_RESULT_ATTRIBUTE)));
			if (info.getType().equals(WebServiceInfo.ERROR_MESSAGE))
				info.setCode(WebServiceInfo.ERROR);
			else
				info.setCode(WebServiceInfo.SUCCESS);

			Log.i("TESTTYPE_ATTRIBUTES_TYPE", info.getType());
		} else if (DETAIL_TAG.equals(localName)) {
			info.setDescription(attributes.getValue(attributes
					.getIndex(DESCRIPTION_DETAIL_ATTRIBUTE)));
		}

		if (USER_TAG.equals(localName)) {
			info.setCode(WebServiceInfo.SUCCESS);
			info.setDescription(WebServiceInfo.SUCCESS_MESSAGE);
		}
		if (MESSAGE_TAG.equals(localName)){
			ReceivedMessageInfo receivedMessage = new ReceivedMessageInfo(); 
			receivedMessage.setCode(super.info.getCode());
			receivedMessage.setType(super.info.getType());
			receivedMessage.setTimestamp(attributes.getValue(attributes.getIndex(TIMESTAMP_ATTRIBUTE)));
			receivedMessage.setFrom(attributes.getValue(attributes.getIndex(FROM_ATTRIBUTE)));
			receivedMessages.add(receivedMessage);
			this.receivedMessage = receivedMessage;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		super.characters(ch, start, length);
		String cdata = new String(ch, start, length);
		receivedMessage.setMessage(cdata);
	}
}
