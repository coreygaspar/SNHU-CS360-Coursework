package com.example.coreygaspar_eventtrackingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddEventActivity extends AppCompatActivity {

    EditText inputName, inputDescription, inputDate, inputTime;
    Button btnSave;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        // input fields
        inputName = findViewById(R.id.inputName);
        inputDescription = findViewById(R.id.inputDescription);
        inputDate = findViewById(R.id.inputDate);
        inputTime = findViewById(R.id.inputTime);

        // save button
        btnSave = findViewById(R.id.btnSave);

        // database helper
        db = new DatabaseHelper(this);

        // save event click
        btnSave.setOnClickListener(v -> {

            String name = inputName.getText().toString().trim();
            String description = inputDescription.getText().toString().trim();
            String date = inputDate.getText().toString().trim();
            String time = inputTime.getText().toString().trim();

            // validate inputs
            if (name.isEmpty() || description.isEmpty() || date.isEmpty() || time.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // insert event into database
            boolean inserted = db.insertEvent(name, description, date, time);

            // result check
            if (inserted) {
                Toast.makeText(this, "Event added", Toast.LENGTH_SHORT).show();
                finish(); // return to main screen
            } else {
                Toast.makeText(this, "Failed to add event", Toast.LENGTH_SHORT).show();
            }
        });
    }
}