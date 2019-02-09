/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.server.dal.dao;

import java.sql.SQLException;
import java.util.List;
import com.imagine.chattingapp.server.dal.entity.Entity;

/**
 *
 * @author Mahmoud Shereif
 */
public interface DAO {
    public void persist(Entity E) throws SQLException;
    public void update(Entity E) throws SQLException;
    public List<Entity> getAll(Entity E) throws SQLException;
}
