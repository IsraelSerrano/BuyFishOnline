package com.example.buyfishonline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.hardware.lights.LightsManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buyfishonline.DB.AppDatabase;
import com.example.buyfishonline.DB.UserDAO;

import java.util.List;

public class BrowseActivity extends AppCompatActivity {

    private TextView mMainDisplay;

    private EditText mEnterFishName;
    private Button mAddToCartButton;

    private UserDAO mUserDAO;
    private List<Fish> mFishList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        wireDisplay();
        getDatabase();
        addFish();
        refreshDisplay();
    }

    private void addFish() {
        mFishList = mUserDAO.getAllFish();
        if(mFishList.isEmpty()){
            mUserDAO.insert(new Fish("Trout", 20));
            mUserDAO.insert(new Fish("Salmon", 3));
            mUserDAO.insert(new Fish("Tilapia", 1));
            mUserDAO.insert(new Fish("Sturgeon", 0));
            mUserDAO.insert(new Fish("Sword Fish", 5));
        }
    }

    private void wireDisplay(){
        mMainDisplay = findViewById(R.id.FishMarketMainDisplay);
        mEnterFishName = findViewById(R.id.enterFishName);

        mAddToCartButton = findViewById(R.id.addToCartButton);

        mAddToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void refreshDisplay(){
        mFishList = mUserDAO.getAllFish();

        if(!mFishList.isEmpty()){
            StringBuilder sb = new StringBuilder();
            for (Fish fish : mFishList){
                sb.append(fish.toString());
            }

            mMainDisplay.setText(sb.toString());
        }
    }

    public void getDatabase(){
        mUserDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .UserDAO();
    }

    public static Intent intentFactory(Context context){
        return new Intent(context, BrowseActivity.class);
    }

}