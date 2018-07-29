package com.example.rayhanshikder.readandsendtexts;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import static android.widget.Toast.*;

//auth HOqX+zMXrPk29AYD // for authentication in netcat
public class MainActivity extends AppCompatActivity {

    private int MY_PERMISSIONS_REQUEST_SMS_RECEIVE = 10;
    private int MY_PERMISSIONS_REQUEST_SMS_SEND = 11;
    public static final String OTP_REGEX = "[0-9]{1,6}";
    private static Context context;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("Initial welcome message","welcome message from my app");
        makeText(MainActivity.this,"Message: "+"this is toast message", LENGTH_LONG).show();
        super.onCreate(savedInstanceState);
        MainActivity.context = getApplicationContext();
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.SEND_SMS},
                MY_PERMISSIONS_REQUEST_SMS_SEND);



    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_SMS_RECEIVE) {
            // YES!!
            Log.i("TAG", "MY_PERMISSIONS_REQUEST_SMS_RECEIVE --> YES");
        }

        if (requestCode == MY_PERMISSIONS_REQUEST_SMS_SEND) {
            // YES!!
            Log.i("TAG", "MY_PERMISSIONS_REQUEST_SMS_SEND --> YES");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECEIVE_SMS},
                    MY_PERMISSIONS_REQUEST_SMS_RECEIVE);
        }
    }

    public static Context getAppContext() {
        return MainActivity.context;
    }
}
