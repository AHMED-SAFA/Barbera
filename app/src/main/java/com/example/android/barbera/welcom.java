package com.example.android.barbera;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;

public class welcom extends AppCompatActivity {

    Button login,register;
    LottieAnimationView lottieAnimationView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);


        login = findViewById(R.id.welcom_login);
        register = findViewById(R.id.welcom_reg);
        lottieAnimationView = findViewById(R.id.animation_view);

        lottieAnimationView.setAnimation(R.raw.welcomjson);
        lottieAnimationView.playAnimation();
        lottieAnimationView.loop(true);

        login.setOnClickListener(v -> {
            Intent intent = new Intent(welcom.this,reg_page.class);
            startActivity(intent);
        });

        register.setOnClickListener(v -> {
            Intent intent = new Intent(welcom.this,login_reg.class);
            startActivity(intent);
        });


//        login.setOnClickListener(v -> startActivity(new Intent(welcom.this,reg_page.class)));
//        register.setOnClickListener(v -> startActivity(new Intent(welcom.this,login_reg.class)));

    }
}