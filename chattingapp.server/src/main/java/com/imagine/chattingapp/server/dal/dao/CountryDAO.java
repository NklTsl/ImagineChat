/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.server.dal.dao;

import com.imagine.chattingapp.common.entity.Country;
import com.imagine.chattingapp.server.control.MainController;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;

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
        MainController.session.beginTransaction();
        MainController.session.saveOrUpdate(mapToAnnoCountry(country));
        MainController.session.getTransaction().commit();
    }

    @Override
    public void delete(List<Object> primaryKey) throws SQLException {
        byte key = (byte)primaryKey.get(0);
        com.imagine.chattingapp.server.dal.entity.Country countryAnno = MainController.session.get(com.imagine.chattingapp.server.dal.entity.Country.class, key);
        MainController.session.beginTransaction();
        MainController.session.delete(countryAnno);
        MainController.session.getTransaction().commit();
    }

    @Override
    public Country getByPrimaryKey(List<Object> primaryKeys) throws SQLException {
        byte key = (byte) primaryKeys.get(0);
        com.imagine.chattingapp.server.dal.entity.Country countryAnno = MainController.session.get(com.imagine.chattingapp.server.dal.entity.Country.class, key);
        Country country = mapToCountry(countryAnno);
        return country;
    }

    @Override
    public List<Country> getAll() throws SQLException {
        Criteria criteria = MainController.session.createCriteria(com.imagine.chattingapp.server.dal.entity.Country.class);
        List<com.imagine.chattingapp.server.dal.entity.Country> allAnnoCountry = criteria.list();
        List<Country> returnedCountry = new ArrayList();
        allAnnoCountry.forEach((countryAnno)->{
            returnedCountry.add(mapToCountry(countryAnno));
        });
        return returnedCountry;
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

    private Country mapToCountry(com.imagine.chattingapp.server.dal.entity.Country countryAnno) {
        Country country = new Country();
        country.setId(countryAnno.getId());
        country.setName(countryAnno.getName());
        return country;
    }
    
    com.imagine.chattingapp.server.dal.entity.Country mapToAnnoCountry(Country country){
        com.imagine.chattingapp.server.dal.entity.Country countryAnno = new com.imagine.chattingapp.server.dal.entity.Country();
        countryAnno.setName(country.getName());
        return countryAnno;
    }
}
