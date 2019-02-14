/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.server.dal.dao;

import com.imagine.chattingapp.server.dal.entity.Country;
import com.imagine.chattingapp.server.dal.entity.User_Status;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mahmoud Shereif
 */
public class CountryDAO implements DAO<Country> {
    DatabaseDataRetreival databaseDataRetreival = null;

    public CountryDAO() throws SQLException {
        databaseDataRetreival = new DatabaseDataRetreival();
    }
    
    @Override
    public void persist(Country country) throws SQLException {
        String persistQuery = "INSERT INTO `chattingapp`.`country` (`ID`, `Name`) VALUES (?, ?);";
        
        List<Object> parameterList = new ArrayList<>();
        parameterList.add(country.getId());
        parameterList.add(country.getName());
        
        databaseDataRetreival.executeUpdateQuery(persistQuery, parameterList);
    }

    @Override
    public void update(Country country) throws SQLException {
        String updateQuery = "UPDATE `chattingapp`.`country` SET `Name` = ? WHERE (`ID` = ?);";
        
        List<Object> parameterList = new ArrayList<>();
        parameterList.add(country.getName());
        parameterList.add(country.getId());
        
        databaseDataRetreival.executeUpdateQuery(updateQuery, parameterList);        
    }

    @Override
    public void delete(List<Object> primaryKey) throws SQLException {
        String deleteQuery = "DELETE FROM `chattingapp`.`country` WHERE (`ID` = ?);";
        
        List<Object> parameterList = new ArrayList<>();
        primaryKey.forEach((key) -> {
            parameterList.add(key);
        });
        
        databaseDataRetreival.executeUpdateQuery(deleteQuery, parameterList);
    }

    @Override
    public Country getByPrimaryKey(List<Object> primaryKeys) throws SQLException {
        String deleteQuery = "SELECT `country`.`ID`, `country`.`Name` "
                + "FROM `chattingapp`.`country` "
                + "WHERE (`ID` = ?);";
        
        List<Object> parameterList = new ArrayList<>();
        primaryKeys.forEach((key) -> {
            parameterList.add(key);
        });
        
        ResultSet queryResult = databaseDataRetreival.executeSelectQuery(deleteQuery, parameterList);
        queryResult.beforeFirst();
        Country country = new Country();
        if(queryResult.next())
        {
            country.setId(queryResult.getByte(1));
            country.setName(queryResult.getString(2));
        }
        return country;
    }

    @Override
    public List<Country> getAll() throws SQLException {
        String getAllQuery = "SELECT `country`.`ID`, `country`.`Name` "
                + "FROM `chattingapp`.`country` ";
        
        ResultSet queryResult = databaseDataRetreival.executeSelectQuery(getAllQuery, new ArrayList<>());
        queryResult.beforeFirst();
        
        List<Country> countryList = new ArrayList<>();
        
        while(queryResult.next())
        {
            Country country = new Country();
            country.setId(queryResult.getByte(1));
            country.setName(queryResult.getString(2));
            countryList.add(country);
        }
        
        
        return countryList;
    }

    @Override
    public List<Country> getByColumnNames(List<String> columnNames, List<Object> columnValues) throws SQLException {
        String getByColumnNamesQuery = "SELECT `country`.`ID`, `country`.`Name` "
                + "FROM `chattingapp`.`country`  "
                + "WHERE ";
        
        for(int i = 0 ; i < columnNames.size() ; i++){
            getByColumnNamesQuery = getByColumnNamesQuery.concat("(" + columnNames.get(i) + " = ?) AND ");
        }
        
        int lastANDIndex = getByColumnNamesQuery.lastIndexOf("AND");
        getByColumnNamesQuery = getByColumnNamesQuery.substring(0, lastANDIndex);
        
        ResultSet queryResult = databaseDataRetreival.executeSelectQuery(getByColumnNamesQuery, columnValues);
        queryResult.beforeFirst();
        List<Country> countryList = new ArrayList<>();
        
        while(queryResult.next())
        {
            Country country = new Country();
            country.setId(queryResult.getByte(1));
            country.setName(queryResult.getString(2));
            countryList.add(country);
        }
        
        
        return countryList;
    }
    
}
