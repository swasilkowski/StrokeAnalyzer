package com.example.asus.strokeanalyzer.DAO;

import com.example.asus.strokeanalyzer.Entities.OtherData;
import com.example.asus.strokeanalyzer.Entities.Patient;
import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;

public class PatientDao_Impl implements PatientDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfPatient;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfPatient;

  public PatientDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPatient = new EntityInsertionAdapter<Patient>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `patient`(`id`,`name`,`surname`,`patient_name`,`dataId`,`Data1`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Patient value) {
        stmt.bindLong(1, value.id);
        if (value.Name == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.Name);
        }
        if (value.Surname == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.Surname);
        }
        stmt.bindLong(4, value.PatientNumber);
        final OtherData _tmpOtherData = value.OtherData;
        if(_tmpOtherData != null) {
          stmt.bindLong(5, _tmpOtherData.dataId);
          stmt.bindLong(6, _tmpOtherData.Data1);
        } else {
          stmt.bindNull(5);
          stmt.bindNull(6);
        }
      }
    };
    this.__updateAdapterOfPatient = new EntityDeletionOrUpdateAdapter<Patient>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `patient` SET `id` = ?,`name` = ?,`surname` = ?,`patient_name` = ?,`dataId` = ?,`Data1` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Patient value) {
        stmt.bindLong(1, value.id);
        if (value.Name == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.Name);
        }
        if (value.Surname == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.Surname);
        }
        stmt.bindLong(4, value.PatientNumber);
        final OtherData _tmpOtherData = value.OtherData;
        if(_tmpOtherData != null) {
          stmt.bindLong(5, _tmpOtherData.dataId);
          stmt.bindLong(6, _tmpOtherData.Data1);
        } else {
          stmt.bindNull(5);
          stmt.bindNull(6);
        }
        stmt.bindLong(7, value.id);
      }
    };
  }

  @Override
  public long insert(Patient patient) {
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfPatient.insertAndReturnId(patient);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(Patient patient) {
    __db.beginTransaction();
    try {
      __updateAdapterOfPatient.handle(patient);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Patient> selectAll() {
    final String _sql = "SELECT * FROM patient";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfName = _cursor.getColumnIndexOrThrow("name");
      final int _cursorIndexOfSurname = _cursor.getColumnIndexOrThrow("surname");
      final int _cursorIndexOfPatientNumber = _cursor.getColumnIndexOrThrow("patient_name");
      final int _cursorIndexOfDataId = _cursor.getColumnIndexOrThrow("dataId");
      final int _cursorIndexOfData1 = _cursor.getColumnIndexOrThrow("Data1");
      final List<Patient> _result = new ArrayList<Patient>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Patient _item;
        final OtherData _tmpOtherData;
        if (! (_cursor.isNull(_cursorIndexOfDataId) && _cursor.isNull(_cursorIndexOfData1))) {
          _tmpOtherData = new OtherData();
          _tmpOtherData.dataId = _cursor.getInt(_cursorIndexOfDataId);
          _tmpOtherData.Data1 = _cursor.getInt(_cursorIndexOfData1);
        }  else  {
          _tmpOtherData = null;
        }
        _item = new Patient();
        _item.id = _cursor.getInt(_cursorIndexOfId);
        _item.Name = _cursor.getString(_cursorIndexOfName);
        _item.Surname = _cursor.getString(_cursorIndexOfSurname);
        _item.PatientNumber = _cursor.getInt(_cursorIndexOfPatientNumber);
        _item.OtherData = _tmpOtherData;
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Patient selectById(int id) {
    final String _sql = "SELECT * FROM patient WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfName = _cursor.getColumnIndexOrThrow("name");
      final int _cursorIndexOfSurname = _cursor.getColumnIndexOrThrow("surname");
      final int _cursorIndexOfPatientNumber = _cursor.getColumnIndexOrThrow("patient_name");
      final int _cursorIndexOfDataId = _cursor.getColumnIndexOrThrow("dataId");
      final int _cursorIndexOfData1 = _cursor.getColumnIndexOrThrow("Data1");
      final Patient _result;
      if(_cursor.moveToFirst()) {
        final OtherData _tmpOtherData;
        if (! (_cursor.isNull(_cursorIndexOfDataId) && _cursor.isNull(_cursorIndexOfData1))) {
          _tmpOtherData = new OtherData();
          _tmpOtherData.dataId = _cursor.getInt(_cursorIndexOfDataId);
          _tmpOtherData.Data1 = _cursor.getInt(_cursorIndexOfData1);
        }  else  {
          _tmpOtherData = null;
        }
        _result = new Patient();
        _result.id = _cursor.getInt(_cursorIndexOfId);
        _result.Name = _cursor.getString(_cursorIndexOfName);
        _result.Surname = _cursor.getString(_cursorIndexOfSurname);
        _result.PatientNumber = _cursor.getInt(_cursorIndexOfPatientNumber);
        _result.OtherData = _tmpOtherData;
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
