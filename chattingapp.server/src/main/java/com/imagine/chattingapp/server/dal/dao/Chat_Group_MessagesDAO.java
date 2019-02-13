/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.server.dal.dao;

import com.imagine.chattingapp.server.dal.entity.Chat_Group_Messages;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Mahmoud Shereif
 */
public class Chat_Group_MessagesDAO implements DAO<Chat_Group_Messages> {

    @Override
    public void persist(Chat_Group_Messages entity) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Chat_Group_Messages entity) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(List<Object> primaryKey) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Chat_Group_Messages getByPrimaryKey(List<Object> primaryKeys) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Chat_Group_Messages> getAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
