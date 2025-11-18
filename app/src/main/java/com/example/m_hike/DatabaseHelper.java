package com.example.m_hike;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "m_hike_db";


//    Hike table
    public static class HikeTable implements BaseColumns{
        public static final String TABLE = "hikes";
        public static final String ID_COLUMN ="hike_id";
        public static final String NAME_COLUMN = "name";
        public static final String LOCATION_COLUMN = "location";
        public static final String DATE_COLUMN = "date";
        public static final String PARKING_COLUMN = "parking";
        public static final String LENGTH_COLUMN = "length";
        public static final String DIFFICULTY_COLUMN = "difficulty";
        public static final String DESCRIPTION_COLUMN = "description";
        public static final String FAVORITE_COLUMN = "favorite";
        public static final String COMPLETED_COLUMN = "completed";
    }

    private SQLiteDatabase database;

    private static final String HIKE_TABLE_CREATE = String.format(
            "CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s INTEGER, " +
                    "%s REAL, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s INTEGER, " +
                    "%s INTEGER) ",
            HikeTable.TABLE, HikeTable.ID_COLUMN, HikeTable.NAME_COLUMN, HikeTable.LOCATION_COLUMN, HikeTable.DATE_COLUMN,
            HikeTable.PARKING_COLUMN, HikeTable.LENGTH_COLUMN, HikeTable.DIFFICULTY_COLUMN, HikeTable.DESCRIPTION_COLUMN, HikeTable.FAVORITE_COLUMN, HikeTable.COMPLETED_COLUMN
    );

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,1);
        database =getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase database){
        database.execSQL(HIKE_TABLE_CREATE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //        Automatically called if db version number changes
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);

        Log.w(this.getClass().getName(), DATABASE_NAME + " database has upgraded to version " + newVersion + " - old data lost");
        onCreate(db);
    }

    public long insertHikeDetails(Hike hike){
        ContentValues rowValues =   new ContentValues();
        rowValues.put(HikeTable.NAME_COLUMN, hike.getName());
        rowValues.put(HikeTable.LOCATION_COLUMN, hike.getLocation());
        rowValues.put(HikeTable.DATE_COLUMN, hike.getDate());
        rowValues.put(HikeTable.PARKING_COLUMN, hike.isParking());
        rowValues.put(HikeTable.LENGTH_COLUMN, hike.getLength());
        rowValues.put(HikeTable.DIFFICULTY_COLUMN, hike.getDifficulty());
        rowValues.put(HikeTable.DESCRIPTION_COLUMN, hike.getDescription());
        rowValues.put(HikeTable.FAVORITE_COLUMN, hike.isFavorite());
        rowValues.put(HikeTable.COMPLETED_COLUMN, hike.isCompleted());

        return database.insertOrThrow(HikeTable.TABLE,null,rowValues);
    }
}
