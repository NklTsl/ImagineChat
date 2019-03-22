/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.server.dal.dao;


import com.imagine.chattingapp.common.entity.User_Status;
import com.imagine.chattingapp.server.control.MainController;
import com.imagine.chattingapp.server.dal.entity.UserStatus;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Mahmoud Shereif
 */
public class User_StatusDAO implements DAO<User_Status> {
    DatabaseDataRetreival databaseDataRetreival = null;
    
    public User_StatusDAO() throws SQLException{
        databaseDataRetreival = new DatabaseDataRetreival();
    }

    @Override
    public void persist(User_Status userStatus) throws SQLException {
        MainController.session.beginTransaction();
        MainController.session.persist(mapToAnnoUserStatus(userStatus));
        MainController.session.getTransaction().commit();
    }

    @Override
    public void update(User_Status userStatus) throws SQLException {
        MainController.session.beginTransaction();
        MainController.session.saveOrUpdate(mapToAnnoUserStatus(userStatus));
        MainController.session.getTransaction().commit();
    }

    @Override
    public void delete(List<Object> primaryKey) throws SQLException {
        byte key = (byte) primaryKey.get(0);
        com.imagine.chattingapp.server.dal.entity.UserStatus userStatusAnno = MainController.session.get(com.imagine.chattingapp.server.dal.entity.UserStatus.class, key);
        MainController.session.beginTransaction();
        MainController.session.delete(userStatusAnno);
        MainController.session.getTransaction().commit();
    }

    @Override
    public User_Status getByPrimaryKey(List<Object> primaryKeys) throws SQLException {
        byte key = (byte)primaryKeys.get(0);
        com.imagine.chattingapp.server.dal.entity.UserStatus userStatus = MainController.session.get(com.imagine.chattingapp.server.dal.entity.UserStatus.class, key);
        User_Status user_Status = mapToUser_Status(userStatus);
        return user_Status;
    }

    @Override
    public List<User_Status> getAll() throws SQLException {
        
        Criteria criteria = MainController.session.createCriteria(UserStatus.class);
        List<com.imagine.chattingapp.server.dal.entity.UserStatus> allAnnoStatus = criteria.list();
        List<User_Status> returnedStatus = new ArrayList();
        allAnnoStatus.forEach((statusAnno)->{
            returnedStatus.add(mapToUserStatus(statusAnno));
        });
        return returnedStatus;
        
    }

    private User_Status mapToUserStatus(com.imagine.chattingapp.server.dal.entity.UserStatus statusAnno) {
        User_Status status = new User_Status();
        status.setId(statusAnno.getId());
        status.setDescription(statusAnno.getDescription());
        return status;
    }

    @Override
    public List<User_Status> getByColumnNames(List<String> columnNames, List<Object> columnValues) throws SQLException {
        Criteria userStatusCriteria = MainController.session.createCriteria(com.imagine.chattingapp.server.dal.entity.UserStatus.class);
        for(int i=0;i<columnNames.size();i++){
            userStatusCriteria.add(Restrictions.eq(columnNames.get(i), columnValues.get(i)));
        }
        List<com.imagine.chattingapp.server.dal.entity.UserStatus> allUserStatusesAnno = userStatusCriteria.list();
        List<User_Status> userStatuses = new ArrayList<>();
        allUserStatusesAnno.forEach((userStatusAnno)->{
            userStatuses.add(mapToUserStatus(userStatusAnno));
        });
        return userStatuses;
    }

    private User_Status mapToUser_Status(UserStatus userStatus) {
        User_Status status = new User_Status();
        status.setId(userStatus.getId());
        status.setDescription(userStatus.getDescription());
        return status;
    }
    
    com.imagine.chattingapp.server.dal.entity.UserStatus mapToAnnoUserStatus(User_Status userStatus){
        com.imagine.chattingapp.server.dal.entity.UserStatus userStatusAnno = new com.imagine.chattingapp.server.dal.entity.UserStatus();
        userStatusAnno.setId(userStatus.getId());
        userStatusAnno.setDescription(userStatus.getDescription());
        return userStatusAnno;
    }
}
