package com.example.buyfishonline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.MultiInstanceInvalidationService;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.drm.DrmStore;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buyfishonline.DB.AppDatabase;
import com.example.buyfishonline.DB.UserDAO;

import java.util.List;

public class AdminActivity extends AppCompatActivity {

    private TextView mUsernameTV;
    private Button mRemoveUser;
    private Button mAddUser;

    private TextView mEnterFishTV;
    private TextView mEnterPriceTV;
    private Button mRemoveFish;
    private Button mAddFish;

    private UserDAO mUserDAO;

    private List<User> mUserList;
    private List<Fish> mFishList;
    private TextView mPasswordTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        getDatabase();
        wireDisplay();
    }

    public void getDatabase(){
        mUserDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .UserDAO();
    }

    private void wireDisplay() {
        mUsernameTV = findViewById(R.id.adminPageUsername);
        mPasswordTV = findViewById(R.id.adminPagePassword);

        mRemoveUser = findViewById(R.id.removeUser);
        mRemoveUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeUser();
            }
        });

        mAddUser = findViewById(R.id.addUser);
        mAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser();
            }
        });

        mEnterFishTV = findViewById(R.id.fishName);
        mEnterPriceTV = findViewById(R.id.fishPrice);

        mRemoveFish = findViewById(R.id.removeFish);
        mRemoveFish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeFish();
            }
        });

        mAddFish = findViewById(R.id.addFish);
        mAddFish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFish();
            }
        });
    }

    private void addFish() {
        mUserDAO.insert(new Fish(getFishName(), 1, getFishPrice()));
        Toast.makeText(this, "Username " + getUsername() + " already taken", Toast.LENGTH_SHORT).show();
    }

    private double getFishPrice() {
        return Double.parseDouble(mEnterPriceTV.getText().toString());
    }

    private void removeFish() {
        if(validateFish()){
            mUserDAO.delete(mUserDAO.getFishByFishName(getFishName()));
            Toast.makeText(this, getFishName() + " removed successfully", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, getFishName() + " not in database.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateFish() {
        mFishList = mUserDAO.getAllFish();

        for(Fish fish : mFishList){
            if(getFishName().equals(fish.getFishName())){
                return true;
            }
        }
        return false;
    }

    private String getFishName() {
        return mEnterFishTV.getText().toString();
    }

    private void addUser() {
        if(validateUser()){
            Toast.makeText(this, "Username " + getUsername() + " already taken", Toast.LENGTH_SHORT).show();
        }else{
            User user = new User(getUsername(), mPasswordTV.getText().toString(), false);
            mUserDAO.insert(user);
            Toast.makeText(this, getUsername() + " account created!", Toast.LENGTH_SHORT).show();
        }
    }

    private String getUsername() {
        return mUsernameTV.getText().toString();
    }

    private void removeUser() {
        if(validateUser()){
            if(mUserDAO.getUserByUsername(mUsernameTV.getText().toString()).isAdmin()){
                Toast.makeText(this, "Cannot remove admin", Toast.LENGTH_SHORT).show();
            }else{
                mUserDAO.delete(mUserDAO.getUserByUsername(mUsernameTV.getText().toString()));
                Toast.makeText(this, mUsernameTV.getText().toString() + " deleted!",
                        Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this,
                    mUsernameTV.getText().toString() + " is not in the database.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateUser() {
        mUserList = mUserDAO.getAllUsers();

        for(User user : mUserList){
            if(user.getUsername().equals(mUsernameTV.getText().toString())){
                return true;
            }
        }
        return false;
    }

    public static Intent intentFactory(Context context){
        return new Intent(context, AdminActivity.class);
    }
}