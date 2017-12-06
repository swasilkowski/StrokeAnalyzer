package com.example.asus.strokeanalyzer.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by S. Wasilkowski on 2017-12-06.
 */

@Entity
public class OtherData {
    @PrimaryKey(autoGenerate = true)
    public int dataId;

    public int Data1;
}
