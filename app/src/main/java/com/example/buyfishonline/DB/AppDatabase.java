package com.example.buyfishonline.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.buyfishonline.Fish;
import com.example.buyfishonline.User;

@Database(entities = {User.class, Fish.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "User.DB";
    public static final String USER_TABLE = "USER_TABLE";
    public static final String FISH_TABLE = "FISH_TABLE";
    public static final String CART_TABLE = "CART_TABLE";

    private static volatile AppDatabase instance;
    private static final Object LOCK = new Object();

    public abstract UserDAO UserDAO();


    public static AppDatabase getInstance(Context context){
        if(instance == null){
            synchronized (LOCK){
                if(instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class,
                            DATABASE_NAME).build();
                }
            }
        }
        return instance;
    }
}
