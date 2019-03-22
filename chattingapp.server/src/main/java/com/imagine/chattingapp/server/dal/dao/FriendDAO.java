/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.imagine.chattingapp.server.dal.dao;

import com.imagine.chattingapp.common.entity.Friend;
import com.imagine.chattingapp.server.control.MainController;
import com.imagine.chattingapp.server.dal.entity.FriendId;
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
public class FriendDAO implements DAO<Friend> {
    DatabaseDataRetreival databaseDataRetreival = null;
    
    public FriendDAO() throws SQLException {
        databaseDataRetreival = new DatabaseDataRetreival();
    }
    com.imagine.chattingapp.server.dal.entity.Friend mapToAnnoFriend(Friend friend){
        com.imagine.chattingapp.server.dal.entity.Friend friendAnno = new com.imagine.chattingapp.server.dal.entity.Friend();
        friendAnno.setBlockFlag(friend.getBlockFlag()?(byte)1:(byte)0);
        String phone1 = friend.getPhoneNumber1();
        String phone2 = friend.getPhoneNumber2();
        FriendId Id = new FriendId(phone1, phone2);
        friendAnno.setId(Id);
        friendAnno.setRelativeGroup(friend.getRealtiveGroup());
        //other fields need not to be setted
        return friendAnno;
    }
    Friend mapToFriend(com.imagine.chattingapp.server.dal.entity.Friend friendAnno){
        Friend friend = new Friend();
        FriendId Id = friendAnno.getId();
        friend.setPhoneNumber1(Id.getPhoneNumber1());
        friend.setPhoneNumber2(Id.getPhoneNumber2());
        friend.setRealtiveGroup(friendAnno.getRelativeGroup());
        friend.setBlockFlag(friendAnno.getBlockFlag()!=0);
        return friend;
    }
    @Override
    public void persist(Friend friend) throws SQLException {
        MainController.session.beginTransaction();
        MainController.session.persist(mapToAnnoFriend(friend));
        MainController.session.getTransaction().commit();
                
       
    }
    
    @Override
    public void update(Friend friend) throws SQLException {
        MainController.session.beginTransaction();
        MainController.session.saveOrUpdate(mapToAnnoFriend(friend));
        MainController.session.getTransaction().commit();
    }
    
    @Override
    public void delete(List<Object> primaryKey) throws SQLException {
        String phone1 = (String)primaryKey.get(0);
        String phone2 = (String)primaryKey.get(1);
        FriendId key = new FriendId(phone1, phone2);
        MainController.session.beginTransaction();
        MainController.session.delete(key);
        MainController.session.getTransaction().commit();
        
    }
    
    @Override
    public Friend getByPrimaryKey(List<Object> primaryKeys) throws SQLException {
        String phone1 = (String)primaryKeys.get(0);
        String phone2 = (String)primaryKeys.get(1);
        FriendId key = new FriendId(phone1, phone2);
        com.imagine.chattingapp.server.dal.entity.Friend friendAnno = MainController.session.get(com.imagine.chattingapp.server.dal.entity.Friend.class, key);
        Friend friend = mapToFriend(friendAnno);
        return friend;
    }
    
    @Override
    public List<Friend> getAll() throws SQLException {
        Query friendQuery = MainController.session.createQuery("from com.imagine.chattingapp.server.dal.entity.Friend");
        List<com.imagine.chattingapp.server.dal.entity.Friend> allFriendsAnno = friendQuery.list();
        List<Friend> friends = new ArrayList<>();
        allFriendsAnno.forEach((friendAnno)->{
            friends.add(mapToFriend(friendAnno));
        });
        return friends;
    }
    
    @Override
    public List<Friend> getByColumnNames(List<String> columnNames, List<Object> columnValues) throws SQLException {
        Criteria friendsCriteria = MainController.session.createCriteria(com.imagine.chattingapp.server.dal.entity.Friend.class);
        for(int i=0;i<columnNames.size();i++){
            friendsCriteria = friendsCriteria.add(Restrictions.eq(columnNames.get(i), columnValues.get(i)));
        }
        List<com.imagine.chattingapp.server.dal.entity.Friend> allFriendsAnno = friendsCriteria.list();
        List<Friend> friends = new ArrayList<>();
        allFriendsAnno.forEach((friendAnno)->{
            friends.add(mapToFriend(friendAnno));
        });
        return friends;
    }
    
}
