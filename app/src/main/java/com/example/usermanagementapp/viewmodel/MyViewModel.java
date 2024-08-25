package com.example.usermanagementapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.usermanagementapp.database.UserDao;
import com.example.usermanagementapp.model.User;
import com.example.usermanagementapp.repository.Repository;

import java.util.List;


public class MyViewModel extends AndroidViewModel {
    private UserDao userDao;
    Repository repository;
    private LiveData<List<User>> allUsers;

    public MyViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);


    }

    //Get users from network
    public LiveData<List<User>> getUsersFromNetwork() {
        return repository.getUsersFromNetwork();
    }

    // Sync data between network and local database
    public void syncDataWithNetwork() {
        repository.syncDataWithNetwork();
    }

    //TODO check the implementation for this method
    // Get users from local database
    public LiveData<List<User>> getAllUsers() {
        return repository.getAllUsers();
    }
    // implementation of the user

    public void insertUser(User... user) {
        repository.insertUser(user);
    }

    public void updateUser(User... user) {
        repository.updateUser(user);
    }

    public void deleteUser(User... user) {
        repository.deleteUser(user);
    }

    // delete user by name
    public void deleteUserByName(String name) {
        repository.deleteUserByName(name);
    }

    // search user by name
    public LiveData<List<User>> searchUsers(String name) {
        return repository.searchUsers(name);
    }

    // search user by Id
    public LiveData<User> getUsersById(int userId) {

//        return UserDao.getUsersById(userId);
        return userDao.getUsersById(userId);
    }


}

