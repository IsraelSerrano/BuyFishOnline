package com.example.buyfishonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OnStartActivity extends AppCompatActivity {

    private Button signInButton;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        wireDisplay();
    }

    private void wireDisplay(){
        signInButton = findViewById(R.id.signInButton);
        signInButton = findViewById(R.id.signUpButton);


    }

}