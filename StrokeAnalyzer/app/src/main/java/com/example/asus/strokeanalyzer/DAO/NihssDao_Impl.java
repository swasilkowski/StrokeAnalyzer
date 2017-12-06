package com.example.asus.strokeanalyzer.DAO;

import com.example.asus.strokeanalyzer.Entities.NihssExamination;
import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;

public class NihssDao_Impl implements NihssDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfNihssExamination;

  public NihssDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfNihssExamination = new EntityInsertionAdapter<NihssExamination>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `nihss_form`(`id`,`patient_id`,`added_on`,`Question1`,`Question2`,`Question3`,`Question4`,`Question5`,`Question6`,`Question7`,`Question8`,`Question9`,`Question10`,`Question11`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, NihssExamination value) {
        stmt.bindLong(1, value.id);
        stmt.bindLong(2, value.PatientId);
        stmt.bindLong(3, value.addedOn);
        stmt.bindLong(4, value.Question1);
        stmt.bindLong(5, value.Question2);
        stmt.bindLong(6, value.Question3);
        stmt.bindLong(7, value.Question4);
        stmt.bindLong(8, value.Question5);
        stmt.bindLong(9, value.Question6);
        stmt.bindLong(10, value.Question7);
        stmt.bindLong(11, value.Question8);
        stmt.bindLong(12, value.Question9);
        stmt.bindLong(13, value.Question10);
        stmt.bindLong(14, value.Question11);
      }
    };
  }

  @Override
  public long insert(NihssExamination nihssExamination) {
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfNihssExamination.insertAndReturnId(nihssExamination);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<NihssExamination> SelectByPatientId(int patientId) {
    final String _sql = "SELECT * FROM nihss_form WHERE patient_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, patientId);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfPatientId = _cursor.getColumnIndexOrThrow("patient_id");
      final int _cursorIndexOfAddedOn = _cursor.getColumnIndexOrThrow("added_on");
      final int _cursorIndexOfQuestion1 = _cursor.getColumnIndexOrThrow("Question1");
      final int _cursorIndexOfQuestion2 = _cursor.getColumnIndexOrThrow("Question2");
      final int _cursorIndexOfQuestion3 = _cursor.getColumnIndexOrThrow("Question3");
      final int _cursorIndexOfQuestion4 = _cursor.getColumnIndexOrThrow("Question4");
      final int _cursorIndexOfQuestion5 = _cursor.getColumnIndexOrThrow("Question5");
      final int _cursorIndexOfQuestion6 = _cursor.getColumnIndexOrThrow("Question6");
      final int _cursorIndexOfQuestion7 = _cursor.getColumnIndexOrThrow("Question7");
      final int _cursorIndexOfQuestion8 = _cursor.getColumnIndexOrThrow("Question8");
      final int _cursorIndexOfQuestion9 = _cursor.getColumnIndexOrThrow("Question9");
      final int _cursorIndexOfQuestion10 = _cursor.getColumnIndexOrThrow("Question10");
      final int _cursorIndexOfQuestion11 = _cursor.getColumnIndexOrThrow("Question11");
      final List<NihssExamination> _result = new ArrayList<NihssExamination>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final NihssExamination _item;
        _item = new NihssExamination();
        _item.id = _cursor.getInt(_cursorIndexOfId);
        _item.PatientId = _cursor.getInt(_cursorIndexOfPatientId);
        _item.addedOn = _cursor.getInt(_cursorIndexOfAddedOn);
        _item.Question1 = _cursor.getInt(_cursorIndexOfQuestion1);
        _item.Question2 = _cursor.getInt(_cursorIndexOfQuestion2);
        _item.Question3 = _cursor.getInt(_cursorIndexOfQuestion3);
        _item.Question4 = _cursor.getInt(_cursorIndexOfQuestion4);
        _item.Question5 = _cursor.getInt(_cursorIndexOfQuestion5);
        _item.Question6 = _cursor.getInt(_cursorIndexOfQuestion6);
        _item.Question7 = _cursor.getInt(_cursorIndexOfQuestion7);
        _item.Question8 = _cursor.getInt(_cursorIndexOfQuestion8);
        _item.Question9 = _cursor.getInt(_cursorIndexOfQuestion9);
        _item.Question10 = _cursor.getInt(_cursorIndexOfQuestion10);
        _item.Question11 = _cursor.getInt(_cursorIndexOfQuestion11);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
