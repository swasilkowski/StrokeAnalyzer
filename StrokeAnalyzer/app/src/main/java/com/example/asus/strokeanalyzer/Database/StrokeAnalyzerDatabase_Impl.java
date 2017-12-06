package com.example.asus.strokeanalyzer.Database;

import com.example.asus.strokeanalyzer.DAO.NihssDao;
import com.example.asus.strokeanalyzer.DAO.NihssDao_Impl;
import com.example.asus.strokeanalyzer.DAO.PatientDao;
import com.example.asus.strokeanalyzer.DAO.PatientDao_Impl;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomOpenHelper;
import android.arch.persistence.room.util.TableInfo;

import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class StrokeAnalyzerDatabase_Impl extends StrokeAnalyzerDatabase {
  private volatile PatientDao _patientDao;

  private volatile NihssDao _nihssDao;

  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `patient` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `surname` TEXT, `patient_name` INTEGER NOT NULL, `dataId` INTEGER, `Data1` INTEGER)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `nihss_form` (`id` INTEGER NOT NULL, `patient_id` INTEGER NOT NULL, `added_on` INTEGER NOT NULL, `Question1` INTEGER NOT NULL, `Question2` INTEGER NOT NULL, `Question3` INTEGER NOT NULL, `Question4` INTEGER NOT NULL, `Question5` INTEGER NOT NULL, `Question6` INTEGER NOT NULL, `Question7` INTEGER NOT NULL, `Question8` INTEGER NOT NULL, `Question9` INTEGER NOT NULL, `Question10` INTEGER NOT NULL, `Question11` INTEGER NOT NULL, PRIMARY KEY(`id`), FOREIGN KEY(`patient_id`) REFERENCES `patient`(`id`) ON UPDATE NO ACTION ON DELETE NO ACTION )");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `OtherData` (`dataId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `Data1` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"607ab1fa189e58926d38241d16e37a90\")");
      }

      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `patient`");
        _db.execSQL("DROP TABLE IF EXISTS `nihss_form`");
        _db.execSQL("DROP TABLE IF EXISTS `OtherData`");
      }

      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        _db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsPatient = new HashMap<String, TableInfo.Column>(6);
        _columnsPatient.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsPatient.put("name", new TableInfo.Column("name", "TEXT", false, 0));
        _columnsPatient.put("surname", new TableInfo.Column("surname", "TEXT", false, 0));
        _columnsPatient.put("patient_name", new TableInfo.Column("patient_name", "INTEGER", true, 0));
        _columnsPatient.put("dataId", new TableInfo.Column("dataId", "INTEGER", false, 0));
        _columnsPatient.put("Data1", new TableInfo.Column("Data1", "INTEGER", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPatient = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPatient = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPatient = new TableInfo("patient", _columnsPatient, _foreignKeysPatient, _indicesPatient);
        final TableInfo _existingPatient = TableInfo.read(_db, "patient");
        if (! _infoPatient.equals(_existingPatient)) {
          throw new IllegalStateException("Migration didn't properly handle patient(com.example.asus.strokeanalyzer.Entities.Patient).\n"
                  + " Expected:\n" + _infoPatient + "\n"
                  + " Found:\n" + _existingPatient);
        }
        final HashMap<String, TableInfo.Column> _columnsNihssForm = new HashMap<String, TableInfo.Column>(14);
        _columnsNihssForm.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsNihssForm.put("patient_id", new TableInfo.Column("patient_id", "INTEGER", true, 0));
        _columnsNihssForm.put("added_on", new TableInfo.Column("added_on", "INTEGER", true, 0));
        _columnsNihssForm.put("Question1", new TableInfo.Column("Question1", "INTEGER", true, 0));
        _columnsNihssForm.put("Question2", new TableInfo.Column("Question2", "INTEGER", true, 0));
        _columnsNihssForm.put("Question3", new TableInfo.Column("Question3", "INTEGER", true, 0));
        _columnsNihssForm.put("Question4", new TableInfo.Column("Question4", "INTEGER", true, 0));
        _columnsNihssForm.put("Question5", new TableInfo.Column("Question5", "INTEGER", true, 0));
        _columnsNihssForm.put("Question6", new TableInfo.Column("Question6", "INTEGER", true, 0));
        _columnsNihssForm.put("Question7", new TableInfo.Column("Question7", "INTEGER", true, 0));
        _columnsNihssForm.put("Question8", new TableInfo.Column("Question8", "INTEGER", true, 0));
        _columnsNihssForm.put("Question9", new TableInfo.Column("Question9", "INTEGER", true, 0));
        _columnsNihssForm.put("Question10", new TableInfo.Column("Question10", "INTEGER", true, 0));
        _columnsNihssForm.put("Question11", new TableInfo.Column("Question11", "INTEGER", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysNihssForm = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysNihssForm.add(new TableInfo.ForeignKey("patient", "NO ACTION", "NO ACTION",Arrays.asList("patient_id"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesNihssForm = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoNihssForm = new TableInfo("nihss_form", _columnsNihssForm, _foreignKeysNihssForm, _indicesNihssForm);
        final TableInfo _existingNihssForm = TableInfo.read(_db, "nihss_form");
        if (! _infoNihssForm.equals(_existingNihssForm)) {
          throw new IllegalStateException("Migration didn't properly handle nihss_form(com.example.asus.strokeanalyzer.Entities.NihssExamination).\n"
                  + " Expected:\n" + _infoNihssForm + "\n"
                  + " Found:\n" + _existingNihssForm);
        }
        final HashMap<String, TableInfo.Column> _columnsOtherData = new HashMap<String, TableInfo.Column>(2);
        _columnsOtherData.put("dataId", new TableInfo.Column("dataId", "INTEGER", true, 1));
        _columnsOtherData.put("Data1", new TableInfo.Column("Data1", "INTEGER", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysOtherData = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesOtherData = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoOtherData = new TableInfo("OtherData", _columnsOtherData, _foreignKeysOtherData, _indicesOtherData);
        final TableInfo _existingOtherData = TableInfo.read(_db, "OtherData");
        if (! _infoOtherData.equals(_existingOtherData)) {
          throw new IllegalStateException("Migration didn't properly handle OtherData(com.example.asus.strokeanalyzer.Entities.OtherData).\n"
                  + " Expected:\n" + _infoOtherData + "\n"
                  + " Found:\n" + _existingOtherData);
        }
      }
    }, "607ab1fa189e58926d38241d16e37a90");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "patient","nihss_form","OtherData");
  }

  @Override
  public PatientDao patientDao() {
    if (_patientDao != null) {
      return _patientDao;
    } else {
      synchronized(this) {
        if(_patientDao == null) {
          _patientDao = new PatientDao_Impl(this);
        }
        return _patientDao;
      }
    }
  }

  @Override
  public NihssDao nihssDao() {
    if (_nihssDao != null) {
      return _nihssDao;
    } else {
      synchronized(this) {
        if(_nihssDao == null) {
          _nihssDao = new NihssDao_Impl(this);
        }
        return _nihssDao;
      }
    }
  }
}
