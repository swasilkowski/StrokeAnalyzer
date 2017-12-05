package Database;

import android.provider.BaseColumns;

/**
 * Created by S. Wasilkowski on 2017-11-28.
 */

public final class DbContract {
    private DbContract() {
    }

    public static class Patient implements BaseColumns {
        public static final String TABLE_NAME = "patient";
        public static final String COLUMN_FIRST_NAME = "first_name";
        public static final String COLUMN_LAST_NAME = "last_name";

        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FIRST_NAME + " TEXT, " +
                COLUMN_LAST_NAME + " TEXT, " + ")";
    }

    public  final static class NihhsForm implements BaseColumns {
        public static final String TABLE_NAME = "nihhs_form";
        public static final String COLUMN_PATIENT_ID = "patient_id";
        public static final String COLUMN_ADDED_ON = "added_on";
        public static final String COLUMN_QUESTION_1 = "question_1";
        public static final String COLUMN_QUESTION_2 = "question_2";
        public static final String COLUMN_QUESTION_3 = "question_3";
        public static final String COLUMN_QUESTION_4 = "question_4";
        public static final String COLUMN_QUESTION_5 = "question_5";
        public static final String COLUMN_QUESTION_6 = "question_6";
        public static final String COLUMN_QUESTION_7 = "question_7";
        public static final String COLUMN_QUESTION_8 = "question_8";
        public static final String COLUMN_QUESTION_9 = "question_9";
        public static final String COLUMN_QUESTION_10 = "question_10";
        public static final String COLUMN_QUESTION_11 = "question_11";
        public static final String COLUMN_QUESTION_12 = "question_12";
        public static final String COLUMN_QUESTION_13 = "question_13";
        public static final String COLUMN_QUESTION_14 = "question_14";
        public static final String COLUMN_QUESTION_15 = "question_15";
        public static final int QuestionsCount = 15;
        public static final String GetQuestionColumnName(int i){
            if (i < 0 || i > 14){
                throw new IndexOutOfBoundsException();
            }
            return "question_"+(i+1);
        }

        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PATIENT_ID + " INTEGER, " +
                COLUMN_ADDED_ON + " INTEGER, " +
                COLUMN_QUESTION_1 + " INTEGER" +
                COLUMN_QUESTION_2 + " INTEGER" +
                COLUMN_QUESTION_3 + " INTEGER" +
                COLUMN_QUESTION_4 + " INTEGER" +
                COLUMN_QUESTION_5 + " INTEGER" +
                COLUMN_QUESTION_6 + " INTEGER" +
                COLUMN_QUESTION_7 + " INTEGER" +
                COLUMN_QUESTION_8 + " INTEGER" +
                COLUMN_QUESTION_9 + " INTEGER" +
                COLUMN_QUESTION_10 + " INTEGER" +
                COLUMN_QUESTION_11 + " INTEGER" +
                COLUMN_QUESTION_12 + " INTEGER" +
                COLUMN_QUESTION_13 + " INTEGER" +
                COLUMN_QUESTION_14 + " INTEGER" +
                COLUMN_QUESTION_15 + " INTEGER" +
                "FOREIGN KEY(" + COLUMN_PATIENT_ID + ") REFERENCES " +
                Patient.TABLE_NAME + "(" + Patient._ID + ") "+ ")";
    }

    public  final static class OtherData implements BaseColumns {
        public static final String TABLE_NAME = "other_data";
        public static final String COLUMN_PATIENT_ID = "patient_id";
        public static final String COLUMN_ADDED_ON = "added_on";
        public static final String COLUMN_QUESTION_1 = "question_1";
        public static final String COLUMN_QUESTION_2 = "question_2";
        public static final String COLUMN_QUESTION_3 = "question_3";
        public static final String COLUMN_QUESTION_4 = "question_4";
        public static final String COLUMN_QUESTION_5 = "question_5";
        public static final String COLUMN_QUESTION_6 = "question_6";
        public static final String COLUMN_QUESTION_7 = "question_7";
        public static final String COLUMN_QUESTION_8 = "question_8";
        public static final String COLUMN_QUESTION_9 = "question_9";
        public static final String COLUMN_QUESTION_10 = "question_10";
        public static final String COLUMN_QUESTION_11 = "question_11";
        public static final String COLUMN_QUESTION_12 = "question_12";
        public static final String COLUMN_QUESTION_13 = "question_13";
        public static final String COLUMN_QUESTION_14 = "question_14";
        public static final String COLUMN_QUESTION_15 = "question_15";

        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PATIENT_ID + " INTEGER, " +
                COLUMN_ADDED_ON + " INTEGER, " +
                COLUMN_QUESTION_1 + " INTEGER" +
                COLUMN_QUESTION_2 + " INTEGER" +
                COLUMN_QUESTION_3 + " INTEGER" +
                COLUMN_QUESTION_4 + " INTEGER" +
                COLUMN_QUESTION_5 + " INTEGER" +
                COLUMN_QUESTION_6 + " INTEGER" +
                COLUMN_QUESTION_7 + " INTEGER" +
                COLUMN_QUESTION_8 + " INTEGER" +
                COLUMN_QUESTION_9 + " INTEGER" +
                COLUMN_QUESTION_10 + " INTEGER" +
                COLUMN_QUESTION_11 + " INTEGER" +
                COLUMN_QUESTION_12 + " INTEGER" +
                COLUMN_QUESTION_13 + " INTEGER" +
                COLUMN_QUESTION_14 + " INTEGER" +
                COLUMN_QUESTION_15 + " INTEGER" +
                "FOREIGN KEY(" + COLUMN_PATIENT_ID + ") REFERENCES " +
                Patient.TABLE_NAME + "(" + Patient._ID + ") "+ ")";
    }
}
