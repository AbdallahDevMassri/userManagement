package com.example.usermanagementapp.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.usermanagementapp.model.Category;
import com.example.usermanagementapp.model.User;
import com.example.usermanagementapp.repository.Repository;
import java.util.List;


public class MyViewModel extends AndroidViewModel {

    Repository repository;
    public MyViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);

    }
    // implementation of the user

    public void insertUser(User... user) {
        repository.insertUser(user);
    }

    public void updateUser(User... user) {
        repository.updateUser(user);
    }

    public void deleteUser(User...  user) {
       repository.deleteUser(user);
    }
    // delete user by name
    public void deleteUserByName(String name) {
        repository.deleteUserByName(name);
    }

    // get list of the users
    public LiveData<List<User>> getAllUsers() {

        return repository.getAllUsers();
    }

    // search user by name

    public LiveData<List<User>>searchUsers(String name) {

        return repository.searchUsers(name);
    }

    // search category by Id
    public LiveData<List<User>> getUsersByCategory(int categoryId) {

        return repository.getUsersByCategory(categoryId);
    }
    // search user by Id

    public LiveData<List<User>> getUsersById(int userId) {

        return repository.getUsersById(userId);
    }

    // implementation of the category

    public void insertCategory(Category... category) {
        repository.insertCategory(category);

    }

    public void updateCategory(Category... category) {

      repository.updateCategory(category);
    }

    public void deleteCategory(Category... category) {
        repository.deleteCategory(category);
    }
    // delete category by Id
    public void deleteCategoryById(String id) {
        repository.deleteCategoryById(id);
    }

    // get list of the categories
    public LiveData<List<Category>> getAllCategories(){

        return repository.getAllCategories();
    }
    // search category by name
    public LiveData<List<Category>>searchCategory(String name){
        return repository.searchCategory(name);
    }


    public LiveData<List<Category>> getCategoryByRating() {

        return repository.getCategoryByRating();
    }

}

