package com.example.android.barbera;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class welcom extends AppCompatActivity {

    private Button login,register;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);

//        getSupportActionBar().hide();
//        setContentView(R.layout.activity_welcom);

        login = findViewById(R.id.welcom_login);
        register = findViewById(R.id.welcom_reg);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(welcom.this,reg_page.class));
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(welcom.this,login_reg.class));
            }
        });

    }
}