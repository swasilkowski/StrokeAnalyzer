package Repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asus.strokeanalyzer.Model.Form.Answer.NumericAnswer;
import com.example.asus.strokeanalyzer.Model.NihssExamination;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import Database.DbContract;
import Database.StrokeAnalyzerDbSQLiteHelper;

/**
 * Created by S. Wasilkowski on 2017-12-04.
 */

public class NihhsFormRepository {
    private Context context;

    int saveToDB(NihssExamination examination, int patientId) throws Exception {
        if (examination.Answers.size() != DbContract.NihhsForm.QuestionsCount)
        {
            throw new Exception("incorrect list");
        }
        SQLiteDatabase database = new StrokeAnalyzerDbSQLiteHelper(context).getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(DbContract.NihhsForm.COLUMN_PATIENT_ID, patientId);
        values.put(DbContract.NihhsForm.COLUMN_ADDED_ON, Calendar.getInstance().getTime().toString());
        values.put(DbContract.NihhsForm.COLUMN_QUESTION_1, ((NumericAnswer)examination.Answers.get(0)).Value);
        values.put(DbContract.NihhsForm.COLUMN_QUESTION_2, ((NumericAnswer)examination.Answers.get(1)).Value);
        values.put(DbContract.NihhsForm.COLUMN_QUESTION_3, ((NumericAnswer)examination.Answers.get(2)).Value);
        values.put(DbContract.NihhsForm.COLUMN_QUESTION_4, ((NumericAnswer)examination.Answers.get(3)).Value);
        values.put(DbContract.NihhsForm.COLUMN_QUESTION_5, ((NumericAnswer)examination.Answers.get(4)).Value);
        values.put(DbContract.NihhsForm.COLUMN_QUESTION_6, ((NumericAnswer)examination.Answers.get(5)).Value);
        values.put(DbContract.NihhsForm.COLUMN_QUESTION_7, ((NumericAnswer)examination.Answers.get(6)).Value);
        values.put(DbContract.NihhsForm.COLUMN_QUESTION_8, ((NumericAnswer)examination.Answers.get(7)).Value);
        values.put(DbContract.NihhsForm.COLUMN_QUESTION_9, ((NumericAnswer)examination.Answers.get(8)).Value);
        values.put(DbContract.NihhsForm.COLUMN_QUESTION_10, ((NumericAnswer)examination.Answers.get(9)).Value);
        values.put(DbContract.NihhsForm.COLUMN_QUESTION_11, ((NumericAnswer)examination.Answers.get(10)).Value);
        values.put(DbContract.NihhsForm.COLUMN_QUESTION_12, ((NumericAnswer)examination.Answers.get(11)).Value);
        values.put(DbContract.NihhsForm.COLUMN_QUESTION_13, ((NumericAnswer)examination.Answers.get(12)).Value);
        values.put(DbContract.NihhsForm.COLUMN_QUESTION_14, ((NumericAnswer)examination.Answers.get(13)).Value);
        values.put(DbContract.NihhsForm.COLUMN_QUESTION_15, ((NumericAnswer)examination.Answers.get(14)).Value);

        long newRowId = database.insert(DbContract.NihhsForm.TABLE_NAME, null, values);

        return (int)newRowId;
    }

    Cursor readFromDB(int id, int patientId) {

        SQLiteDatabase database = new StrokeAnalyzerDbSQLiteHelper(context).getReadableDatabase();

        String[] projection = {
                DbContract.NihhsForm._ID,
                DbContract.NihhsForm.COLUMN_PATIENT_ID,
                DbContract.NihhsForm.COLUMN_ADDED_ON,
                DbContract.NihhsForm.COLUMN_QUESTION_1,
                DbContract.NihhsForm.COLUMN_QUESTION_2,
                DbContract.NihhsForm.COLUMN_QUESTION_3,
                DbContract.NihhsForm.COLUMN_QUESTION_4,
                DbContract.NihhsForm.COLUMN_QUESTION_5,
                DbContract.NihhsForm.COLUMN_QUESTION_6,
                DbContract.NihhsForm.COLUMN_QUESTION_7,
                DbContract.NihhsForm.COLUMN_QUESTION_8,
                DbContract.NihhsForm.COLUMN_QUESTION_9,
                DbContract.NihhsForm.COLUMN_QUESTION_10,
                DbContract.NihhsForm.COLUMN_QUESTION_11,
                DbContract.NihhsForm.COLUMN_QUESTION_12,
                DbContract.NihhsForm.COLUMN_QUESTION_13,
                DbContract.NihhsForm.COLUMN_QUESTION_14,
                DbContract.NihhsForm.COLUMN_QUESTION_15,
        };

        String selection =
                DbContract.Patient._ID + " = ? " +
                        DbContract.NihhsForm.COLUMN_PATIENT_ID + " = ? ";

        String[] selectionArgs = {id + "", patientId + ""};

        Cursor cursor = database.query(
                DbContract.NihhsForm.TABLE_NAME,     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // don't sort
        );

        return cursor;
    }

    public NihssExamination SelectById(int id){
        Cursor cursor = readFromDB(id, -1);
        if (cursor.getCount() != 1){
            return null;
        }

        NihssExamination examination = GetExaminationFromCursor(cursor);
        return examination;

    }

    public List<NihssExamination> SelectByPatientId(int patientId){
        Cursor cursor = readFromDB(-1, patientId);
        List<NihssExamination> nihssExaminations = new LinkedList<NihssExamination>();
        if (cursor.getCount() > 1)
            return nihssExaminations;
        do {
            NihssExamination examination = GetExaminationFromCursor(cursor);
            nihssExaminations.add(examination);
        } while (cursor.moveToNext());
        return nihssExaminations;
    }

    private NihssExamination GetExaminationFromCursor(Cursor cursor){
        NihssExamination examination = new NihssExamination();
        examination.Date = new Date(cursor.getLong(cursor.getColumnIndex(DbContract.NihhsForm.COLUMN_ADDED_ON)));
        for(int i = 0; i < DbContract.NihhsForm.QuestionsCount; i++){
            NumericAnswer answer = new NumericAnswer(i);
            answer.Value = cursor.getInt(cursor.getColumnIndex(DbContract.NihhsForm.GetQuestionColumnName(i)));
            examination.Answers.add(answer);
        }
        return examination;
    }
}
