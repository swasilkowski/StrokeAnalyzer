package Database;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomDatabase;

import DAO.NihssDao;
import DAO.PatientDao;
import Entities.NihssExamination;
import Entities.OtherData;
import Entities.Patient;

/**
 * Created by S. Wasilkowski on 2017-12-06.
 */

@Database(entities = {Patient.class, NihssExamination.class, OtherData.class}, version = 1)
public abstract class StrokeAnalyzerDatabase extends RoomDatabase {

    public abstract PatientDao patientDao();

    public abstract NihssDao nihssDao();

}
