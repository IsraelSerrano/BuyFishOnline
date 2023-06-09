package com.example.buyfishonline.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.buyfishonline.Cart;
import com.example.buyfishonline.Fish;
import com.example.buyfishonline.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    void insert(User... users);

    @Update
    void update(User... users);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE)
    List<User> getAllUsers();

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE mUsername = :username ")
    User getUserByUsername(String username);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE mUserId = :userId ")
    User getUserByUserId(int userId);

    @Insert
    void insert(Fish... fish);

    @Update
    void update(Fish... fish);

    @Delete
    void delete(Fish fish);

    @Query("SELECT * FROM " + AppDatabase.FISH_TABLE)
    List<Fish> getAllFish();

    @Query("SELECT * FROM " + AppDatabase.FISH_TABLE + " WHERE mFishName = :fishName ")
    Fish getFishByFishName(String fishName);
    @Query("UPDATE fish_table SET mFishCount = :fishCount WHERE mFishName =:fishName")
    void update(String fishName, int fishCount);

    @Insert
    void insert(Cart... carts);

    @Update
    void update(Cart... cart);

    @Delete
    void delete(Cart cart);

    @Query("SELECT * FROM " + AppDatabase.CART_TABLE + " WHERE mUsername = :username ")
    Cart getCartByUsername(String username);

    @Query("UPDATE CART_TABLE SET mFishList = :fishList WHERE mUsername =:username")
    void update(String username, String fishList);

}
