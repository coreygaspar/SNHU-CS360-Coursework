package com.example.coreygaspar_eventtrackingapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    DatabaseHelper db;
    ArrayList<String> eventList;

    Button addBtn;
    Button smsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // views
        gridView = findViewById(R.id.gridView);
        addBtn = findViewById(R.id.btnAddEvent);
        smsBtn = findViewById(R.id.btnSms);

        // database
        db = new DatabaseHelper(this);

        // open add event screen
        addBtn.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, AddEventActivity.class))
        );

        // open sms screen
        smsBtn.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, SmsActivity.class))
        );

        // open edit/delete dialog on item click
        gridView.setOnItemClickListener((parent, view, position, id) ->
                showEditDialog(position)
        );

        loadEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadEvents();
    }

    // edit or delete event dialog
    private void showEditDialog(int position) {

        Cursor cursor = db.getAllEvents();

        try {
            if (!cursor.moveToPosition(position)) return;

            int eventId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
            String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
            String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));

            // create edit layout
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);

            EditText nameInput = new EditText(this);
            nameInput.setText(name);

            EditText descInput = new EditText(this);
            descInput.setText(description);

            EditText dateInput = new EditText(this);
            dateInput.setText(date);

            EditText timeInput = new EditText(this);
            timeInput.setText(time);

            layout.addView(nameInput);
            layout.addView(descInput);
            layout.addView(dateInput);
            layout.addView(timeInput);

            new AlertDialog.Builder(this)
                    .setTitle("Edit Event")
                    .setView(layout)

                    // update event
                    .setPositiveButton("Update", (dialog, which) -> {

                        db.updateEvent(
                                eventId,
                                nameInput.getText().toString(),
                                descInput.getText().toString(),
                                dateInput.getText().toString(),
                                timeInput.getText().toString()
                        );

                        loadEvents();
                    })

                    // delete event
                    .setNeutralButton("Delete", (dialog, which) -> {
                        db.deleteEvent(eventId);
                        loadEvents();
                    })

                    .setNegativeButton("Cancel", null)
                    .show();

        } finally {
            cursor.close();
        }
    }

    // load events into grid
    private void loadEvents() {

        eventList = new ArrayList<>();
        Cursor cursor = db.getAllEvents();

        try {

            if (cursor.getCount() == 0) {
                eventList.add("No events yet");
            } else {

                while (cursor.moveToNext()) {

                    String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                    String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                    String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                    String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));

                    eventList.add(name + "\n" + description + "\n" + date + " " + time);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            eventList.add("Error loading events");
        } finally {
            cursor.close();
        }

        // bind data to grid
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                eventList
        );

        gridView.setAdapter(adapter);
    }
}