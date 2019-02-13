/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.server.dal.dao;

import com.imagine.chattingapp.server.dal.entity.On_To_One_Messages;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Mahmoud Shereif
 */
public class One_To_One_MessagesDAO implements DAO<On_To_One_Messages> {

    @Override
    public void persist(On_To_One_Messages entity) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(On_To_One_Messages entity) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(List<Object> primaryKey) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public On_To_One_Messages getByPrimaryKey(List<Object> primaryKeys) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<On_To_One_Messages> getAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
