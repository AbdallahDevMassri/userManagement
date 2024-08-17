package com.example.usermanagementapp.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.usermanagementapp.model.User;
import com.example.usermanagementapp.database.UserDao;
import com.example.usermanagementapp.database.UserDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserRepository {

    private final UserDao userDao;
    private final LiveData<List<User>> userList;
    private final ExecutorService executorService;


    public UserRepository(Application application) {
        UserDatabase db = UserDatabase.getDatabase(application);
        userDao = db.userDao();
        userList = userDao.getAllUsers();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<User>> getAllUsers() {
        return userList;
    }

    public void insertUser(User user) {
        executorService.execute(() -> userDao.insertUser(user));
    }

    public void updateUser(User user) {
        executorService.execute(() -> userDao.updateUser(user));
    }

    public void deleteUser(User user) {
        executorService.execute(() -> userDao.deleteUser(user));
    }
}
