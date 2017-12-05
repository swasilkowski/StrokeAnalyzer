package Repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.asus.strokeanalyzer.Model.Patient;

import java.util.LinkedList;
import java.util.List;

import Database.DbContract;
import Database.StrokeAnalyzerDbSQLiteHelper;

/**
 * Created by S. Wasilkowski on 2017-11-28.
 */

public class PatientRepository {
    private Context context;

    public PatientRepository(Context context){
        this.context = context;
    }

    int saveToDB(Patient patient) {
        SQLiteDatabase database = new StrokeAnalyzerDbSQLiteHelper(context).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbContract.Patient.COLUMN_FIRST_NAME, patient.Name);
        values.put(DbContract.Patient.COLUMN_LAST_NAME, patient.Surname);

        long newRowId = database.insert(DbContract.Patient.TABLE_NAME, null, values);

        return (int)newRowId;
    }

    Cursor readFromDB(int id, Boolean all) {

        SQLiteDatabase database = new StrokeAnalyzerDbSQLiteHelper(context).getReadableDatabase();

        String[] projection = {
                DbContract.Patient._ID,
                DbContract.Patient.COLUMN_FIRST_NAME,
                DbContract.Patient.COLUMN_LAST_NAME
        };

        String selection =
                DbContract.Patient._ID + " = ? " + "or TRUE = ? ";

        String[] selectionArgs = {id + "", all.toString()};

        Cursor cursor = database.query(
                DbContract.Patient.TABLE_NAME,     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // don't sort
        );

        return cursor;
    }

    public Patient SelectById(int id){
        Cursor cursor = readFromDB(id, false);
        if (cursor.getCount() != 1){
            return null;
        }
        Patient patient = new Patient();
        patient.Id = cursor.getInt(cursor.getColumnIndex(DbContract.Patient._ID));
        patient.Name = cursor.getString(cursor.getColumnIndex(DbContract.Patient.COLUMN_FIRST_NAME)) +
                " " + cursor.getString(cursor.getColumnIndex(DbContract.Patient.COLUMN_LAST_NAME));
        return patient;
    }

    public List<Patient> SelectAll(){
        Cursor cursor = readFromDB(-1, true);
        List<Patient> patientList = new LinkedList<Patient>();
        if (cursor.getCount() > 1)
            return patientList;
        do {
            Patient patient = new Patient();
            patient.Id = cursor.getInt(cursor.getColumnIndex(DbContract.Patient._ID));
            patient.Name = cursor.getString(cursor.getColumnIndex(DbContract.Patient.COLUMN_FIRST_NAME)) +
                    " " + cursor.getString(cursor.getColumnIndex(DbContract.Patient.COLUMN_LAST_NAME));
            patientList.add(patient);
        } while (cursor.moveToNext());
        return patientList;
    }
}
