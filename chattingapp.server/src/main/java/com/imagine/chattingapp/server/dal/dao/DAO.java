/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagine.chattingapp.server.dal.dao;

import com.imagine.chattingapp.common.entity.Entity;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Mahmoud Shereif
 */
public interface DAO<T extends Entity> {
    public void persist(T entity) throws SQLException;
    public void update(T entity) throws SQLException;
    public void delete(List<Object> primaryKey) throws SQLException;
    public T getByPrimaryKey(List<Object> primaryKeys) throws SQLException;
    public List<T> getAll() throws SQLException;
    public List<T> getByColumnNames(List<String> columnNames, List<Object> columnValues) throws SQLException;
}
