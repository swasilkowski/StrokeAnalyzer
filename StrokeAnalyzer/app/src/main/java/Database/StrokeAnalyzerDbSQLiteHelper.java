package Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by S. Wasilkowski on 2017-11-28.
 */

public class StrokeAnalyzerDbSQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "stroke_anylizer_database";

    public StrokeAnalyzerDbSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DbContract.Patient.CREATE_TABLE);
        sqLiteDatabase.execSQL(DbContract.NihhsForm.CREATE_TABLE);
        sqLiteDatabase.execSQL(DbContract.OtherData.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DbContract.Patient.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DbContract.NihhsForm.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DbContract.OtherData.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
