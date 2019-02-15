/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.imagine.chattingapp.server.dal.dao;

import com.imagine.chattingapp.common.entity.Admin;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mahmoud Shereif
 */
public class AdminDAO implements DAO<Admin> {
    DatabaseDataRetreival databaseDataRetreival = null;
    public AdminDAO() throws SQLException{
        databaseDataRetreival = new DatabaseDataRetreival();
    }
    
    @Override
    public void persist(Admin admin) throws SQLException {
        String persistQuery = "INSERT INTO `chattingapp`.`admin` "
                + "(`Phone_Number`, `Name`, `Email`, `Password`, `Gender`, `Date_Of_Birth`, `Biography`, `Country_ID`) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        List<Object> parameterList = new ArrayList<>();
        parameterList.add(admin.getPhoneNumber());
        parameterList.add(admin.getName());
        parameterList.add(admin.getEmail());
        parameterList.add(admin.getPassword());
        parameterList.add(admin.getGender());
        parameterList.add(admin.getDateOfBirth());
        parameterList.add(admin.getBiography());
        parameterList.add(admin.getCountryID());
        
        databaseDataRetreival.executeUpdateQuery(persistQuery, parameterList);
    }
    
    @Override
    public void update(Admin admin) throws SQLException {
        String updateQuery = "UPDATE `chattingapp`.`admin` "
                + "SET `Name` = ?, `Email` = ?, `Password` = ?, "
                + "`Gender` = ?, `Date_Of_Birth` = ?, `Biography` = ?, "
                + "`Country_ID` = ? WHERE (`Phone_Number` = ?)";
        
        List<Object> parameterList = new ArrayList<>();
        parameterList.add(admin.getName());
        parameterList.add(admin.getEmail());
        parameterList.add(admin.getPassword());
        parameterList.add(admin.getGender());
        parameterList.add(admin.getDateOfBirth());
        parameterList.add(admin.getBiography());
        parameterList.add(admin.getCountryID());
        parameterList.add(admin.getPhoneNumber());
        
        databaseDataRetreival.executeUpdateQuery(updateQuery, parameterList);
    }
    
    @Override
    public void delete(List<Object> primaryKey) throws SQLException {
        String deleteQuery = "DELETE FROM `chattingapp`.`admin` WHERE (`Phone_Number` = ?)";
        
        List<Object> parameterList = new ArrayList<>();
        primaryKey.forEach((key) -> {
            parameterList.add(key);
        });
        
        databaseDataRetreival.executeUpdateQuery(deleteQuery, parameterList);
    }
    
    @Override
    public Admin getByPrimaryKey(List<Object> primaryKeys) throws SQLException {
        String getByPrimaryKeyQuery = "SELECT `admin`.`Phone_Number`,`admin`.`Name`,`admin`.`Email`,"
                + "`admin`.`Picture`,`admin`.`Password`,`admin`.`Gender`,`admin`.`Gender`,"
                + "`admin`.`Biography`,`admin`.`Country_ID` FROM `chattingapp`.`admin` "
                + "where `admin`.`Phone_Number` = ?";
        
        List<Object> parameterList = new ArrayList<>();
        primaryKeys.forEach((key) -> {
            parameterList.add(key);
        });
        
        ResultSet queryResult = databaseDataRetreival.executeSelectQuery(getByPrimaryKeyQuery, parameterList);
        queryResult.beforeFirst();
        Admin admin = new Admin();
        if(queryResult.next())
        {
            admin.setPhoneNumber(queryResult.getString(1));
            admin.setName(queryResult.getString(2));
            admin.setEmail(queryResult.getString(3));
            admin.setPicture(queryResult.getBytes(4));
            admin.setPassword(queryResult.getString(5));
            admin.setGender(queryResult.getInt(6) == 0 ? false : true);
            admin.setDateOfBirth(queryResult.getDate(7).getTime());
            admin.setBiography(queryResult.getString(8));
            admin.setCountryID(queryResult.getByte(9));
        }
        return admin;
        
    }
    
    @Override
    public List<Admin> getAll() throws SQLException {
        String getAllQuery = "SELECT `admin`.`Phone_Number`,`admin`.`Name`,`admin`.`Email`,"
                + "`admin`.`Picture`,`admin`.`Password`,`admin`.`Gender`,`admin`.`Gender`,"
                + "`admin`.`Biography`,`admin`.`Country_ID` FROM `chattingapp`.`admin` ";
        
        ResultSet queryResult = databaseDataRetreival.executeSelectQuery(getAllQuery, new ArrayList<>());
        queryResult.beforeFirst();
        
        List<Admin> adminList = new ArrayList<>();
        
        while(queryResult.next())
        {
            Admin admin = new Admin();
            admin.setPhoneNumber(queryResult.getString(1));
            admin.setName(queryResult.getString(2));
            admin.setEmail(queryResult.getString(3));
            admin.setPicture(queryResult.getBytes(4));
            admin.setPassword(queryResult.getString(5));
            admin.setGender(queryResult.getInt(6) == 0 ? false : true);
            admin.setDateOfBirth(queryResult.getDate(7).getTime());
            admin.setBiography(queryResult.getString(8));
            admin.setCountryID(queryResult.getByte(9));
            adminList.add(admin);
        }
        
        
        return adminList;
    }

    @Override
    public List<Admin> getByColumnNames(List<String> columnNames, List<Object> columnValues) throws SQLException {
                String getByColumnNamesQuery = "SELECT `admin`.`Phone_Number`,`admin`.`Name`,`admin`.`Email`,"
                + "`admin`.`Picture`,`admin`.`Password`,`admin`.`Gender`,`admin`.`Gender`,"
                + "`admin`.`Biography`,`admin`.`Country_ID` FROM `chattingapp`.`admin`  "
                + "WHERE ";
        
        for(int i = 0 ; i < columnNames.size() ; i++){
            getByColumnNamesQuery = getByColumnNamesQuery.concat("(" + columnNames.get(i) + " = ?) AND ");
        }
        
        int lastANDIndex = getByColumnNamesQuery.lastIndexOf("AND");
        getByColumnNamesQuery = getByColumnNamesQuery.substring(0, lastANDIndex);
        
        ResultSet queryResult = databaseDataRetreival.executeSelectQuery(getByColumnNamesQuery, columnValues);
        queryResult.beforeFirst();
        List<Admin> adminList = new ArrayList<Admin>();
        if(queryResult.next())
        {
            Admin admin = new Admin();
            admin.setPhoneNumber(queryResult.getString(1));
            admin.setName(queryResult.getString(2));
            admin.setEmail(queryResult.getString(3));
            admin.setPicture(queryResult.getBytes(4));
            admin.setPassword(queryResult.getString(5));
            admin.setGender(queryResult.getInt(6) == 0 ? false : true);
            admin.setDateOfBirth(queryResult.getDate(7).getTime());
            admin.setBiography(queryResult.getString(8));
            admin.setCountryID(queryResult.getByte(9));
            adminList.add(admin);
        }
        
        return adminList;
    }
    
    
}
