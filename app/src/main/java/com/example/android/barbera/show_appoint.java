//package com.example.android.barbera;
//
//import android.os.Bundle;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//public class show_appoint extends AppCompatActivity {
//
//     ListView listView;
//     ArrayAdapter<String> dataAdapter;
//     DatabaseReference databaseReference;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_show_appoint);
//
//        listView = findViewById(R.id.listview);
//
//        dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
//        listView.setAdapter(dataAdapter);
//
//        // Initialize Firebase components (FirebaseDatabase, DatabaseReference)
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        databaseReference = database.getReference("users");
//
//        // Retrieve data based on email
//        String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
//        retrieveDataForEmail(userEmail);
//    }
//
//    private void retrieveDataForEmail(String userEmail) {
//
//        // Query data based on the user's email
//        databaseReference.orderByChild("email").equalTo(userEmail).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                dataAdapter.clear();
//
//                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
//
//                    String selectedOption1 = userSnapshot.child("selectedOption1").getValue(String.class);
//                    String timestamp = userSnapshot.child("timestamp").getValue(String.class);
//                    String date = userSnapshot.child("date").getValue(String.class);
//                    String cost = userSnapshot.child("Cost").getValue(String.class);
//
//                    String item = "Style Type: " + selectedOption1 + "\nBarber Will Arrive At: " + timestamp + "\nDate: " + date + "\nCost: "
//                            + cost;
//
//                    dataAdapter.add(item);
//                }
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Toast.makeText(show_appoint.this, "Failed", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//}


package com.example.android.barbera;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class show_appoint extends AppCompatActivity {

    ListView appointmentsListView;
    ArrayList<String> appointmentList;
    ArrayAdapter<String> adapter;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_appoint);

        appointmentsListView = findViewById(R.id.listview);
        appointmentList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, appointmentList);
        appointmentsListView.setAdapter(adapter);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null)
        {
            DatabaseReference userAppointmentsRef = FirebaseDatabase.getInstance()
                    .getReference("user_appointments").child(user.getUid());

            userAppointmentsRef.addValueEventListener(new ValueEventListener()
            {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    appointmentList.clear();

                    for (DataSnapshot appointmentSnapshot : dataSnapshot.getChildren())
                    {
                        String selectedOption = appointmentSnapshot.child("selectedOption1").getValue(String.class);
                        String timestamp = appointmentSnapshot.child("timestamp").getValue(String.class);
                        String date = appointmentSnapshot.child("date").getValue(String.class);
                        String cost = appointmentSnapshot.child("Cost").getValue(String.class);
                        String appointmentInfo = selectedOption + "\nTime: " + timestamp + "\nDate: " + date + "\nCost: " + cost;
                        appointmentList.add(appointmentInfo);
                    }
                    adapter.notifyDataSetChanged();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError)
                {
                    // Handle error
                }
            });
        }
    }
}
