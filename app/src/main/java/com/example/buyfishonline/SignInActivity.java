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

import java.util.List;

public class SignInActivity extends AppCompatActivity {
    private EditText mUsername;
    private EditText mPassword;
    private Button mButton;

    private UserDAO mUserDAO;

    private String mUsernameString;
    private String mPasswordString;
    private User mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        wireDisplay();

        getDatabase();
    }

    private void getDatabase() {
        mUserDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .UserDAO();
        List<User> users = mUserDAO.getAllUsers();
        if(users.isEmpty()){
            User defaultUser = new User("Iz", "Iz123");
            User altUser = new User("a", "b");
            mUserDAO.insert(defaultUser, altUser);
        }
    }

    private void wireDisplay(){
        mUsername = findViewById(R.id.editTextTextPersonName);
        mPassword = findViewById(R.id.editTextTextPassword);

        mButton = findViewById(R.id.SignIn);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getValuesFromDisplay();
                if(checkForUserInDatabase()){
                    if(!validatePassword()){
                        Toast.makeText(SignInActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                    }else{
                        Intent intent = LandingPageActivity.intentFactory(getApplicationContext(), mUser.getUserId());
                        startActivity(intent);
                    }
                }
            }
        });
    }

    private boolean validatePassword() {
        return mUser.getPassword().equals(mPasswordString);
    }

    private boolean checkForUserInDatabase() {
        mUser = mUserDAO.getUserByUsername(mUsernameString);
        if(mUser == null){
            Toast.makeText(this, "no user " + mUsernameString + " found", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void getValuesFromDisplay() {
        mUsernameString = mUsername.getText().toString();
        mPasswordString = mPassword.getText().toString();
    }
    public static Intent intentFactory(Context context){
        return new Intent(context, SignInActivity.class);
    }
}