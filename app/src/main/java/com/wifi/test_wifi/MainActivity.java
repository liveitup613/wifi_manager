package com.wifi.test_wifi;

import android.Manifest;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.thanosfisherman.wifiutils.WifiUtils;
import com.thanosfisherman.wifiutils.wifiConnect.ConnectionSuccessListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 555);
        final Button button = findViewById(R.id.button);
        WifiUtils.enableLog(true);
        //TODO: CHECK IF LOCATION SERVICES ARE ON
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectWithWpa();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void connectWithWps() {
        WifiUtils.withContext(getApplicationContext()).connectWithWps("TP-LINK_2802", "51362485").onConnectionWpsResult(this::checkResult).start();
    }

    private void connectWithWpa() {
        String ote = "TP-LINK_2082";
        String otePass = "1122334455";

        WifiUtils.withContext(getApplicationContext())
                .connectWith(ote, otePass)
                .setTimeout(40000)
                .onConnectionResult(this::checkResult)
                .start();
    }

    private void enableWifi() {
        WifiUtils.withContext(getApplicationContext()).enableWifi(this::checkResult);
        //or without the callback
        //WifiUtils.withContext(getApplicationContext()).enableWifi();
    }

    private void checkResult(boolean isSuccess) {
        if (isSuccess)
            Toast.makeText(MainActivity.this, "SUCCESS!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(MainActivity.this, "EPIC FAIL!", Toast.LENGTH_SHORT).show();
    }
}
