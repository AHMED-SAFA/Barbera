package com.example.android.barbera;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class profile extends AppCompatActivity {

    Toolbar toolbar;

    ImageView profileImageView;
    TextView nameTextView, nicknameTextView, ageTextView, addressTextView;

    DatabaseReference databaseReference;
    Button crtbttn;
    FirebaseAuth auth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbar = findViewById(R.id.toolbar_id);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        crtbttn = findViewById(R.id.crtBtn);

        crtbttn.setOnClickListener(v -> {
            Intent intent = new Intent(this, creat_profile.class);
            startActivity(intent);
        });


        profileImageView = findViewById(R.id.profileImageView);
        nameTextView = findViewById(R.id.nameTextView);
        nicknameTextView = findViewById(R.id.nicknameTextView);
        ageTextView = findViewById(R.id.ageTextView);
        addressTextView = findViewById(R.id.addressTextView);

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {

            String userId = user.getUid();

            databaseReference.child(userId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        User userProfile = dataSnapshot.getValue(User.class);

                        if (userProfile != null) {
                            nameTextView.setText("Name: " + userProfile.getName());
                            nicknameTextView.setText("Nickname: " + userProfile.getNickname());
                            ageTextView.setText("Age: " + userProfile.getAge());
                            addressTextView.setText("Address: " + userProfile.getAddress());

                            // Load profile image using Picasso or any other image loading library
                            Picasso.get().load(userProfile.getImageUrl()).into(profileImageView);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(profile.this, "Error Occured", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}