/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.server.dal.dao;


import com.imagine.chattingapp.common.entity.User_Status;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        String persistQuery = "INSERT INTO `chattingapp`.`user_status` (`ID`, `Description`) VALUES (?, ?);";
        
        List<Object> parameterList = new ArrayList<>();
        parameterList.add(userStatus.getId());
        parameterList.add(userStatus.getDescription());
        
        databaseDataRetreival.executeUpdateQuery(persistQuery, parameterList);
    }

    @Override
    public void update(User_Status userStatus) throws SQLException {
        String updateQuery = "UPDATE `chattingapp`.`user_status` SET `Description` = ? WHERE (`ID` = ?);";
        
        List<Object> parameterList = new ArrayList<>();
        parameterList.add(userStatus.getDescription());
        parameterList.add(userStatus.getId());
        
        databaseDataRetreival.executeUpdateQuery(updateQuery, parameterList);        
    }

    @Override
    public void delete(List<Object> primaryKey) throws SQLException {
        String deleteQuery = "DELETE FROM `chattingapp`.`user_status` WHERE (`ID` = ?);";
        
        List<Object> parameterList = new ArrayList<>();
        primaryKey.forEach((key) -> {
            parameterList.add(key);
        });
        
        databaseDataRetreival.executeUpdateQuery(deleteQuery, parameterList);
    }

    @Override
    public User_Status getByPrimaryKey(List<Object> primaryKeys) throws SQLException {
        String deleteQuery = "SELECT `user_status`.`ID`, `user_status`.`Description` "
                + "FROM `chattingapp`.`user_status` "
                + "WHERE (`ID` = ?);";
        
        List<Object> parameterList = new ArrayList<>();
        primaryKeys.forEach((key) -> {
            parameterList.add(key);
        });
        
        ResultSet queryResult = databaseDataRetreival.executeSelectQuery(deleteQuery, parameterList);
        queryResult.beforeFirst();
        User_Status userStatus = new User_Status();
        if(queryResult.next())
        {
            userStatus.setId(queryResult.getByte(1));
            userStatus.setDescription(queryResult.getString(2));
        }
        return userStatus;
    }

    @Override
    public List<User_Status> getAll() throws SQLException {
        String getAllQuery = "SELECT `user_status`.`ID`, `user_status`.`Description` "
                + "FROM `chattingapp`.`user_status` ";
        
        ResultSet queryResult = databaseDataRetreival.executeSelectQuery(getAllQuery, new ArrayList<>());
        queryResult.beforeFirst();
        
        List<User_Status> userStatusList = new ArrayList<>();
        
        while(queryResult.next())
        {
            User_Status userStatus = new User_Status();
            userStatus.setId(queryResult.getByte(1));
            userStatus.setDescription(queryResult.getString(2));
            userStatusList.add(userStatus);
        }
        
        
        return userStatusList;
    }

    @Override
    public List<User_Status> getByColumnNames(List<String> columnNames, List<Object> columnValues) throws SQLException {
                String getByColumnNamesQuery = "SELECT `user_status`.`ID`, `user_status`.`Description` "
                + "FROM `chattingapp`.`user_status`  "
                + "WHERE ";
        
        for(int i = 0 ; i < columnNames.size() ; i++){
            getByColumnNamesQuery = getByColumnNamesQuery.concat("(" + columnNames.get(i) + " = ?) AND ");
        }
        
        int lastANDIndex = getByColumnNamesQuery.lastIndexOf("AND");
        getByColumnNamesQuery = getByColumnNamesQuery.substring(0, lastANDIndex);
        
        ResultSet queryResult = databaseDataRetreival.executeSelectQuery(getByColumnNamesQuery, columnValues);
        queryResult.beforeFirst();
        List<User_Status> userStatusList = new ArrayList<>();
        
        while(queryResult.next())
        {
            User_Status userStatus = new User_Status();
            userStatus.setId(queryResult.getByte(1));
            userStatus.setDescription(queryResult.getString(2));
            userStatusList.add(userStatus);
        }
        
        
        return userStatusList;
    }

    
    
}
