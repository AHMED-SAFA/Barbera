package com.example.android.barbera;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class login_reg extends AppCompatActivity
{
     TextView login_text;
     Button sinup;
     EditText emaill,passwrd;
     FirebaseAuth auth;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_reg);

        login_text =findViewById(R.id.loginRedirectText_id);
        sinup = findViewById(R.id.signup_button_id);
        emaill = findViewById(R.id.signup_email_id);
        passwrd = findViewById(R.id.signup_password_id);
        auth = FirebaseAuth.getInstance();

        sinup.setOnClickListener(view ->
        {
            String user = emaill.getText().toString();
            String pass = passwrd.getText().toString();

            if (user.isEmpty())
            {
                emaill.setError("Email cannot be empty");
            }
            if(pass.isEmpty())
            {
                passwrd.setError("Password cannot be empty");
            }
            else
            {
                auth.createUserWithEmailAndPassword(user,pass).addOnCompleteListener(task ->
                {
                    if(task.getException() instanceof FirebaseAuthUserCollisionException)
                    {
                        Toast.makeText(this, "Already exists!", Toast.LENGTH_SHORT).show();
                    }
                    else if(task.isSuccessful()){
                        Toast.makeText(login_reg.this, "Signup Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(login_reg.this, reg_page.class));
                    }
                    else
                    {
                        Toast.makeText(this, "Signup Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        login_text.setOnClickListener(v -> {
            Intent intent = new Intent(login_reg.this, reg_page.class);
            startActivity(intent);
            finish();
        });
    }
}

