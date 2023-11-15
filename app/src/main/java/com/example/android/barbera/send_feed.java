package com.example.android.barbera;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;
import java.util.Map;

public class send_feed extends AppCompatActivity {
    String mssg = "";
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    Toolbar toolbar;
    DatabaseReference databaseReference;
    EditText editText;
    Button button;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_feed);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        editText = findViewById(R.id.feedbackEditText);
        button = findViewById(R.id.submitButton);
        toolbar = findViewById(R.id.toolbar_id);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null)
        {
            String userUid = user.getUid();
            databaseReference = database.getReference("users").child(userUid).child("messages");
        }

        button.setOnClickListener(v ->
        {
            mssg = editText.getText().toString();

            if (databaseReference != null) {
                DatabaseReference newMessageRef = databaseReference.push();
                Map<String, Object> messageData = new HashMap<>();
                messageData.put("content", mssg);
                messageData.put("timestamp", System.currentTimeMillis());
                newMessageRef.updateChildren(messageData);
                editText.setText("");
                Toast.makeText(this, "Feedback sent!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
