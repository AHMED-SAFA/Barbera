//package com.example.android.barbera;
//
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.ActionBar;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//
//import android.annotation.SuppressLint;
//import android.app.DatePickerDialog;
//import android.content.Intent;
//import android.os.Bundle;
//import android.app.TimePickerDialog;
//import android.service.autofill.UserData;
//import android.widget.Button;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.Calendar;
//import java.util.Locale;
//
//public class Appoint extends AppCompatActivity {
//
//    Button pickTimeBtn, pickDateBtn;
//    TextView confirm_text;
//    RadioGroup radioGroup1;
//    RadioButton styledRadioButton, randomRadioButton;
//    String selectedRadioButtonText1 = "";
//    String selectedTime = "";
//    String costs = "8$";
//    String selectedDate = "";
//    FirebaseAuth mAuth;
//    Toolbar toolbar;
//    FirebaseDatabase database;
//    DatabaseReference databaseReference;
//
//    @SuppressLint("MissingInflatedId")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_appoint);
//
//        toolbar = findViewById(R.id.toolbar_id);
//        setSupportActionBar(toolbar);
//        ActionBar actionBar = getSupportActionBar();
//        assert actionBar != null;
//        actionBar.setDisplayHomeAsUpEnabled(true);
//
//        pickTimeBtn = findViewById(R.id.time_picker_button_id);
//        pickDateBtn = findViewById(R.id.date_picker_button_id);
//        confirm_text = findViewById(R.id.confirm_id);
//
//        styledRadioButton = findViewById(R.id.styled_radio_id);
//        randomRadioButton = findViewById(R.id.random_radio_id);
//
//        radioGroup1 = findViewById(R.id.WoringRadioGrp1_id);
//
//        // Initialize Firebase components
//        mAuth = FirebaseAuth.getInstance();
//        database = FirebaseDatabase.getInstance();
//        databaseReference = database.getReference("users");
//
//        radioGroup1.setOnCheckedChangeListener((group, checkedId) -> {
//            if (checkedId == R.id.styled_radio_id)
//                selectedRadioButtonText1 = styledRadioButton.getText().toString();
//            else if (checkedId == R.id.random_radio_id)
//                selectedRadioButtonText1 = randomRadioButton.getText().toString();
//        });
//
//        pickTimeBtn.setOnClickListener(v -> {
//            final Calendar c = Calendar.getInstance();
//            int hour = c.get(Calendar.HOUR_OF_DAY), minute = c.get(Calendar.MINUTE);
//
//            TimePickerDialog timePickerDialog = new TimePickerDialog(Appoint.this, (view, hourOfDay, minute1) -> {
//                String amOrPm;
//                if (hourOfDay < 12) {
//                    amOrPm = "AM";
//                } else {
//                    amOrPm = "PM";
//                    if (hourOfDay > 12) {
//                        hourOfDay -= 12;
//                    }
//                }
//                selectedTime = String.format(Locale.getDefault(), "%02d:%02d %s", hourOfDay, minute1, amOrPm);
//                pickTimeBtn.setText(selectedTime);
//            }, hour, minute, true); // Use true to show AM/PM format
//
//            timePickerDialog.show();
//        });
//
//        // Add an OnClickListener for the date picker button
//        pickDateBtn.setOnClickListener(v -> showDatePickerDialog());
//
//        confirm_text.setOnClickListener(v1 -> {
//            if (selectedTime.isEmpty() || selectedRadioButtonText1.isEmpty() || selectedDate.isEmpty()) {
//                Toast.makeText(Appoint.this, "Please select buttons, date, and time", Toast.LENGTH_SHORT).show();
//            } else {
//
//                FirebaseUser user = mAuth.getCurrentUser();
//                String userEmail = user.getEmail();
//
//                // Update the selected options, formatted timestamp, and date in the Realtime Database
//                databaseReference.orderByChild("email").equalTo(userEmail)
//                        .addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
//                                    userSnapshot.child("selectedOption1").getRef().setValue(selectedRadioButtonText1);
//                                    userSnapshot.child("timestamp").getRef().setValue(selectedTime);
//                                    userSnapshot.child("date").getRef().setValue(selectedDate);
//                                    userSnapshot.child("Cost").getRef().setValue(costs);
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//                                // Handle error
//                            }
//                        });
//                // Provide feedback to the user
//                Toast.makeText(this, "Appoint Confirmed", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    // Add a method to display the date picker dialog
//    private void showDatePickerDialog() {
//        Calendar c = Calendar.getInstance();
//        int years = c.get(Calendar.YEAR);
//        int month = c.get(Calendar.MONTH);
//        int day = c.get(Calendar.DAY_OF_MONTH);
//
//        DatePickerDialog datePickerDialog = new DatePickerDialog(Appoint.this, (view, year, monthOfYear, dayOfMonth) -> {
//            // Handle the selected date (e.g., store it or display it)
//            selectedDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
//            // Handle the selected date, e.g., store it or display it
//            pickDateBtn.setText(selectedDate);
//        }, years, month, day);
//        datePickerDialog.show();
//    }
//}

//
//package com.example.android.barbera;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.ActionBar;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//
//import android.annotation.SuppressLint;
//import android.app.DatePickerDialog;
//import android.app.TimePickerDialog;
//import android.content.Intent;
//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.Calendar;
//import java.util.HashMap;
//import java.util.Locale;
//
//public class Appoint extends AppCompatActivity {
//
//    Button pickTimeBtn, pickDateBtn;
//    TextView confirm_text;
//    RadioGroup radioGroup1;
//    RadioButton styledRadioButton, randomRadioButton;
//    String selectedRadioButtonText1 = "";
//    String selectedTime = "";
//    String costs = "8$";
//    String selectedDate = "";
//    FirebaseAuth mAuth;
//    Toolbar toolbar;
//    FirebaseDatabase database;
//    DatabaseReference databaseReference;
//
//    @SuppressLint("MissingInflatedId")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_appoint);
//
//        toolbar = findViewById(R.id.toolbar_id);
//        setSupportActionBar(toolbar);
//        ActionBar actionBar = getSupportActionBar();
//        assert actionBar != null;
//        actionBar.setDisplayHomeAsUpEnabled(true);
//
//        pickTimeBtn = findViewById(R.id.time_picker_button_id);
//        pickDateBtn = findViewById(R.id.date_picker_button_id);
//        confirm_text = findViewById(R.id.confirm_id);
//
//        styledRadioButton = findViewById(R.id.styled_radio_id);
//        randomRadioButton = findViewById(R.id.random_radio_id);
//
//        radioGroup1 = findViewById(R.id.WoringRadioGrp1_id);
//
//        // Initialize Firebase components
//        mAuth = FirebaseAuth.getInstance();
//        database = FirebaseDatabase.getInstance();
//        databaseReference = database.getReference("users");
//
//        radioGroup1.setOnCheckedChangeListener((group, checkedId) -> {
//            if (checkedId == R.id.styled_radio_id)
//                selectedRadioButtonText1 = styledRadioButton.getText().toString();
//            else if (checkedId == R.id.random_radio_id)
//                selectedRadioButtonText1 = randomRadioButton.getText().toString();
//        });
//
//        pickTimeBtn.setOnClickListener(v -> {
//            final Calendar c = Calendar.getInstance();
//            int hour = c.get(Calendar.HOUR_OF_DAY), minute = c.get(Calendar.MINUTE);
//
//            TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute1) -> {
//                String amOrPm;
//                if (hourOfDay < 12) {
//                    amOrPm = "AM";
//                } else {
//                    amOrPm = "PM";
//                    if (hourOfDay > 12) {
//                        hourOfDay -= 12;
//                    }
//                }
//                selectedTime = String.format(Locale.getDefault(), "%02d:%02d %s", hourOfDay, minute1, amOrPm);
//                pickTimeBtn.setText(selectedTime);
//            }, hour, minute, true); // Use true to show AM/PM format
//
//            timePickerDialog.show();
//        });
//
//        // Add an OnClickListener for the date picker button
//        pickDateBtn.setOnClickListener(v -> showDatePickerDialog());
//
//        confirm_text.setOnClickListener(v1 -> {
//            if (selectedTime.isEmpty() || selectedRadioButtonText1.isEmpty() || selectedDate.isEmpty()) {
//                Toast.makeText(Appoint.this, "Please select buttons, date, and time", Toast.LENGTH_SHORT).show();
//            } else {
//                FirebaseUser user = mAuth.getCurrentUser();
//                String userEmail = user.getEmail();
//
//                DatabaseReference userAppointmentsRef = database.getReference("user_appointments").child(user.getUid()).push();
//                userAppointmentsRef.child("selectedOption1").setValue(selectedRadioButtonText1);
//                userAppointmentsRef.child("timestamp").setValue(selectedTime);
//                userAppointmentsRef.child("date").setValue(selectedDate);
//                userAppointmentsRef.child("Cost").setValue(costs);
//
//                // Provide feedback to the user
//                Toast.makeText(this, "Appointment Confirmed", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//
//    // Add a method to display the date picker dialog
//    private void showDatePickerDialog() {
//        Calendar c = Calendar.getInstance();
//        int years = c.get(Calendar.YEAR);
//        int month = c.get(Calendar.MONTH);
//        int day = c.get(Calendar.DAY_OF_MONTH);
//
//        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
//            // Handle the selected date (e.g., store it or display it)
//            selectedDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
//            // Handle the selected date, e.g., store it or display it
//            pickDateBtn.setText(selectedDate);
//        }, years, month, day);
//        datePickerDialog.show();
//    }
//}



package com.example.android.barbera;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class Appoint extends AppCompatActivity {

    Button pickTimeBtn,pickTimeBtn1,pickTimeBtn2, pickDateBtn,pickDateBtn1,pickDateBtn2;
    TextView confirm_text,confirm_text1,confirm_text2;
    RadioGroup radioGroup1,radioGroup2,radioGroup3;
    RadioButton styledRadioButton,styledRadioButton1,styledRadioButton2, randomRadioButton,randomRadioButton1,randomRadioButton2;
    String selectedRadioButtonText1 = "";
    String selectedTime = "";
    String costs = "";
    String selectedDate = "";
    FirebaseAuth mAuth;
    Toolbar toolbar;
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoint);

        toolbar = findViewById(R.id.toolbar_id);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("users");

        pickTimeBtn = findViewById(R.id.time_picker_button_id);
        pickTimeBtn1 = findViewById(R.id.time_picker_button_id1);
        pickTimeBtn2 = findViewById(R.id.time_picker_button_id2);

        pickDateBtn = findViewById(R.id.date_picker_button_id);
        pickDateBtn1 = findViewById(R.id.date_picker_button_id1);
        pickDateBtn2 = findViewById(R.id.date_picker_button_id2);

        confirm_text = findViewById(R.id.confirm_id);
        confirm_text1 = findViewById(R.id.confirm_id1);
        confirm_text2 = findViewById(R.id.confirm_id2);

        styledRadioButton = findViewById(R.id.styled_radio_id);
        styledRadioButton1 = findViewById(R.id.styled_radio_id1);
        styledRadioButton2 = findViewById(R.id.styled_radio_id2);

        randomRadioButton = findViewById(R.id.random_radio_id);
        randomRadioButton1 = findViewById(R.id.random_radio_id1);
        randomRadioButton2 = findViewById(R.id.random_radio_id2);

        radioGroup1 = findViewById(R.id.WoringRadioGrp1_id);
        radioGroup2 = findViewById(R.id.WoringRadioGrp2_id);
        radioGroup3 = findViewById(R.id.WoringRadioGrp3_id);

        radioGroup1.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.styled_radio_id)
                selectedRadioButtonText1 = styledRadioButton.getText().toString();
            else if (checkedId == R.id.random_radio_id)
                selectedRadioButtonText1 = randomRadioButton.getText().toString();
        });
        radioGroup2.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.styled_radio_id1)
                selectedRadioButtonText1 = styledRadioButton1.getText().toString();
            else if (checkedId == R.id.random_radio_id1)
                selectedRadioButtonText1 = randomRadioButton1.getText().toString();
        });
        radioGroup3.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.styled_radio_id2)
                selectedRadioButtonText1 = styledRadioButton2.getText().toString();
            else if (checkedId == R.id.random_radio_id2)
                selectedRadioButtonText1 = randomRadioButton2.getText().toString();
        });

        pickTimeBtn.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY), minute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute1) -> {
                String amOrPm;
                if (hourOfDay < 12) {
                    amOrPm = "AM";
                } else {
                    amOrPm = "PM";
                    if (hourOfDay > 12) {
                        hourOfDay -= 12;
                    }
                }
                selectedTime = String.format(Locale.getDefault(), "%02d:%02d %s", hourOfDay, minute1, amOrPm);
                pickTimeBtn.setText(selectedTime);
            }, hour, minute, true); // Use true to show AM/PM format

            timePickerDialog.show();
        });
        pickTimeBtn1.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY), minute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute1) -> {
                String amOrPm;
                if (hourOfDay < 12) {
                    amOrPm = "AM";
                } else {
                    amOrPm = "PM";
                    if (hourOfDay > 12) {
                        hourOfDay -= 12;
                    }
                }
                selectedTime = String.format(Locale.getDefault(), "%02d:%02d %s", hourOfDay, minute1, amOrPm);
                pickTimeBtn1.setText(selectedTime);
            }, hour, minute, true);
            timePickerDialog.show();
        });
        pickTimeBtn2.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY), minute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute1) -> {
                String amOrPm;
                if (hourOfDay < 12) {
                    amOrPm = "AM";
                } else {
                    amOrPm = "PM";
                    if (hourOfDay > 12) {
                        hourOfDay -= 12;
                    }
                }
                selectedTime = String.format(Locale.getDefault(), "%02d:%02d %s", hourOfDay, minute1, amOrPm);
                pickTimeBtn2.setText(selectedTime);
            }, hour, minute, true); // Use true to show AM/PM format

            timePickerDialog.show();
        });

        pickDateBtn.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            int years = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(Appoint.this, (view, year, monthOfYear, dayOfMonth) -> {
                // Handle the selected date (e.g., store it or display it)
                selectedDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                // Handle the selected date, e.g., store it or display it
                pickDateBtn.setText(selectedDate);
            }, years, month, day);
            datePickerDialog.show();
        });
        pickDateBtn1.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            int years = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(Appoint.this, (view, year, monthOfYear, dayOfMonth) -> {
                // Handle the selected date (e.g., store it or display it)
                selectedDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                // Handle the selected date, e.g., store it or display it
                pickDateBtn1.setText(selectedDate);
            }, years, month, day);
            datePickerDialog.show();
        });
        pickDateBtn2.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            int years = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(Appoint.this, (view, year, monthOfYear, dayOfMonth) -> {
                // Handle the selected date (e.g., store it or display it)
                selectedDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                // Handle the selected date, e.g., store it or display it
                pickDateBtn2.setText(selectedDate);
            }, years, month, day);
            datePickerDialog.show();
        });

        confirm_text.setOnClickListener(v1 -> {
            costs = "8$";
            please_confirm();
        });
        confirm_text1.setOnClickListener(v1 -> {
            costs = "10$";
            please_confirm();
        });
        confirm_text2.setOnClickListener(v1 -> {
            costs = "15$";
            please_confirm();
        });
    }

    private void please_confirm() {

        if (selectedTime.isEmpty() || selectedRadioButtonText1.isEmpty() || selectedDate.isEmpty()) {
            Toast.makeText(Appoint.this, "Please select buttons, date, and time", Toast.LENGTH_SHORT).show();
        } else {
            FirebaseUser user = mAuth.getCurrentUser();
            String userEmail = user.getEmail();

            DatabaseReference userAppointmentsRef = database.getReference("user_appointments").child(user.getUid()).push();
            userAppointmentsRef.child("selectedOption1").setValue(selectedRadioButtonText1);
            userAppointmentsRef.child("timestamp").setValue(selectedTime);
            userAppointmentsRef.child("date").setValue(selectedDate);
            userAppointmentsRef.child("Cost").setValue(costs);

            Toast.makeText(this, "Appointment Confirmed", Toast.LENGTH_SHORT).show();
        }
    }
}

