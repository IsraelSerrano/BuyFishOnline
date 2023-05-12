package com.example.buyfishonline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.buyfishonline.DB.AppDatabase;
import com.example.buyfishonline.DB.UserDAO;

import kotlinx.coroutines.MainCoroutineDispatcher;

public class SignUpActivity extends AppCompatActivity {

    private EditText mUsernameString;
    private EditText mPasswordString;

    private UserDAO mUserDAO;
    private Button mSignUpButton;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getDatabase();
        wireDisplay();

    }


    private void wireDisplay(){
        mUsernameString = findViewById(R.id.editTextTextPersonName);
        mPasswordString = findViewById(R.id.editTextTextPassword);

        mSignUpButton = findViewById(R.id.SignUpButton);

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkForUserInDatabase()){
                    Toast.makeText(getApplicationContext(), "Account Created!", Toast.LENGTH_SHORT).show();
                    startActivity(LandingPageActivity.intentFactory(getApplicationContext(), mUser.getUserId()));
                }
            }
        });
    }

    public void getDatabase(){
        mUserDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .UserDAO();
    }

    private boolean checkForUserInDatabase() {
        mUser = mUserDAO.getUserByUsername(mUsernameString.getText().toString());
        if(mUser != null){
            Toast.makeText(this, "Username " + mUser.getUsername() + " already in use.", Toast.LENGTH_SHORT).show();
            return true;
        }
        mUserDAO.insert(new User(mUsernameString.getText().toString(), mPasswordString.getText().toString(), false));
        mUser = mUserDAO.getUserByUsername(mUsernameString.getText().toString());
        return false;
    }


    public static Intent intentFactory(Context context){
        return new Intent(context, SignUpActivity.class);
    }
}