package com.example.android.barbera;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

public class creat_profile extends AppCompatActivity {

    ImageView imageView;
    Button up_button,shwbttn;
    Toolbar toolbar;
    FloatingActionButton floatingActionButton;
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    EditText nicknameEditText, addressEditText, ageEditText, nameEditText;
    FirebaseStorage storage;
    StorageReference storageReference;
    Uri selectedImageUri;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_profile);

        toolbar = findViewById(R.id.toolbar_id);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        shwbttn = findViewById(R.id.shwBtn);


        shwbttn.setOnClickListener(v -> {
            Intent intent = new Intent(this, profile.class);
            startActivity(intent);
        });


        imageView = findViewById(R.id.img_id);
        floatingActionButton = findViewById(R.id.floatingActionButton_id);
        up_button = findViewById(R.id.up_button_id);
        nicknameEditText = findViewById(R.id.editTextText);
        addressEditText = findViewById(R.id.editTextText2);
        ageEditText = findViewById(R.id.editTextText4);
        nameEditText = findViewById(R.id.editTextText5);

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        floatingActionButton.setOnClickListener(v -> ImagePicker.with(creat_profile.this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start()
        );

        up_button.setOnClickListener(v -> uploadData());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        selectedImageUri= data.getData();
        imageView.setImageURI(selectedImageUri);
    }


    private void uploadData() {
        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            final String userId = user.getUid();

            final String nickname = nicknameEditText.getText().toString();
            final String address = addressEditText.getText().toString();
            final String age = ageEditText.getText().toString();
            final String name = nameEditText.getText().toString();

            if (selectedImageUri != null) {
                // Upload image to Firebase Storage
                final StorageReference imageRef = storageReference.child("profile_images/" + userId);
                imageRef.putFile(selectedImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {
                            // Get the download URL for the image
                            imageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if (task.isSuccessful()) {
                                        String imageUrl = task.getResult().toString();
                                        // Save user data with image URL to Firebase Realtime Database
                                        User userProfile = new User(userId, name, nickname, age, address, imageUrl);
                                        databaseReference.child(userId).setValue(userProfile);

                                        nicknameEditText.setText("");
                                        addressEditText.setText("");
                                        ageEditText.setText("");
                                        nameEditText.setText("");

                                        Toast.makeText(creat_profile.this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
//                                        Intent intent = new Intent(creat_profile.this, profile.class);
//                                        startActivity(intent);
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(creat_profile.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else {
                // Save user data without image URL to Firebase Realtime Database
                User userProfile = new User(userId, name, nickname, age, address, "");

                databaseReference.child(userId).setValue(userProfile);
                Toast.makeText(creat_profile.this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
            }
        }
    }



}