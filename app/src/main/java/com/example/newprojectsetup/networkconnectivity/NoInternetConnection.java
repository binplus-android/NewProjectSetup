package com.example.newprojectsetup.networkconnectivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newprojectsetup.Activity.Splash_screen;
import com.example.newprojectsetup.R;


public class NoInternetConnection extends AppCompatActivity {
    ImageView mCheckConnection;
    TextView tryagain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.actvity_no_internet_connection);
        mCheckConnection =  findViewById(R.id.no_internet_connection);
        tryagain =  findViewById(R.id.tryagain);
        tryagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkConnection.connectionChecking(getApplicationContext())) {
                    Intent intent = new Intent(NoInternetConnection.this, Splash_screen.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK |
                            Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(),"Check your connection.Try again!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}