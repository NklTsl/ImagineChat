/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.server.dal.dao;


import com.imagine.chattingapp.common.entity.One_To_One_Message_status;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mahmoud Shereif
 */
public class One_To_One_Message_statusDAO implements DAO<One_To_One_Message_status>{

    DatabaseDataRetreival databaseDataRetreival = null;
    public One_To_One_Message_statusDAO() throws SQLException {
        databaseDataRetreival = new DatabaseDataRetreival();
    }

    
    @Override
    public void persist(One_To_One_Message_status oneToOneMesasgeStatus) throws SQLException {
        String persistQuery = "INSERT INTO `chattingapp`.`one_to_one_message_status` (`ID`, `Description`) VALUES (?, ?);";
        
        List<Object> parameterList = new ArrayList<>();
        parameterList.add(oneToOneMesasgeStatus.getId());
        parameterList.add(oneToOneMesasgeStatus.getDescription());
        
        databaseDataRetreival.executeUpdateQuery(persistQuery, parameterList);
    }

    @Override
    public void update(One_To_One_Message_status oneToOneMesasgeStatus) throws SQLException {
        String updateQuery = "UPDATE `chattingapp`.`one_to_one_message_status` SET `Description` = ? WHERE (`ID` = ?);";
        
        List<Object> parameterList = new ArrayList<>();
        parameterList.add(oneToOneMesasgeStatus.getDescription());
        parameterList.add(oneToOneMesasgeStatus.getId());
        
        databaseDataRetreival.executeUpdateQuery(updateQuery, parameterList);  
    }

    @Override
    public void delete(List<Object> primaryKey) throws SQLException {
        String deleteQuery = "DELETE FROM `chattingapp`.`one_to_one_message_status` WHERE (`ID` = ?);";
        
        List<Object> parameterList = new ArrayList<>();
        primaryKey.forEach((key) -> {
            parameterList.add(key);
        });
        
        databaseDataRetreival.executeUpdateQuery(deleteQuery, parameterList);
    }

    @Override
    public One_To_One_Message_status getByPrimaryKey(List<Object> primaryKeys) throws SQLException {
        String deleteQuery = "SELECT `one_to_one_message_status`.`ID`, `one_to_one_message_status`.`Description` "
                + "FROM `chattingapp`.`one_to_one_message_status` "
                + "WHERE (`ID` = ?);";
        
        List<Object> parameterList = new ArrayList<>();
        primaryKeys.forEach((key) -> {
            parameterList.add(key);
        });
        
        ResultSet queryResult = databaseDataRetreival.executeSelectQuery(deleteQuery, parameterList);
        queryResult.beforeFirst();
        One_To_One_Message_status oneToOneMessageStatus = new One_To_One_Message_status();
        if(queryResult.next())
        {
            oneToOneMessageStatus.setId(queryResult.getByte(1));
            oneToOneMessageStatus.setDescription(queryResult.getString(2));
        }
        return oneToOneMessageStatus;
    }

    @Override
    public List<One_To_One_Message_status> getAll() throws SQLException {
        String getAllQuery = "SELECT `one_to_one_message_status`.`ID`, `one_to_one_message_status`.`Description` "
                + "FROM `chattingapp`.`one_to_one_message_status` ";
        
        ResultSet queryResult = databaseDataRetreival.executeSelectQuery(getAllQuery, new ArrayList<>());
        queryResult.beforeFirst();
        
        List<One_To_One_Message_status> oneToOneMessageStatusList = new ArrayList<>();
        
        while(queryResult.next())
        {
            One_To_One_Message_status oneToOneMessageStatus = new One_To_One_Message_status();
            oneToOneMessageStatus.setId(queryResult.getByte(1));
            oneToOneMessageStatus.setDescription(queryResult.getString(2));
            oneToOneMessageStatusList.add(oneToOneMessageStatus);
        }
        
        
        return oneToOneMessageStatusList;
    }

    @Override
    public List<One_To_One_Message_status> getByColumnNames(List<String> columnNames, List<Object> columnValues) throws SQLException {
        String getByColumnNamesQuery = "SELECT `one_to_one_message_status`.`ID`, `one_to_one_message_status`.`Description` "
                + "FROM `chattingapp`.`one_to_one_message_status`  "
                + "WHERE ";
        
        for(int i = 0 ; i < columnNames.size() ; i++){
            getByColumnNamesQuery = getByColumnNamesQuery.concat("(" + columnNames.get(i) + " = ?) AND ");
        }
        
        int lastANDIndex = getByColumnNamesQuery.lastIndexOf("AND");
        getByColumnNamesQuery = getByColumnNamesQuery.substring(0, lastANDIndex);
        
        ResultSet queryResult = databaseDataRetreival.executeSelectQuery(getByColumnNamesQuery, columnValues);
        queryResult.beforeFirst();
        List<One_To_One_Message_status> oneToOneMessageStatusList = new ArrayList<>();
        
        while(queryResult.next())
        {
            One_To_One_Message_status oneToOneMessageStatus = new One_To_One_Message_status();
            oneToOneMessageStatus.setId(queryResult.getByte(1));
            oneToOneMessageStatus.setDescription(queryResult.getString(2));
            oneToOneMessageStatusList.add(oneToOneMessageStatus);
        }
        
        
        return oneToOneMessageStatusList;
    }
    
}
