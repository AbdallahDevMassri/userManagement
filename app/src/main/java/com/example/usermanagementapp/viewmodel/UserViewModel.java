package com.example.usermanagementapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.usermanagementapp.model.User;
import com.example.usermanagementapp.repository.UserRepository;

import java.util.List;

public class UserViewModel extends ViewModel {

    private final UserRepository userRepository;
    private final LiveData<List<User>> userList;

    public UserViewModel() {
        userRepository = new UserRepository();
        userList = userRepository.getAllUsers();
    }

    public LiveData<List<User>> getUserList() {
        return userList;
    }

    public void addUser(User user) {
        userRepository.insertUser(user);
    }

    public void updateUser(User user) {
        userRepository.updateUser(user);
    }

    public void deleteUser(User user) {
        userRepository.deleteUser(user);
    }
}
