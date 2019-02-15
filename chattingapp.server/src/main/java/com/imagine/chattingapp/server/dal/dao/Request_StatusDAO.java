/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.server.dal.dao;

import com.imagine.chattingapp.common.entity.Request_Status;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mahmoud Shereif
 */
public class Request_StatusDAO implements DAO<Request_Status> {
    DatabaseDataRetreival databaseDataRetreival = null;

    public Request_StatusDAO() throws SQLException{
        databaseDataRetreival = new DatabaseDataRetreival();
    }
    
    @Override
    public void persist(Request_Status requestStatus) throws SQLException {
        String persistQuery = "INSERT INTO `chattingapp`.`request_status` (`ID`, `Description`) VALUES (?, ?);";
        
        List<Object> parameterList = new ArrayList<>();
        parameterList.add(requestStatus.getId());
        parameterList.add(requestStatus.getDescription());
        
        databaseDataRetreival.executeUpdateQuery(persistQuery, parameterList);
    }

    @Override
    public void update(Request_Status requestStatus) throws SQLException {
        String updateQuery = "UPDATE `chattingapp`.`request_status` SET `Description` = ? WHERE (`ID` = ?);";
        
        List<Object> parameterList = new ArrayList<>();
        parameterList.add(requestStatus.getDescription());
        parameterList.add(requestStatus.getId());
        
        databaseDataRetreival.executeUpdateQuery(updateQuery, parameterList);       
    }

    @Override
    public void delete(List<Object> primaryKey) throws SQLException {
        String deleteQuery = "DELETE FROM `chattingapp`.`request_status` WHERE (`ID` = ?);";
        
        List<Object> parameterList = new ArrayList<>();
        primaryKey.forEach((key) -> {
            parameterList.add(key);
        });
        
        databaseDataRetreival.executeUpdateQuery(deleteQuery, parameterList);
    }

    @Override
    public Request_Status getByPrimaryKey(List<Object> primaryKeys) throws SQLException {
        String deleteQuery = "SELECT `request_status`.`ID`, `request_status`.`Description` "
                + "FROM `chattingapp`.`request_status` "
                + "WHERE (`ID` = ?);";
        
        List<Object> parameterList = new ArrayList<>();
        primaryKeys.forEach((key) -> {
            parameterList.add(key);
        });
        
        ResultSet queryResult = databaseDataRetreival.executeSelectQuery(deleteQuery, parameterList);
        queryResult.beforeFirst();
        Request_Status requestStatus = new Request_Status();
        if(queryResult.next())
        {
            requestStatus.setId(queryResult.getByte(1));
            requestStatus.setDescription(queryResult.getString(2));
        }
        return requestStatus;
    }

    @Override
    public List<Request_Status> getAll() throws SQLException {
        String getAllQuery = "SELECT `request_status`.`ID`, `request_status`.`Description` "
                + "FROM `chattingapp`.`request_status` ";
        
        ResultSet queryResult = databaseDataRetreival.executeSelectQuery(getAllQuery, new ArrayList<>());
        queryResult.beforeFirst();
        
        List<Request_Status> requestStatusList = new ArrayList<>();
        
        while(queryResult.next())
        {
            Request_Status requestStatus = new Request_Status();
            requestStatus.setId(queryResult.getByte(1));
            requestStatus.setDescription(queryResult.getString(2));
            requestStatusList.add(requestStatus);
        }
        
        
        return requestStatusList;
    }

    @Override
    public List<Request_Status> getByColumnNames(List<String> columnNames, List<Object> columnValues) throws SQLException {
        String getByColumnNamesQuery = "SELECT `request_status`.`ID`, `request_status`.`Description` "
                + "FROM `chattingapp`.`request_status`  "
                + "WHERE ";
        
        for(int i = 0 ; i < columnNames.size() ; i++){
            getByColumnNamesQuery = getByColumnNamesQuery.concat("(" + columnNames.get(i) + " = ?) AND ");
        }
        
        int lastANDIndex = getByColumnNamesQuery.lastIndexOf("AND");
        getByColumnNamesQuery = getByColumnNamesQuery.substring(0, lastANDIndex);
        
        ResultSet queryResult = databaseDataRetreival.executeSelectQuery(getByColumnNamesQuery, columnValues);
        queryResult.beforeFirst();
    List<Request_Status> requestStatusList = new ArrayList<>();
        
        while(queryResult.next())
        {
            Request_Status requestStatus = new Request_Status();
            requestStatus.setId(queryResult.getByte(1));
            requestStatus.setDescription(queryResult.getString(2));
            requestStatusList.add(requestStatus);
        }
        
        
        return requestStatusList;
    }
    
}
