//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.02.25 at 04:43:06 PM EET 
//


package com.imagine.chattingapp.client.jaxb;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the utilities package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Msg_QNAME = new QName("http://www.MahmoudShereif.com", "Msg");
    private final static QName _ChatSession_QNAME = new QName("http://www.MahmoudShereif.com", "ChatSession");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: utilities
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link MsgType }
     * 
     */
    public MsgType createMsgType() {
        return new MsgType();
    }

    /**
     * Create an instance of {@link ChatSessionType }
     * 
     */
    public ChatSessionType createChatSessionType() {
        return new ChatSessionType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MsgType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.MahmoudShereif.com", name = "Msg")
    public JAXBElement<MsgType> createMsg(MsgType value) {
        return new JAXBElement<MsgType>(_Msg_QNAME, MsgType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ChatSessionType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.MahmoudShereif.com", name = "ChatSession")
    public JAXBElement<ChatSessionType> createChatSession(ChatSessionType value) {
        return new JAXBElement<ChatSessionType>(_ChatSession_QNAME, ChatSessionType.class, null, value);
    }

}