/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.server.dal.dao;


import com.imagine.chattingapp.common.entity.User;
import com.imagine.chattingapp.server.control.MainController;
import com.imagine.chattingapp.server.dal.entity.Country;
import com.imagine.chattingapp.server.dal.entity.UserStatus;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

/**
 *
 * @author Mahmoud Shereif
 */
public class UserDAO extends DatabaseDataRetreival implements DAO<User>{
    DatabaseDataRetreival databaseDataRetreival = null;
    
    public UserDAO() throws SQLException {
        databaseDataRetreival = new DatabaseDataRetreival();
    }

    com.imagine.chattingapp.server.dal.entity.User mapToAnnoUser(User user)
    {
        com.imagine.chattingapp.server.dal.entity.User userAnno = new com.imagine.chattingapp.server.dal.entity.User();
        userAnno.setPhoneNumber(user.getPhoneNumber());
        userAnno.setName(user.getName());
        userAnno.setEmail(user.getPhoneNumber());
        userAnno.setPicture(user.getPicture());
        userAnno.setPassword(user.getPassword());
        userAnno.setGender(user.getGender()? (byte)1 : (byte)0);
        userAnno.setDateOfBirth(new Date(user.getDateOfBirth()));
        userAnno.setBiography(user.getBiography());
        userAnno.setCountry(MainController.session.get(Country.class, user.getCountryID()));
        userAnno.setUserStatus(MainController.session.get(UserStatus.class, user.getStatusID()));
            
        return userAnno;
    }
    
    
    public User mapToUser(com.imagine.chattingapp.server.dal.entity.User userAnno){
        User user = new User();
        user.setPhoneNumber(userAnno.getPhoneNumber());
        user.setName(userAnno.getName());
        user.setEmail(userAnno.getEmail());
        user.setPicture(userAnno.getPicture());
        user.setPassword(userAnno.getPassword());
        user.setGender((userAnno.getGender() != 0));
        user.setDateOfBirth(userAnno.getDateOfBirth().getTime());
        user.setBiography(userAnno.getBiography());
        user.setCountryID(userAnno.getCountry().getId());
        user.setStatusID(userAnno.getUserStatus().getId());
        return user;
    }
    
    
    @Override
    public void persist(User user) throws SQLException {
        MainController.session.beginTransaction();
        MainController.session.persist(mapToAnnoUser(user));
        MainController.session.getTransaction().commit();
    }

    @Override
    public void update(User user) throws SQLException {
        com.imagine.chattingapp.server.dal.entity.User userAnno = mapToAnnoUser(user);
        MainController.session.beginTransaction();
        MainController.session.merge(userAnno);
        MainController.session.getTransaction().commit();
    }

    @Override
    public void delete(List<Object> primaryKey) throws SQLException {
        String key = (String)primaryKey.get(0);
        com.imagine.chattingapp.server.dal.entity.User userAnno = MainController.session.get(com.imagine.chattingapp.server.dal.entity.User.class, key);
        MainController.session.beginTransaction();
        MainController.session.delete(userAnno);
        MainController.session.getTransaction().commit();
    }

    @Override
    public User getByPrimaryKey(List<Object> primaryKeys) throws SQLException {
        String key = (String)primaryKeys.get(0);
        com.imagine.chattingapp.server.dal.entity.User userAnno = MainController.session.get(com.imagine.chattingapp.server.dal.entity.User.class, key);
        User user = mapToUser(userAnno);
        return user;
    }

    @Override
    public List<User> getAll() throws SQLException {
        MainController.session.beginTransaction();
        Query usersQuery = MainController.session.createQuery("from com.imagine.chattingapp.server.dal.entity.User");
        List<com.imagine.chattingapp.server.dal.entity.User> allAnnoUsers = usersQuery.list();
        List<User> returnedUsers = new ArrayList();
        allAnnoUsers.forEach((userAnno)->{
            returnedUsers.add(mapToUser(userAnno));
        });
        MainController.session.getTransaction().commit();
        return returnedUsers;
    }

    @Override
    public List<User> getByColumnNames(List<String> columnNames, List<Object> columnValues) throws SQLException {
                
        MainController.session.beginTransaction();
        Criteria userCriteria = MainController.session.createCriteria(com.imagine.chattingapp.server.dal.entity.User.class);
        columnNames.set(0, "phoneNumber");
        columnNames.set(1, "password");
        for(int i=0;i<columnNames.size();i++){
            userCriteria = userCriteria.add(Restrictions.eq(columnNames.get(i), columnValues.get(i)));
        }
        
        List<com.imagine.chattingapp.server.dal.entity.User> allAnnoUsers = userCriteria.list();
        List<User> returnedUsers = new ArrayList<>();
        allAnnoUsers.forEach((usr)->{
            returnedUsers.add(mapToUser(usr));
        });
        MainController.session.getTransaction().commit();
        return returnedUsers;
    }
    
}
