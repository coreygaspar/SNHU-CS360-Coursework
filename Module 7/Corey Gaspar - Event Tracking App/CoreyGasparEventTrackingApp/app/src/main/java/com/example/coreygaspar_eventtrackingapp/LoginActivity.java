package com.example.coreygaspar_eventtrackingapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText usernameInput, passwordInput;
    Button loginBtn, createBtn;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // input fields
        usernameInput = findViewById(R.id.username);
        passwordInput = findViewById(R.id.password);

        // buttons
        loginBtn = findViewById(R.id.signIn);
        createBtn = findViewById(R.id.createAccount);

        // database helper
        db = new DatabaseHelper(this);

        // login button click
        loginBtn.setOnClickListener(v -> {

            String user = usernameInput.getText().toString().trim();
            String pass = passwordInput.getText().toString().trim();

            // validate input
            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Enter username and password", Toast.LENGTH_SHORT).show();
                return;
            }

            // check user in database
            Cursor cursor = db.getReadableDatabase().rawQuery(
                    "SELECT * FROM users WHERE username=? AND password=?",
                    new String[]{user, pass}
            );

            // login success or failure
            if (cursor.moveToFirst()) {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
            }

            cursor.close();
        });

        // create account button click
        createBtn.setOnClickListener(v -> {

            String user = usernameInput.getText().toString().trim();
            String pass = passwordInput.getText().toString().trim();

            // validate input
            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Enter username and password", Toast.LENGTH_SHORT).show();
                return;
            }

            // insert new user
            ContentValues values = new ContentValues();
            values.put("username", user);
            values.put("password", pass);

            long result = db.getWritableDatabase().insert("users", null, values);

            // result check
            if (result == -1) {
                Toast.makeText(this, "User already exists", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Account created", Toast.LENGTH_SHORT).show();
            }
        });
    }
}