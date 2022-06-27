package com.example.newprojectsetup.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.example.newprojectsetup.Common.Common;
import com.example.newprojectsetup.Common.Session_management;
import com.example.newprojectsetup.R;
import com.example.newprojectsetup.Utli.ConnectivityReceiver;
import com.example.newprojectsetup.networkconnectivity.NoInternetConnection;

public class Splash_screen extends AppCompatActivity {

    Common common;

    Session_management sessionManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ///////////////////////////hide status bar///////////
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ////////////// hide status bar/////////

        common = new Common(Splash_screen.this);
        sessionManagement = new Session_management(Splash_screen.this);
        SharedPreferences preferences = getSharedPreferences("push", MODE_PRIVATE);


        checkAndGo();
    }

    public void checkAndGo() {
        if (isOnline()) {
            int SPLASH_TIME_OUT = 6000;
            new Handler().postDelayed(new Runnable() {

                                          @Override
                                          public void run() {
//                    if (Session_management.getIsLogin()) {

                                              Intent i = new Intent(Splash_screen.this, MainActivity.class);
                                              startActivity(i);

//                    } else {

//                        Intent i = new Intent(Splash_screen.this, LoginActivity.class);
//                        startActivity(i);

                                              finish();
//                    }
//                    finish();

                                          }
                                      },
                    SPLASH_TIME_OUT);
        } else {
            if (ConnectivityReceiver.isConnected()) {
                checkAndGo();
            } else {
                Intent intent = new Intent(Splash_screen.this, NoInternetConnection.class);
                startActivity(intent);

            }
        }


    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = null;
        if (cm != null) {
            netInfo = cm.getActiveNetworkInfo();
        }
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void onStart() {
        super.onStart();
    }
}
