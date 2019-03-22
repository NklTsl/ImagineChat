/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.imagine.chattingapp.server.dal.dao;

import com.imagine.chattingapp.common.entity.Admin;
import com.imagine.chattingapp.common.entity.User;
import com.imagine.chattingapp.server.control.MainController;
import java.sql.Date;
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
public class AdminDAO implements DAO<Admin> {
    DatabaseDataRetreival databaseDataRetreival = null;
    public AdminDAO() throws SQLException{
        databaseDataRetreival = new DatabaseDataRetreival();
    }
    com.imagine.chattingapp.server.dal.entity.Admin mapToAnnoAdmin(Admin admin){
        com.imagine.chattingapp.server.dal.entity.Admin adminAnno = new com.imagine.chattingapp.server.dal.entity.Admin();
        adminAnno.setPhoneNumber(admin.getPhoneNumber());
        adminAnno.setPassword(admin.getPassword());
        adminAnno.setName(admin.getName());
        adminAnno.setDateOfBirth(new Date(admin.getDateOfBirth()));
        adminAnno.setEmail(admin.getEmail());
        adminAnno.setBiography(admin.getBiography());
        adminAnno.setGender(admin.getGender() ? (byte)1 : (byte)0);
        adminAnno.setPicture(admin.getPicture());
        adminAnno.setCountry(MainController.session.get(com.imagine.chattingapp.server.dal.entity.Country.class, admin.getCountryID()));
        return adminAnno;
    }
    Admin mapToAdmin(com.imagine.chattingapp.server.dal.entity.Admin adminAnno){
        Admin admin = new Admin();
        admin.setPhoneNumber(adminAnno.getPhoneNumber());
        admin.setPassword(adminAnno.getPassword());
        admin.setName(adminAnno.getName());
        admin.setDateOfBirth(adminAnno.getDateOfBirth().getTime());
        admin.setEmail(admin.getEmail());
        admin.setBiography(admin.getBiography());
        admin.setGender(adminAnno.getGender() != 0 );
        admin.setPicture(admin.getPicture());
        admin.setCountryID(adminAnno.getCountry().getId());
        return admin;
    }
    @Override
    public void persist(Admin admin) throws SQLException {
        MainController.session.beginTransaction();
        MainController.session.persist(mapToAnnoAdmin(admin));
        MainController.session.getTransaction().commit();
    }
    
    @Override
    public void update(Admin admin) throws SQLException {
        MainController.session.beginTransaction();
        MainController.session.saveOrUpdate(mapToAnnoAdmin(admin));
        MainController.session.getTransaction().commit();
    }
    
    @Override
    public void delete(List<Object> primaryKey) throws SQLException {
        String key = (String) primaryKey.get(0);
        com.imagine.chattingapp.server.dal.entity.Admin adminAnno = MainController.session.get(com.imagine.chattingapp.server.dal.entity.Admin.class,key);
        MainController.session.beginTransaction();
        MainController.session.delete(adminAnno);
        MainController.session.getTransaction().commit();
    }
    
    @Override
    public Admin getByPrimaryKey(List<Object> primaryKeys) throws SQLException {
        String key = (String)primaryKeys.get(0);
        com.imagine.chattingapp.server.dal.entity.Admin adminAnno = MainController.session.get(com.imagine.chattingapp.server.dal.entity.Admin.class, key);
        Admin admin = mapToAdmin(adminAnno);
        return admin;
        
    }
    
    @Override
    public List<Admin> getAll() throws SQLException {
        Query adminsQuery = MainController.session.createQuery("from com.imagine.chattingapp.server.dal.entity.Admin");
        List<com.imagine.chattingapp.server.dal.entity.Admin> allAnnoAdmins = adminsQuery.list();
        List<Admin> returnedAdmins = new ArrayList();
        allAnnoAdmins.forEach((annoAdmin)->{
            returnedAdmins.add(mapToAdmin(annoAdmin));
        });
        return returnedAdmins;
        
    }

    @Override
    public List<Admin> getByColumnNames(List<String> columnNames, List<Object> columnValues) throws SQLException {
        Criteria adminCriteria = MainController.session.createCriteria(com.imagine.chattingapp.server.dal.entity.Admin.class);
        for(int i = 0; i<columnNames.size(); i++){
            adminCriteria = adminCriteria.add(Restrictions.eq(columnNames.get(i), columnValues.get(0)));
        }
        List<com.imagine.chattingapp.server.dal.entity.Admin> allAnnoAdmins = adminCriteria.list();
        List<Admin> returnedAdmin = new ArrayList<>();
        allAnnoAdmins.forEach((adminAnno)->{
            returnedAdmin.add(mapToAdmin(adminAnno));
        });
        return returnedAdmin;
    }
}
