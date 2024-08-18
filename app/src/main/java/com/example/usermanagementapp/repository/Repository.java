package com.example.usermanagementapp.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Query;
import androidx.room.Update;

import com.example.usermanagementapp.database.CategoryDao;
import com.example.usermanagementapp.model.Category;
import com.example.usermanagementapp.model.User;
import com.example.usermanagementapp.database.UserDao;
import com.example.usermanagementapp.database.UserDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {

    UserDao userDao;
    CategoryDao categoryDao;
//    private final LiveData<List<User>> userList;
//    private final LiveData<List<Category>> categoryList;
    private final ExecutorService executorService;


    public Repository(Application application) {
        UserDatabase db = UserDatabase.getDatabase(application);
        userDao = db.userDao();
        categoryDao = db.categoryDao();


//        userList = userDao.getAllUsers();
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

    // search category by Id
    public LiveData<List<User>> getUsersByCategory(int categoryId) {

    return userDao.getUsersByCategory(categoryId);
    }
    // search user by Id

    public LiveData<List<User>> getUsersById(int userId) {

        return getUsersById(userId);
    }

    // implementation of the category

    public void insertCategory(Category... category) {
        executorService.execute(() -> categoryDao.insertCategory(category));

    }

    public void updateCategory(Category... category) {

        executorService.execute(() -> categoryDao.updateCategory(category));
    }

    public void deleteCategory(Category... category) {
        executorService.execute(() -> categoryDao.deleteCategory(category));

    }

    // delete category by Id

    public void deleteCategoryById(String id) {
        executorService.execute(() -> categoryDao.deleteCategoryById(id));
    }

    // get list of the categories
    public LiveData<List<Category>> getAllCategories(){

        return categoryDao.getAllCategories();

    }
    // search category by name
     public  LiveData<List<Category>>searchCategory(String name){
        return categoryDao.searchCategory(name);

    }


    public LiveData<List<Category>> getCategoryByRating() {

        return categoryDao.getCategoryByRating();
    }

}
