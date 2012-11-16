package com.tdam_2012_g1.mensajesWeb;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

public class WebServiceHandler extends DefaultHandler {

	protected WebServiceInfo info;
	protected static final String RESULT_TAG = "result";
	protected static final String DETAIL_TAG = "detail";
	protected static final String USER_TAG = "user";
	protected static final String TYPE_RESULT_ATTRIBUTE = "type";
	protected static final String DESCRIPTION_DETAIL_ATTRIBUTE = "description";
	protected static final String CODE_DETAIL_ATTRIBUTE = "code";

	public WebServiceInfo getInfo() {
		return info;
	}

	public WebServiceHandler() {
		info = new WebServiceInfo();
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
			info.setCode(Integer.parseInt(attributes.getValue(attributes
					.getIndex(CODE_DETAIL_ATTRIBUTE))));
			info.setDescription(attributes.getValue(attributes
					.getIndex(DESCRIPTION_DETAIL_ATTRIBUTE)));
		}

		if (USER_TAG.equals(localName)) {
			info.setCode(WebServiceInfo.SUCCESS);
			info.setDescription(WebServiceInfo.SUCCESS_MESSAGE);
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		super.characters(ch, start, length);
	}
}
