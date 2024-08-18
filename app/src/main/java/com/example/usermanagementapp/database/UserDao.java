package com.example.usermanagementapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.usermanagementapp.model.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insertUser(User... user);

    @Update
    void updateUser(User... user);

    @Delete
    void deleteUser(User... user);

    @Query("delete from user_table where firstName  = :name")
    void deleteUserByName(String name);

    // get list of the users
    @Query("SELECT * FROM user_table ORDER BY id ASC")
    LiveData<List<User>> getAllUsers();
   // search user by name
    @Query("select *from user_table where firstName like '%' || :name || '%' ")
    LiveData<List<User>>searchUsers(String name);
   // search category by Id
    @Query(("select * from user_table where categoryId = :categoryId"))
    LiveData<List<User>> getUsersByCategory(int categoryId);
    // search user by Id
    @Query(("select * from user_table where userID = :userId"))
    LiveData<List<User>> getUsersById(int userId);

}