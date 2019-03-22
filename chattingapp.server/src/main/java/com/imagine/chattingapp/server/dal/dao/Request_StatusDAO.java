/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.server.dal.dao;

import com.imagine.chattingapp.common.entity.Request_Status;
import com.imagine.chattingapp.server.control.MainController;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

/**
 *
 * @author Mahmoud Shereif
 */
public class Request_StatusDAO implements DAO<Request_Status> {
    DatabaseDataRetreival databaseDataRetreival = null;

    public Request_StatusDAO() throws SQLException{
        databaseDataRetreival = new DatabaseDataRetreival();
    }
    com.imagine.chattingapp.server.dal.entity.RequestStatus mapToAnnoRequestStatus(Request_Status requestStatus){
        com.imagine.chattingapp.server.dal.entity.RequestStatus requestStatusAnno = new com.imagine.chattingapp.server.dal.entity.RequestStatus();
        requestStatusAnno.setId(requestStatus.getId());
        requestStatusAnno.setDescription(requestStatus.getDescription());
        return requestStatusAnno;
    }
    Request_Status mapToRequestStatus(com.imagine.chattingapp.server.dal.entity.RequestStatus requestStatusAnno){
        Request_Status requestStatus = new Request_Status();
        requestStatus.setId(requestStatusAnno.getId());
        requestStatus.setDescription(requestStatusAnno.getDescription());
        return requestStatus;
    }
    
    @Override
    public void persist(Request_Status requestStatus) throws SQLException {
        MainController.session.beginTransaction();
        MainController.session.persist(mapToAnnoRequestStatus(requestStatus));
        MainController.session.getTransaction().commit();
    }

    @Override
    public void update(Request_Status requestStatus) throws SQLException {
        MainController.session.beginTransaction();
        MainController.session.saveOrUpdate(mapToAnnoRequestStatus(requestStatus));
        MainController.session.getTransaction().commit();
    }
    @Override
    public void delete(List<Object> primaryKey) throws SQLException {
        byte key = (byte) primaryKey.get(0);
        com.imagine.chattingapp.server.dal.entity.RequestStatus requestStatusAnno = MainController.session.get(com.imagine.chattingapp.server.dal.entity.RequestStatus.class,key);
        MainController.session.beginTransaction();
        MainController.session.delete(requestStatusAnno);
        MainController.session.getTransaction().commit();
    }

    @Override
    public Request_Status getByPrimaryKey(List<Object> primaryKeys) throws SQLException {
        byte key = (byte) primaryKeys.get(0);
        com.imagine.chattingapp.server.dal.entity.RequestStatus requestStatusAnno = MainController.session.get(com.imagine.chattingapp.server.dal.entity.RequestStatus.class, key);
        Request_Status requestStatus = mapToRequestStatus(requestStatusAnno);
        return requestStatus;
    }

    @Override
    public List<Request_Status> getAll() throws SQLException {
        Query countryQuery = MainController.session.createQuery("from com.imagine.chattingapp.server.dal.entity.RequestStatus");
        List<com.imagine.chattingapp.server.dal.entity.RequestStatus> allAnnoRequestStatuses = countryQuery.list();
        List<Request_Status> returnedRequestStatuses = new ArrayList<>();
        allAnnoRequestStatuses.forEach((annoRequestStatus)->{
            returnedRequestStatuses.add(mapToRequestStatus(annoRequestStatus));
        });
        return returnedRequestStatuses;
    }

    @Override
    public List<Request_Status> getByColumnNames(List<String> columnNames, List<Object> columnValues) throws SQLException {
        Criteria requestStatusCriteria = MainController.session.createCriteria(com.imagine.chattingapp.server.dal.entity.RequestStatus.class);
        for(int i = 0; i<columnNames.size(); i++){
            requestStatusCriteria = requestStatusCriteria.add(Restrictions.eq(columnNames.get(i), columnValues.get(0)));
        }
        List<com.imagine.chattingapp.server.dal.entity.RequestStatus> allAnnoRequestStatus = requestStatusCriteria.list();
        List<Request_Status> returnedRequestStatuses = new ArrayList<>();
        allAnnoRequestStatus.forEach((requestStatusAnno)->{
            returnedRequestStatuses.add(mapToRequestStatus(requestStatusAnno));
        });
        return returnedRequestStatuses;   
    }
    
}
