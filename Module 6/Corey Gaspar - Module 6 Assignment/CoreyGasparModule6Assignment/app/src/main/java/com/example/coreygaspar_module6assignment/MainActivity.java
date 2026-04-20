package com.example.coreygaspar_module6assignment;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //
    SensorManager sensor;
    TextView data;
    List<Sensor> myList;

    SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float[] sensor_data = sensorEvent.values;
            data.setText("x: " + sensor_data[0] + "\ny: " + sensor_data[1] + "\nz: " + sensor_data[2]);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        data = (TextView)findViewById(R.id.sensorData);
        sensor = (SensorManager)getSystemService(SENSOR_SERVICE);
        myList = sensor.getSensorList(Sensor.TYPE_ACCELEROMETER);

        if(myList.size()!=0){
            sensor.registerListener(listener, (Sensor)myList.get(0), SensorManager.SENSOR_DELAY_NORMAL);
        }else{
            Toast.makeText(getBaseContext(), "No accelerometer detected", Toast.LENGTH_LONG).show();
        }


    }

    @Override
    protected void onStop() {
        super.onStop();
        if(myList.size()!=0)
            sensor.unregisterListener(listener);
    }
}