package com.example.usermanagementapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usermanagementapp.R;
import com.example.usermanagementapp.model.User;
import com.example.usermanagementapp.viewmodel.MyViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    MyViewModel myViewModel;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        userAdapter = new UserAdapter();
        recyclerView.setAdapter(userAdapter);

        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);

        fetchUsers();

        userAdapter.setOnItemClickListener(new UserAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(User user) {
                // Start AddEditUserActivity and pass the selected user's data
                Intent intent = new Intent(MainActivity.this, AddEditUserActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(User user) {
                myViewModel.deleteUser(user);
                Toast.makeText(MainActivity.this, "User deleted", Toast.LENGTH_SHORT).show();
                fetchUsers();
            }
        });


        // Initialize FloatingActionButton and set its click listener
        FloatingActionButton addButton = findViewById(R.id.add_item_fab);
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditUserActivity.class);
            startActivity(intent);
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        fetchUsers();
    }
    private void fetchUsers() {
        myViewModel.getUsersFromNetwork().observe(this, users -> {
            if (users != null && !users.isEmpty()) {
                userAdapter.setUsers(users);
            } else {
                Toast.makeText(MainActivity.this, "Unable to fetch users from the network. Showing local data.", Toast.LENGTH_LONG).show();
                myViewModel.getAllUsers().observe(this, localUsers -> {
                    if (localUsers != null && !localUsers.isEmpty()) {
                        userAdapter.setUsers(localUsers);
                    }
                });
            }
        });
    }

}



