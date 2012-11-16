package com.tdam_2012_g1.mensajesWeb;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class XmlParserProvider {

  private static SAXParserFactory factory = SAXParserFactory.newInstance();

  public static SAXParser getParser() throws SAXException {
    try {
      return factory.newSAXParser();
    } catch (ParserConfigurationException e) {
      throw new SAXException(e);
    }
  }

  public static void releaseParser(SAXParser parser) {

  }
}
