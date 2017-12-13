package com.example.asus.strokeanalyzer.Database;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.database.sqlite.SQLiteCantOpenDatabaseException;

/**
 * Created by S. Wasilkowski on 2017-12-06.
 */

public final class DatabaseAccess {
    public StrokeAnalyzerDatabase database;

    private static DatabaseAccess instance;

    private DatabaseAccess(Context context) {
        database = Room.databaseBuilder(context, StrokeAnalyzerDatabase.class,
                "stroke_analyzer_database").allowMainThreadQueries().build();
    }

    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public static DatabaseAccess getInstance() throws SQLiteCantOpenDatabaseException {
        if (instance == null) {
            throw new SQLiteCantOpenDatabaseException();
        }
        return instance;
    }
}
