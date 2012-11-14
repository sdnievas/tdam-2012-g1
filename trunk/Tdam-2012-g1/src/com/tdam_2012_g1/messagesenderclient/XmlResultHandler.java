package com.tdam_2012_g1.messagesenderclient;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlResultHandler extends DefaultHandler{
    
    public static final int resultIncorrect = 1;
    public static final int resultCorrect = 0;
    public static final int resultUsuarioExistente = 5;
    public static final int resultUsuarioNoRegistrado = 11;
    
    String resultInfo;
    int result;
    
    public String getResultInfo()
    {
            return resultInfo;
    }
    
    public int getResult()
    {
            return result;
    }
    
    @Override
    public void startElement(String uri, String localName, String qName,
                    Attributes attributes) throws SAXException {
            // TODO Auto-generated method stub
            
            super.startElement(uri, localName, qName, attributes);
            if(localName.equals("result"))
            {
                    if(attributes.getValue("type").equals("success"))
                    {
                            result = resultCorrect;
                            resultInfo = "success";
                    }
                    if(attributes.getValue("type").equals("error"))
                    {
                            result = resultIncorrect;
                            resultInfo = "Ha ocurrido un error.";
                    }
            }
            if(localName.equals("detail"))
            {
                    if(attributes.getValue("code").compareTo(String.valueOf(resultUsuarioExistente))==0)
                    {
                            result = resultUsuarioExistente;
                            resultInfo = attributes.getValue("description");
                    }
                    if(attributes.getValue("code").compareTo(String.valueOf(resultUsuarioNoRegistrado))==0)
                    {
                            result = resultUsuarioNoRegistrado;
                            resultInfo = attributes.getValue("description");
                            resultInfo += " - Usuario no registrado";
                    }
                    else
                    {
                            resultInfo = attributes.getValue("description");
                    }
            }
    }
}
