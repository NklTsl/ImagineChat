/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.server.dal.dao;

import com.imagine.chattingapp.server.dal.entity.Admin;
import com.imagine.chattingapp.server.dal.entity.Entity;
import com.imagine.chattingapp.server.dal.entity.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mahmoud Shereif
 */
public class UserDAO extends DatabaseDataRetreival implements DAO<User>{
    DatabaseDataRetreival databaseDataRetreival = null;
    
    public UserDAO() throws SQLException {
        databaseDataRetreival = new DatabaseDataRetreival();
    }

    @Override
    public void persist(User user) throws SQLException {
        String persistQuery = "INSERT INTO `chattingapp`.`user` "
                + "(`Phone_Number`, `Name`, `Email`, `Password`, `Gender`, `Date_Of_Birth`, `Biography`, `Country_ID`, `Status_ID`) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        List<Object> parameterList = new ArrayList<>();
        parameterList.add(user.getPhoneNumber());
        parameterList.add(user.getName());
        parameterList.add(user.getEmail());
        parameterList.add(user.getPassword());
        parameterList.add(user.getGender());
        parameterList.add(user.getDateOfBirth());
        parameterList.add(user.getBiography());
        parameterList.add(user.getCountryID());
        parameterList.add(user.getStatusID());
        
        databaseDataRetreival.executeUpdateQuery(persistQuery, parameterList);
    }

    @Override
    public void update(User user) throws SQLException {
        String updateQuery = "UPDATE `chattingapp`.`user` "
                + "SET `Name` = ?, `Email` = ?, `Password` = ?, "
                + "`Gender` = ?, `Date_Of_Birth` = ?, `Biography` = ?, "
                + "`Country_ID` = ?, `Status_ID` = ? WHERE (`Phone_Number` = ?)";
        
        List<Object> parameterList = new ArrayList<>();
        parameterList.add(user.getName());
        parameterList.add(user.getEmail());
        parameterList.add(user.getPassword());
        parameterList.add(user.getGender());
        parameterList.add(user.getDateOfBirth());
        parameterList.add(user.getBiography());
        parameterList.add(user.getCountryID());
        parameterList.add(user.getStatusID());
        parameterList.add(user.getPhoneNumber());
        databaseDataRetreival.executeUpdateQuery(updateQuery, parameterList);        
    }

    @Override
    public void delete(List<Object> primaryKey) throws SQLException {
        String deleteQuery = "DELETE FROM `chattingapp`.`user` WHERE (`Phone_Number` = ?)";
        
        List<Object> parameterList = new ArrayList<>();
        primaryKey.forEach((key) -> {
            parameterList.add(key);
        });
        
        databaseDataRetreival.executeUpdateQuery(deleteQuery, parameterList);
    }

    @Override
    public User getByPrimaryKey(List<Object> primaryKeys) throws SQLException {
        String deleteQuery = "SELECT `user`.`Phone_Number`,`user`.`Name`,`user`.`Email`,"
                + "`user`.`Picture`,`user`.`Password`,`user`.`Gender`,`user`.`Gender`,"
                + "`user`.`Biography`,`user`.`Country_ID`,`user`.`Status_ID` FROM `chattingapp`.`user` "
                + "where `user`.`Phone_Number` = ?";
        
        List<Object> parameterList = new ArrayList<>();
        primaryKeys.forEach((key) -> {
            parameterList.add(key);
        });
        
        ResultSet queryResult = databaseDataRetreival.executeSelectQuery(deleteQuery, parameterList);
        queryResult.beforeFirst();
        User user = new User();
        if(queryResult.next())
        {
            user.setPhoneNumber(queryResult.getString(1));
            user.setName(queryResult.getString(2));
            user.setEmail(queryResult.getString(3));
            user.setPicture(queryResult.getBytes(4));
            user.setPassword(queryResult.getString(5));
            user.setGender(queryResult.getInt(6) == 0 ? false : true);
            user.setDateOfBirth(queryResult.getDate(7));
            user.setBiography(queryResult.getString(8));
            user.setCountryID(queryResult.getByte(9));
            user.setStatusID(queryResult.getByte(10));
        }
        return user;
    }

    @Override
    public List<User> getAll() throws SQLException {
        String getAllQuery = "SELECT `user`.`Phone_Number`,`user`.`Name`,`user`.`Email`,"
                + "`user`.`Picture`,`user`.`Password`,`user`.`Gender`,`user`.`Gender`,"
                + "`user`.`Biography`,`user`.`Country_ID`,`user`.`Status_ID` FROM `chattingapp`.`user` ";
        
        ResultSet queryResult = databaseDataRetreival.executeSelectQuery(getAllQuery, new ArrayList<>());
        queryResult.beforeFirst();
        
        List<User> userList = new ArrayList<>();
        
        while(queryResult.next())
        {
            User user = new User();
            user.setPhoneNumber(queryResult.getString(1));
            user.setName(queryResult.getString(2));
            user.setEmail(queryResult.getString(3));
            user.setPicture(queryResult.getBytes(4));
            user.setPassword(queryResult.getString(5));
            user.setDateOfBirth(queryResult.getDate(7));
            user.setBiography(queryResult.getString(8));
            user.setCountryID(queryResult.getByte(9));
            user.setStatusID(queryResult.getByte(10));
            userList.add(user);
        }
        
        
        return userList;
    }
    
}
