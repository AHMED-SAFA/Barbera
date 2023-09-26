package com.example.android.barbera;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class providers extends AppCompatActivity {

    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_providers);

        toolbar = findViewById(R.id.toolbar_id);
        bottomNavigationView = findViewById(R.id.bottom_id);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        bottomNavigationView.setOnItemSelectedListener(item -> {

            if(item.getItemId() == R.id.home_id)
            {
                Intent intent = new Intent(providers.this, homePage.class);
                startActivity(intent);
                finish();
            }
            else if(item.getItemId() == R.id.share)
            {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBodyText = "Your message goes here";
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject here");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBodyText);
                startActivity(Intent.createChooser(sharingIntent, "Share"));
                return true;
            }
            else if(item.getItemId() == R.id.cart)
            {
                Toast.makeText(providers.this, "Cart", Toast.LENGTH_SHORT).show();
            }
            else if(item.getItemId() == R.id.about_usid){
                Intent intent = new Intent(providers.this, about__us.class);
                startActivity(intent);
            }
            return true;
        });
    }
}