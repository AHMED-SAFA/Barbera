package com.example.android.barbera;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class reg_page extends AppCompatActivity{

    Button login;
    TextView sinuptext;
    EditText emaill, pass;
    FirebaseAuth auth;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_page);

        login = findViewById(R.id.login_in_login);
        sinuptext = findViewById(R.id.signpRedirectText_id);
        emaill = findViewById(R.id.name_in_login);
        pass = findViewById(R.id.pass_in_login);
        mAuth = FirebaseAuth.getInstance();
        auth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null)
        {
            startActivity(new Intent(reg_page.this, homePage.class ));
            finish();
        }

        login.setOnClickListener(view ->
        {
            String email = emaill.getText().toString();
            String passwrd = pass.getText().toString();

            if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches())
            {
                if (!passwrd.isEmpty())
                {
                    auth.signInWithEmailAndPassword(email, passwrd).addOnSuccessListener(authResult ->
                    {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(reg_page.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(reg_page.this, homePage.class ));
                        finish();
                    }).addOnFailureListener(e -> Toast.makeText(reg_page.this, "Login Failed", Toast.LENGTH_SHORT).show());
                }
                else
                {
                    pass.setError("Password cannot be empty");
                }

            }
            else if (email.isEmpty()) {
                emaill.setError("Email cannot be empty");
            } else {
                emaill.setError("Please enter a valid email");
            }
        });

        sinuptext.setOnClickListener(v -> {
            Intent intent = new Intent(reg_page.this, login_reg.class);
            startActivity(intent);
            finish();
        });
    }
}
