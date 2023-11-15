package com.example.android.barbera;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class homePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    CardView appoint, service,provideer,maps;
    DrawerLayout drawerLayout;
    TextView header_text;
    Toolbar toolbar;
    NavigationView navigationView;
    FirebaseAuth mAuth;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        appoint = findViewById(R.id.appointid);
        maps = findViewById(R.id.maps_id);
        toolbar = findViewById(R.id.toolbar_id);
        header_text = findViewById(R.id.header_text_id);
        provideer = findViewById(R.id.provider_id);
        service = findViewById(R.id.serviceid);
        navigationView = findViewById(R.id.navigation_id);
        drawerLayout = findViewById(R.id.drawerlayout_id);
        mAuth = FirebaseAuth.getInstance();

        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        
        appoint.setOnClickListener(v -> startActivity(new Intent(homePage.this, Appoint.class)));
        service.setOnClickListener(v -> startActivity(new Intent(homePage.this, show_feed.class)));
        provideer.setOnClickListener(v -> startActivity(new Intent(homePage.this, providers.class)));
        maps.setOnClickListener(v -> startActivity(new Intent(homePage.this, MainActivity.class)));

        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        getMenuInflater().inflate(R.menu.menu_things, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.shre)
        {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBodyText = "Your message goes here";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject here");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
            startActivity(Intent.createChooser(sharingIntent, "Share"));
            return true;
        }
        if (item.getItemId() == R.id.abt_us )
        {
            Intent intent = new Intent(homePage.this, about__us.class);
            startActivity(intent);
        }

        if (item.getItemId() == R.id.logout_id)
        {
            FirebaseAuth.getInstance().signOut();
            finish();
            Intent intent = new Intent(homePage.this, reg_page.class);
            startActivity(intent);
        }
        return true;
    }
    @Override
    public void onBackPressed()
    {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.home_id){
            Intent intent = new Intent(homePage.this, homePage.class);
            startActivity(intent);
            finish();
        }
        if (item.getItemId() == R.id.logout)
        {
            FirebaseAuth.getInstance().signOut();
            finish();
            Intent intent = new Intent(homePage.this, reg_page.class);
            startActivity(intent);
        }
        if(item.getItemId() == R.id.feed_id){

            Intent intent = new Intent(homePage.this, send_feed.class);
            startActivity(intent);
        }
        if(item.getItemId() == R.id.profile_id){

            Intent intent = new Intent(homePage.this, creat_profile.class);
            startActivity(intent);
        }

        if(item.getItemId() == R.id.appoint_idid){
            Intent intent = new Intent(homePage.this, show_appoint.class);
            startActivity(intent);
        }

        if(item.getItemId() == R.id.share){
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBodyText = "Your message goes here";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject here");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
            startActivity(Intent.createChooser(sharingIntent, "Share"));
            return true;
        }
        if(item.getItemId() == R.id.about_usid){
            Intent intent = new Intent(homePage.this, about__us.class);
            startActivity(intent);
        }
        if(item.getItemId() == R.id.Update_id)
        {
            Intent intent = new Intent(homePage.this, Update.class);
            startActivity(intent);
        }
        return true;
    }
}