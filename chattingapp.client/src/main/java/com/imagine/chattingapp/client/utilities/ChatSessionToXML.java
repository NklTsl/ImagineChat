/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.client.utilities;

import com.imagine.chattingapp.client.control.ServiceLocator.ServiceLocator;
import com.imagine.chattingapp.client.jaxb.ChatSessionType;
import com.imagine.chattingapp.client.jaxb.MsgType;
import com.imagine.chattingapp.client.jaxb.ObjectFactory;
import com.imagine.chattingapp.common.dto.ChatSession;
import com.imagine.chattingapp.common.dto.GroupMessage;
import com.imagine.chattingapp.common.dto.Message;
import com.imagine.chattingapp.common.dto.OneToOneMessage;
import com.imagine.chattingapp.common.serverservices.ContactsService;
import com.imagine.chattingapp.common.serverservices.GetNameByPhoneService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import sun.misc.IOUtils;

/**
 *
 * @author Mahmoud Shereif
 */
public class ChatSessionToXML {

    public ChatSessionToXML() {

    }

    public static void ChatSessionToXML(ChatSession chatSession, String userName, File file) {
        try {
            GetNameByPhoneService getNameByPhoneService = (GetNameByPhoneService) ServiceLocator.getService("GetNameByPhoneService");
            ObjectFactory objectFactory = new ObjectFactory();
            ChatSessionType chatSessionType = objectFactory.createChatSessionType();
            chatSessionType.setFrom(userName);
            chatSessionType.setTo(chatSession.getName());

            Date dateNow = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            XMLGregorianCalendar calendarDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(dateFormat.format(dateNow));
            chatSessionType.setDateTaken(calendarDate);

            dateFormat = new SimpleDateFormat("HH:mm:ss");
            XMLGregorianCalendar calendarTime = DatatypeFactory.newInstance().newXMLGregorianCalendar(dateFormat.format(dateNow));
            chatSessionType.setTimeTaken(calendarTime);

            List<MsgType> messagesList = new ArrayList<>();

            for (Message message : chatSession.getMessagesList()) {
                if (message instanceof OneToOneMessage) {
                    OneToOneMessage oneToOneMessage = (OneToOneMessage) message;

                    MsgType msgType = objectFactory.createMsgType();
                    msgType.setDate(calendarDate);
                    msgType.setTime(calendarTime);
                    msgType.setFrom(getNameByPhoneService.getByPhone(oneToOneMessage.getSenderPhone()));
                    msgType.setBody(oneToOneMessage.getMessage());
                    chatSessionType.getMsg().add(msgType);
                } else {
                    GroupMessage groupMessage = (GroupMessage) message;

                    MsgType msgType = objectFactory.createMsgType();
                    msgType.setDate(calendarDate);
                    msgType.setTime(calendarTime);
                    msgType.setFrom(getNameByPhoneService.getByPhone(groupMessage.getSenderUser().getPhoneNumber()));
                    msgType.setBody(groupMessage.getMessage());
                    chatSessionType.getMsg().add(msgType);
                }
            }

            JAXBContext jAXBContext = JAXBContext.newInstance("com.imagine.chattingapp.client.jaxb");

            Marshaller marsh = jAXBContext.createMarshaller();
            marsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marsh.marshal(objectFactory.createChatSession(chatSessionType), new FileOutputStream(file));
            Path path = Paths.get(file.getPath());
            Charset charset = StandardCharsets.UTF_8;

            int pos = file.getName().lastIndexOf(".");
            String justName = pos > 0 ? file.getName().substring(0, pos) : file.getName();
            
            
            String content = new String(Files.readAllBytes(path), charset);
            content = content.replaceAll("xmlns=\"http://www.MahmoudShereif.com\"", "");
            content = content.replaceAll("\\?>", "?>\n"
                    + "<?xml-stylesheet type = \"text/xsl\" href = \"" + justName + ".xsl\"?>");
            Files.write(path, content.getBytes(charset));

            String xslFileContent = "<?xml version = \"1.0\" encoding = \"UTF-8\"?>\n"
                    + "<xsl:stylesheet version = \"1.0\" \n"
                    + "xmlns:xsl = \"http://www.w3.org/1999/XSL/Transform\">   \n"
                    + "   <xsl:template match = \"/\"> \n"
                    + "   <style>\n"
                    + "\n"
                    + "td, th {\n"
                    + "  border: 1px solid #ddd;\n"
                    + "  padding: 8px;\n"
                    + "}\n"
                    + "\n"
                    + "tr:nth-child(even){background-color: #f2f2f2;}\n"
                    + "\n"
                    + "tr:hover {background-color: #ddd;}\n"
                    + "\n"
                    + "th {\n"
                    + "  padding-top: 12px;\n"
                    + "  padding-bottom: 12px;\n"
                    + "  text-align: left;\n"
                    + "  background-color: #4CAF50;\n"
                    + "  color: white;\n"
                    + "}\n"
                    + "</style>\n"
                    + "      <html> \n"
                    + "         <body> \n"
                    + "		 \n"
                    + "			\n"
                    + "            <h2>messages from <xsl:value-of select = \"ChatSession/@from\"/> to <xsl:value-of select = \"ChatSession/@to\"/> </h2>\n"
                    + "			\n"
                    + "			\n"
                    + "            <table border = \"1\"> \n"
                    + "               <tr bgcolor = \"#9acd32\"> \n"
                    + "                  <th>from</th>  \n"
                    + "                  <th>date</th> \n"
                    + "                  <th>time</th> \n"
                    + "                  <th>message</th> \n"
                    + "               </tr> \n"
                    + "				\n"
                    + "				\n"
                    + "               <xsl:for-each select=\"ChatSession/Msg\"> \n"
                    + "                  <tr> \n"
                    + "					 <td><xsl:value-of select = \"@from\"/></td> \n"
                    + "                     <td><xsl:value-of select = \"Date\"/></td> \n"
                    + "                     <td><xsl:value-of select = \"Time\"/></td> \n"
                    + "                     <td><xsl:value-of select = \"body\" disable-output-escaping=\"yes\"/></td> \n"
                    + "						\n"
                    + "                  </tr> \n"
                    + "               </xsl:for-each> \n"
                    + "            </table> \n"
                    + "         </body> \n"
                    + "      </html> \n"
                    + "   </xsl:template>  \n"
                    + "</xsl:stylesheet>";
            
            path = Paths.get(file.getParent() + "\\" + justName + ".xsl");
            Files.write(path, xslFileContent.getBytes(charset));
        } catch (DatatypeConfigurationException ex) {
            Logger.getLogger(ChatSessionToXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ChatSessionToXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ChatSessionToXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JAXBException ex) {
            Logger.getLogger(ChatSessionToXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ChatSessionToXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ChatSessionToXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
