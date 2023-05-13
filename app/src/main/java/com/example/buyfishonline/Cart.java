package com.example.buyfishonline;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.buyfishonline.DB.AppDatabase;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = AppDatabase.CART_TABLE)
public class Cart {

    @PrimaryKey(autoGenerate = true)
    private int mCartId;

    private String mUsername;

    private String mFishList;

    public Cart(String username, String fishList) {
        mUsername = username;
        mFishList = fishList;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public int getCartId() {
        return mCartId;
    }

    public void setCartId(int cartId) {
        mCartId = cartId;
    }

    public String getFishList() {
        return mFishList;
    }

    public void setFishList(String fishList) {
        mFishList = fishList;
    }
}
