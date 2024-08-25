package com.example.usermanagementapp.view;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.health.connect.datatypes.SleepSessionRecord;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.os.Bundle;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.usermanagementapp.R;
import com.example.usermanagementapp.model.User;
import com.example.usermanagementapp.viewmodel.MyViewModel;

import java.io.IOException;

public class AddEditUserActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_PICK = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private static final int REQUEST_PERMISSION_CAMERA = 100;

    private ImageView imageViewAvatar;
    private Button buttonSelectImage, saveButton;
    private EditText editTextFirstName, editTextLastName, editTextEmail, editTextId;
    private MyViewModel myViewModel;
    private Uri selectedImage;

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
        buttonSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImagePickerDialog();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String first_name = editTextFirstName.getText().toString().trim();
                String last_name = editTextLastName.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String id = editTextId.getText().toString().trim();
                String imageUrl = selectedImage != null ? selectedImage.toString() : "";

                //TODO Continue validate the inputs !
                if (TextUtils.isEmpty(first_name) || TextUtils.isEmpty(last_name) || TextUtils.isEmpty(email)) {
                    Toast.makeText(AddEditUserActivity.this, "Please enter all data", Toast.LENGTH_LONG).show();
                    return;

                }

                User user = new User(id, first_name, last_name, email, imageUrl);
//                Toast.makeText(AddEditUserActivity.this,  first_name , Toast.LENGTH_LONG).show();
//                Toast.makeText(AddEditUserActivity.this,  last_name , Toast.LENGTH_LONG).show();
//                Toast.makeText(AddEditUserActivity.this,  id , Toast.LENGTH_LONG).show();
//                Toast.makeText(AddEditUserActivity.this, email, Toast.LENGTH_LONG).show();
//                Toast.makeText(AddEditUserActivity.this, imageUrl, Toast.LENGTH_LONG).show();
                myViewModel.insertUser(user);

                Toast.makeText(AddEditUserActivity.this,"you added "+user.getFirstName()+" successfully", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(AddEditUserActivity.this,MainActivity.class);
//                startActivity(intent);
            }
        });
    }

    private void showImagePickerDialog() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, REQUEST_IMAGE_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_PICK) {
                Uri selectedImage = data.getData();
                imageViewAvatar.setImageURI(selectedImage);
                // Save the image URI or convert it to a path if necessary
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
                Toast.makeText(AddEditUserActivity.this,"Permission denied",Toast.LENGTH_LONG).show();
            }
        }
    }

}