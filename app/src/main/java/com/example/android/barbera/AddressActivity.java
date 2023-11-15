package com.example.android.barbera;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

public class AddressActivity extends AppCompatActivity {
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        toolbar = findViewById(R.id.toolbar_id);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView companyNameTextView = findViewById(R.id.companyNameTextView);
        TextView addressTextView = findViewById(R.id.addressTextView);

        String companyName = getIntent().getStringExtra("companyName");
        Address address = getIntent().getParcelableExtra("address");

        companyNameTextView.setText(companyName);
        assert address != null;
        addressTextView.setText(address.toString());

    }
}