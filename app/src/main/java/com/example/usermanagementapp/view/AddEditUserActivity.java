package com.example.usermanagementapp.view;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
    private Button buttonSelectImage;
    Button saveButton;

    MyViewModel myViewModel;

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

        imageViewAvatar =findViewById(R.id.image_view_avatar);
        buttonSelectImage = findViewById(R.id.button_select_image);
        buttonSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImagePickerDialog();
            }
        });
        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        saveButton = findViewById(R.id.button_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String first_name = String.valueOf(findViewById(R.id.edit_text_first_name));
                String last_name =String.valueOf(findViewById(R.id.edit_text_last_name));
                String email = String.valueOf(findViewById(R.id.edit_text_email));
                String id = String.valueOf(findViewById(R.id.edit_text_id));
                String imageUrl = String.valueOf(R.id.image_view_avatar);

               //TODO Continue validate the inputs !
                if (TextUtils.isEmpty(first_name) || TextUtils.isEmpty(last_name) || TextUtils.isEmpty(email)) {
                    Toast.makeText(AddEditUserActivity.this,"Please enter all data", Toast.LENGTH_LONG).show();
                    return;

                }

                User user = new User(id,first_name,last_name,email,imageUrl);
                myViewModel.insertUser(user);
                Toast.makeText(AddEditUserActivity.this,"user saved \n "+first_name+" "+last_name,Toast.LENGTH_LONG).show();
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
            }
        }
    }

}