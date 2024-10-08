package com.example.usermanagementapp.view;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.example.usermanagementapp.R;
import com.example.usermanagementapp.model.User;
import com.example.usermanagementapp.viewmodel.MyViewModel;

import java.util.Objects;

public class AddEditUserActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_PICK = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private static final int REQUEST_PERMISSION_CAMERA = 100;

    private ImageView imageViewAvatar;
    private Button buttonSelectImage, saveButton;
    private EditText editTextFirstName, editTextLastName, editTextEmail, editTextId;
    private Uri selectedImage;
    private MyViewModel myViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_edit_user);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)
                , (v, insets) -> {
                    Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                    return insets;
                });

        imageViewAvatar = findViewById(R.id.image_view_avatar);
        buttonSelectImage = findViewById(R.id.button_select_image);
        saveButton = findViewById(R.id.button_save);
        editTextFirstName = findViewById(R.id.edit_text_first_name);
        editTextLastName = findViewById(R.id.edit_text_last_name);
        editTextEmail = findViewById(R.id.edit_text_email);
        editTextId = findViewById(R.id.edit_text_id);
        imageViewAvatar = findViewById(R.id.image_view_avatar);

        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        Intent intent = getIntent();
        if (intent.hasExtra("user")) {
            User user = (User) intent.getSerializableExtra("user");
            fillUserData(user);
        }

        buttonSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImagePickerDialog();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveUser();
            }
        });
    }

    private void fillUserData(User user) {
        if (user != null) {
            editTextFirstName.setText(user.getFirstName() != null ? user.getFirstName() : "");
            editTextLastName.setText(user.getLastName() != null ? user.getLastName() : "");
            editTextEmail.setText(user.getEmail() != null ? user.getEmail() : "");
            editTextId.setText(String.valueOf(user.getId()));
            if (user.getAvatar() != null && !user.getAvatar().isEmpty()) {
                imageViewAvatar.setImageURI(Uri.parse(user.getAvatar()));
                selectedImage = Uri.parse(user.getAvatar());
            }
        }
    }

    private void saveUser() {
        String first_name = editTextFirstName.getText().toString().trim();
        String last_name = editTextLastName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String userID = editTextId.getText().toString().trim();
        String imageUrl = selectedImage != null ? selectedImage.toString() : "";

        if (TextUtils.isEmpty(first_name) || TextUtils.isEmpty(last_name) || TextUtils.isEmpty(email)) {
            Toast.makeText(AddEditUserActivity.this, "Please enter all data", Toast.LENGTH_LONG).show();
            return;
        }

        User user = new User(userID, first_name, last_name, email, imageUrl);
        user.setId(Integer.parseInt(userID));

        if (getIntent().hasExtra("user")) {

            int userId = ((User) Objects.requireNonNull(getIntent().getSerializableExtra("user"))).getId();

            //update existing user
            if (getIntent().hasExtra("user")) {
                // Update existing user
                myViewModel.updateUser(user);
                Toast.makeText(AddEditUserActivity.this, "User updated successfully", Toast.LENGTH_LONG).show();
            } else {
                // Add new user
                myViewModel.insertUser(user);
                Toast.makeText(AddEditUserActivity.this, "User added successfully", Toast.LENGTH_LONG).show();
            }
            Intent intent = new Intent(AddEditUserActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }


    private void showImagePickerDialog() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, REQUEST_IMAGE_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == REQUEST_IMAGE_PICK) {
                selectedImage = data.getData();
                imageViewAvatar.setImageURI(selectedImage);
            } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                imageViewAvatar.setImageBitmap(photo);
                //TODO
                // Save the image or upload it to the server
            }
        }
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION_CAMERA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, open the camera
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, REQUEST_IMAGE_CAPTURE);
            } else {
                // Permission denied
                Toast.makeText(AddEditUserActivity.this, "Permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

}