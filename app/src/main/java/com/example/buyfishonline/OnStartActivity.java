package com.example.buyfishonline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.buyfishonline.DB.AppDatabase;
import com.example.buyfishonline.DB.UserDAO;

import java.util.List;

public class OnStartActivity extends AppCompatActivity {

    private Button signInButton;
    private Button signUpButton;

    private UserDAO mUserDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        wireDisplay();
    }

    private void wireDisplay(){
        signInButton = findViewById(R.id.signInButton);
        signUpButton = findViewById(R.id.signUpButton);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(SignInActivity.intentFactory(getApplicationContext()));
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(SignUpActivity.intentFactory(getApplicationContext()));
            }
        });
    }

    private void getDatabase() {
        mUserDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .UserDAO();

    }

    public static Intent intentFactory(Context context){
        return new Intent(context, OnStartActivity.class);
    }
}