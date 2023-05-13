package com.example.buyfishonline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buyfishonline.DB.AppDatabase;
import com.example.buyfishonline.DB.UserDAO;

public class CartActivity extends AppCompatActivity {

    private TextView mMainDisplay;

    private Button mCheckOut;

    private User mUser;
    private UserDAO mUserDAO;

    private Cart mCart;
    private String [] cart;

    private static final String USER_ID_KEY = "com.example.buyfishonline.userIdKey";
    private static final String PREFERENCES_KEY = "com.example.buyfishonline.preferencesKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        mMainDisplay = findViewById(R.id.UserCart);

        mMainDisplay.setMovementMethod(new ScrollingMovementMethod());
        getDatabase();
        getUser();
        populateDisplay();

        mCheckOut = findViewById(R.id.CheckOut);

        mCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmPurchase();
            }
        });
    }

    private void confirmPurchase() {
        mUserDAO.update(mUser.getUsername(), "");
        Toast.makeText(this, "Order Confirmed!", Toast.LENGTH_SHORT).show();
        startActivity(LandingPageActivity.intentFactory(getApplicationContext(), mUser.getUserId()));
    }

    private void populateDisplay() {
        mCart = mUserDAO.getCartByUsername(mUser.getUsername());
        String[] cart =  mCart.getFishList().split(",");
        if(cart.length > 0 && !cart[0].equals("")){
            StringBuilder sb = new StringBuilder();
            for(String fish : cart){
                sb.append(fish);
                sb.append(" $");
                sb.append(mUserDAO.getFishByFishName(fish).getFishPrice());
                sb.append("\n");
            }
            mMainDisplay.setText(sb.toString());
        }

    }

    private void getDatabase() {
        mUserDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .UserDAO();
    }

    private void getUser() {
        mUser = mUserDAO.getUserByUserId(getIntent().getIntExtra(USER_ID_KEY, -1));
    }

    public static Intent intentFactory(Context context, int userId){
        Intent intent = new Intent(context,CartActivity.class);
        intent.putExtra(USER_ID_KEY, userId);

        return intent;
    }
}