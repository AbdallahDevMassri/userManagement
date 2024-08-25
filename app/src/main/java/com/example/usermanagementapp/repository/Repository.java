package com.example.usermanagementapp.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.usermanagementapp.model.User;
import com.example.usermanagementapp.database.UserDao;
import com.example.usermanagementapp.database.UserDatabase;
import com.example.usermanagementapp.network.ReqResApi;
import com.example.usermanagementapp.network.RetrofitClient;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    private UserDao userDao;
    private ReqResApi reqResApi;
    private final ExecutorService executorService;


    public Repository(Application application) {
        UserDatabase db = UserDatabase.getDatabase(application);
        userDao = db.userDao();
        reqResApi = RetrofitClient.getClient().create(ReqResApi.class);
        executorService = Executors.newSingleThreadExecutor();
    }

    //fetch users from network and save to local database
    public LiveData<List<User>> getUsersFromNetwork() {
        MutableLiveData<List<User>> liveData = new MutableLiveData<>();
        reqResApi.getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<User> users = response.body();
                    // Save to Room Database
                    executorService.execute(() -> {
                        userDao.insertUser(users.toArray(new User[0]));
                        liveData.postValue(users);
                    });
                }
            }
            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                liveData.postValue(null); // handle failure
            }
        });
        return liveData;
    }

    // Get all users (local database)
    public LiveData<List<User>> getAllUsers(){
        return userDao.getAllUsers();
    }
    // Sync data: fetch from network and update local database
    public void syncDataWithNetwork() {
        reqResApi.getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<User> users = response.body();
                    executorService.execute(() -> {
                        userDao.insertUser(users.toArray(new User[0]));
                    });
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
    }

    // Insert, Update, and Delete methods for local database (Room)
    public void insertUser(User... user) {
        executorService.execute(() -> userDao.insertUser(user));
    }

    public void updateUser(User... user) {
        executorService.execute(() -> userDao.updateUser(user));
    }

    public void deleteUser(User... user) {
        executorService.execute(() -> userDao.deleteUser(user));
    }

    // delete user by name
    public void deleteUserByName(String name) {
        executorService.execute(() -> userDao.deleteUserByName(name));
    }

    // search user by name

    public LiveData<List<User>> searchUsers(String name) {

        return userDao.searchUsers(name);
    }


    // search user by Id

    public LiveData<List<User>> getUsersById(int userId) {

        return getUsersById(userId);
    }

}
