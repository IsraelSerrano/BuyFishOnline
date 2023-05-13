package com.example.buyfishonline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.lights.LightsManager;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buyfishonline.DB.AppDatabase;
import com.example.buyfishonline.DB.UserDAO;

import java.util.ArrayList;
import java.util.List;

public class BrowseActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.buyfishonline.userIdKey";
    private static final String PREFERENCES_KEY = "com.example.buyfishonline.preferencesKey";

    private TextView mMainDisplay;

    private EditText mEnterFishName;
    private Button mAddToCartButton;

    private SharedPreferences mPreferences = null;

    private User mUser;
    private int mUserId;
    private UserDAO mUserDAO;
    private List<Fish> mFishList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        wireDisplay();
        getDatabase();
        addFish();

        getPrefs();

        refreshDisplay();
    }





    private boolean validateFish() {
        String fishInQuestion = mEnterFishName.getText().toString();
        for(Fish fish : mFishList){
            if(fishInQuestion.equals(fish.getFishName()) && fish.getFishCount() > 0){
                return true;
            }
        }
        Toast.makeText(getApplicationContext(), fishInQuestion + " not available", Toast.LENGTH_SHORT).show();
        return false;
    }


    private void getPrefs() {
        mPreferences = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
    }

    private void addFish() {
        mFishList = mUserDAO.getAllFish();
        if(mFishList.isEmpty()){
            mUserDAO.insert(new Fish("Trout", 20, 5.99));
            mUserDAO.insert(new Fish("Salmon", 3, 10.99));
            mUserDAO.insert(new Fish("Tilapia", 1, 6.00));
            mUserDAO.insert(new Fish("Sturgeon", 0, 55.50));
            mUserDAO.insert(new Fish("Sword Fish", 5, 180.75));
        }
    }

    private void wireDisplay(){
        mMainDisplay = findViewById(R.id.FishMarketMainDisplay);
        mEnterFishName = findViewById(R.id.enterFishName);

        mAddToCartButton = findViewById(R.id.addToCartButton);
        mMainDisplay.setMovementMethod(new ScrollingMovementMethod());

        mAddToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateFish()){
                    addToCart();
                }
            }
        });
    }

    private void addToCart() {
        mUser = mUserDAO.getUserByUserId(getIntent().getIntExtra(USER_ID_KEY, -1));

        if(mUserDAO.getCartByUsername(mUser.getUsername()) == null){
            mUserDAO.insert(new Cart(mUser.getUsername(), ""));
        }

        Cart cart = mUserDAO.getCartByUsername(mUser.getUsername());
        mUserDAO.update(mEnterFishName.getText().toString(),
                mUserDAO.getFishByFishName(mEnterFishName.getText().toString()).getFishCount() - 1);
        mUserDAO.update(mUser.getUsername(), cart.getFishList() + mEnterFishName.getText().toString() + ",");
        Toast.makeText(this, mEnterFishName.getText().toString() + " added to cart", Toast.LENGTH_SHORT).show();
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

    public static Intent intentFactory(Context context, int userId){
        Intent intent = new Intent(context,BrowseActivity.class);
        intent.putExtra(USER_ID_KEY, userId);

        return intent;
    }

}