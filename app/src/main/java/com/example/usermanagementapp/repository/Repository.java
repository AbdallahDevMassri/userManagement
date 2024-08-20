package com.example.usermanagementapp.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;


import com.example.usermanagementapp.model.User;
import com.example.usermanagementapp.database.UserDao;
import com.example.usermanagementapp.database.UserDatabase;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {

    UserDao userDao;

    private final ExecutorService executorService;


    public Repository(Application application) {
        UserDatabase db = UserDatabase.getDatabase(application);
        userDao = db.userDao();


        executorService = Executors.newSingleThreadExecutor();
    }


    // implementation of the user
    public void insertUser(User... user) {
        executorService.execute(() -> userDao.insertUser(user));
    }

    public void updateUser(User... user) {
        executorService.execute(() -> userDao.updateUser(user));
    }

    public void deleteUser(User...  user) {
        executorService.execute(() -> userDao.deleteUser(user));
    }
    // delete user by name
    public void deleteUserByName(String name) {
        executorService.execute(() -> userDao.deleteUserByName(name));
    }

    // get list of the users
    public LiveData<List<User>> getAllUsers() {
        return userDao.getAllUsers();
    }

    // search user by name

    public LiveData<List<User>>searchUsers(String name) {

    return userDao.searchUsers(name);
    }


    // search user by Id

    public LiveData<List<User>> getUsersById(int userId) {

        return getUsersById(userId);
    }

}
