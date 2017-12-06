package Database;

import android.arch.persistence.room.Room;
import android.content.Context;

/**
 * Created by S. Wasilkowski on 2017-12-06.
 */

public final class DatabaseAccess {
    public StrokeAnalyzerDatabase database;

    private static DatabaseAccess instance = new DatabaseAccess();

    private DatabaseAccess() {
        Context context = null;
        database = Room.databaseBuilder(context, StrokeAnalyzerDatabase.class,
                "stroke_analyzer_database").build();
    }

    public static DatabaseAccess getInstance() {
        return instance;
    }
}
