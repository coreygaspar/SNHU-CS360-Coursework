package com.example.coreygaspar_eventtrackingapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class SmsActivity extends Activity {

    Button enableBtn, backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        // buttons
        enableBtn = findViewById(R.id.btnEnableSms);
        backBtn = findViewById(R.id.btnBack);

        // enable sms permission
        enableBtn.setOnClickListener(v -> {

            // check permission
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.SEND_SMS)
                    != PackageManager.PERMISSION_GRANTED) {

                // request permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS}, 1);

            } else {
                Toast.makeText(this, "SMS already enabled", Toast.LENGTH_SHORT).show();
                goToMain();
            }
        });

        // back to main screen
        backBtn.setOnClickListener(v -> finish());
    }

    // return to main activity
    private void goToMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    // permission result handler
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {

            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "SMS Permission Granted", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "SMS Permission Denied", Toast.LENGTH_SHORT).show();
            }

            goToMain();
        }
    }
}