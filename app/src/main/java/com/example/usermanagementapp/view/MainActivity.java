package com.example.usermanagementapp.view;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.usermanagementapp.R;
import com.example.usermanagementapp.viewmodel.MyViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
        myViewModel.getAllUsers().observe(this, users -> userAdapter.setUsers(users));


        // Initialize FloatingActionButton and set its click listener
        FloatingActionButton addButton = findViewById(R.id.add_item_fab);
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditUserActivity.class);
            startActivity(intent);
        });
    }

}



