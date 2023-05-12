package com.example.buyfishonline;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.buyfishonline.DB.AppDatabase;

@Entity(tableName = AppDatabase.FISH_TABLE)
public class Fish {

    @PrimaryKey(autoGenerate = true)
    private int mFishId;

    private String mFishName;
    private int mFishCount;

    public Fish(String fishName, int fishCount) {
        mFishName = fishName;
        mFishCount = fishCount;
    }

    public int getFishId() {
        return mFishId;
    }

    public void setFishId(int fishId) {
        mFishId = fishId;
    }

    public String getFishName() {
        return mFishName;
    }

    public void setFishName(String fishName) {
        mFishName = fishName;
    }

    public int getFishCount() {
        return mFishCount;
    }

    public void setFishCount(int fishCount) {
        mFishCount = fishCount;
    }

    public String toString() {
        return "Fish: " + mFishName + "\n" +
                "Quantity: " + mFishCount + "\n" +
                "FishId: " + mFishId + "\n" +
                "=-=-=-=-=-=-=-=\n";
    }
}
