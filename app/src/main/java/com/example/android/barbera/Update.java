package com.example.android.barbera;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Update extends AppCompatActivity {

     EditText editTextCurrentEmail;
     EditText editTextCurrentPassword;
     EditText editTextNewPassword;
     Button btnChangePassword;
     Toolbar toolbar;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        editTextCurrentEmail = findViewById(R.id.editTextCurrentEmail);
        editTextCurrentPassword = findViewById(R.id.editTextCurrentPassword);
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        btnChangePassword = findViewById(R.id.btnChangePassword);
        toolbar = findViewById(R.id.toolbar_id);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        btnChangePassword.setOnClickListener(view ->
                changePassword()
        );
    }

    private void changePassword() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            String currentEmail = editTextCurrentEmail.getText().toString();
            String currentPassword = editTextCurrentPassword.getText().toString();
            String newPassword = editTextNewPassword.getText().toString();

            if (currentEmail.isEmpty())
            {
                editTextCurrentEmail.setError("Email cannot be empty");
            }
            if (currentPassword.isEmpty())
            {
                editTextCurrentPassword.setError("Password cannot be empty");
            }
            if (newPassword.isEmpty())
            {
                editTextNewPassword.setError("Password cannot be empty");
            }
            else {

                AuthCredential credential = EmailAuthProvider.getCredential(currentEmail, currentPassword);

                user.reauthenticate(credential).addOnCompleteListener(task ->
                {
                    if (task.isSuccessful()) {
                        user.updatePassword(newPassword).addOnCompleteListener(updateTask ->
                        {
                            if (updateTask.isSuccessful()) {

                                Toast.makeText(Update.this, "Password Updated", Toast.LENGTH_SHORT).show();
                                FirebaseAuth.getInstance().signOut();
                                finish();
                                Intent intent = new Intent(Update.this, reg_page.class);
                                startActivity(intent);

                            } else {
                                Toast.makeText(Update.this, "Update Failed", Toast.LENGTH_SHORT).show();
                            }

                        });
                    } else {
                        Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
        else
        {
            Toast.makeText(this, "Register First", Toast.LENGTH_SHORT).show();
        }
    }

}