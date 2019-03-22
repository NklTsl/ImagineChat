/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.imagine.chattingapp.server.dal.dao;

import com.imagine.chattingapp.common.entity.Friend;
import com.imagine.chattingapp.server.control.MainController;
import static com.imagine.chattingapp.server.control.MainController.session;
import com.imagine.chattingapp.server.dal.entity.ChatGroup;
import com.imagine.chattingapp.server.dal.entity.User;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Mahmoud Shereif
 */
public class TestDaos {

    public TestDaos() throws SQLException {
    }
    
    public static void main(String[] args){
        
        try {
//            Admin admin = new Admin();
//            admin.setPhoneNumber("01141744785");
//            admin.setName("Ahmed");
//            admin.setEmail("mnet30@yahoo.com");
//            admin.setPicture(new byte[5]);
//            admin.setPassword("Mahmoud");
//            admin.setDateOfBirth(new Date(1990, 1, 1));
//            admin.setGender(true);
//            admin.setBiography("ITI");
//            admin.setCountryID((byte)1);
//            
//            AdminDAO adminDAO = new AdminDAO();
//            List<Object> list = new ArrayList();
//            list.add(admin.getPhoneNumber());
//            List<Admin> listadmin = adminDAO.getAll();
//            
//            listadmin.contains(list);
            
            
            
//            User user = new User();
//            user.setPhoneNumber("1");
//            user.setName("Mahmoud");
//            user.setEmail("mnet30@yahoo.com");
//            user.setPicture(new byte[5]);
//            user.setPassword("Mahmoud");
//            user.setDateOfBirth(new Date(1990, 1, 1));
//            user.setGender(true);
//            user.setBiography("ITI");
//            user.setCountryID((byte)1);
//            user.setStatusID((byte)1);
//            
//            
//            User user1 = new User();
//            user1.setPhoneNumber("2");
//            user1.setName("Ahmed");
//            user1.setEmail("mnet300@yahoo.com");
//            user1.setPicture(new byte[5]);
//            user1.setPassword("Mahmoud");
//            user1.setDateOfBirth(new Date(1990, 1, 1));
//            user1.setGender(true);
//            user1.setBiography("ITI");
//            user1.setCountryID((byte)1);
//            user1.setStatusID((byte)1);
//            
//            UserDAO userDAO = new UserDAO();
//            userDAO.persist(user);
//            
//            user.setBiography("updated");
//            
//            userDAO.update(user);
//            
//            userDAO.persist(user1);
//            
//            
//            List<Object> list = new ArrayList();
//            list.add(user1.getPhoneNumber());
//            
//            User userGet = userDAO.getByPrimaryKey(list);
//            
//            userDAO.delete(list);
//            
//            List<User> listuser = userDAO.getAll();
//            
//            listuser.contains(list);
            
            
            /*Friend friend = new Friend();
            friend.setPhoneNumber1("1");
            friend.setPhoneNumber2("2");
            friend.setRealtiveGroup("other");
            friend.setBlockFlag(false);
            friend.setLastMessageSentTime(new Timestamp(1990, 1, 2, 2, 1, 2, 0));
            friend.setFontFamliy("font");
            friend.setFontSize((short)500);
            friend.setFontColor(50);
            friend.setBoldFlag(false);
            friend.setUnderlineFlag(false);
            friend.setItalicFlag(false);
            friend.setTextBackground(new byte[10]);
            
            
            Friend friend2 = new Friend();
            friend2.setPhoneNumber1("2");
            friend2.setPhoneNumber2("1");
            friend2.setRealtiveGroup("other");
            friend2.setBlockFlag(false);
            friend2.setLastMessageSentTime(new Timestamp(1990, 1, 2, 2, 1, 2, 0));
            friend2.setFontFamliy("font");
            friend2.setFontSize((short)500);
            friend2.setFontColor(50);
            friend2.setBoldFlag(false);
            friend2.setUnderlineFlag(false);
            friend2.setItalicFlag(false);
            friend2.setTextBackground(new byte[10]);
            
            FriendDAO friendDAO = new FriendDAO();
            friendDAO.persist(friend);
            
            friend.setFontFamliy("updated");
            
            friendDAO.update(friend);
            
            friendDAO.persist(friend2);
            
            
            List<Object> list = new ArrayList();
            list.add(friend2.getPhoneNumber1());
            list.add(friend2.getPhoneNumber2());
            
            Friend friendGet = friendDAO.getByPrimaryKey(list);
            
            friendDAO.delete(list);
            
            List<Friend> listFriend = friendDAO.getAll();
            
            listFriend.contains(list);*/
            /*SessionFactory sessionFactory = new Configuration().configure(new TestDaos().getClass().getResource("/cfg/hibernate.cfg.xml")).buildSessionFactory();
            Session session = sessionFactory.openSession();
            
            
            ChatGroup chatGroup = session.get(ChatGroup.class, 23);
        User user = session.get(User.class, "01111111111");
        System.out.println();*/
            
        } catch (Exception ex) {
            Logger.getLogger(TestDaos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
