package com.example.m_hike.DatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.m_hike.Hike.Hike;

import java.util.ArrayList;

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
                    "%s TEXT NOT NULL, " +
                    "%s TEXT NOT NULL, " +
                    "%s TEXT NOT NULL, " +
                    "%s INTEGER NOT NULL, " +
                    "%s REAL NOT NULL, " +
                    "%s TEXT NOT NULL, " +
                    "%s TEXT, " +
                    "%s INTEGER NOT NULL DEFAULT 0, " +
                    "%s INTEGER NOT NULL DEFAULT 0) ",
            HikeTable.TABLE, HikeTable.ID_COLUMN, HikeTable.NAME_COLUMN, HikeTable.LOCATION_COLUMN, HikeTable.DATE_COLUMN,
            HikeTable.PARKING_COLUMN, HikeTable.LENGTH_COLUMN, HikeTable.DIFFICULTY_COLUMN, HikeTable.DESCRIPTION_COLUMN, HikeTable.FAVORITE_COLUMN, HikeTable.COMPLETED_COLUMN
    );

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,1);
        database =getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase database){
//        ! REMEMBER TO ADD THE SECOND TABLE !
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
        rowValues.put(HikeTable.PARKING_COLUMN, hike.getParking());
        rowValues.put(HikeTable.LENGTH_COLUMN, hike.getLength());
        rowValues.put(HikeTable.DIFFICULTY_COLUMN, hike.getDifficulty());
        rowValues.put(HikeTable.DESCRIPTION_COLUMN, hike.getDescription());
        rowValues.put(HikeTable.FAVORITE_COLUMN, hike.getFavorite());
        rowValues.put(HikeTable.COMPLETED_COLUMN, hike.getCompleted());

        return database.insertOrThrow(HikeTable.TABLE,null,rowValues);
    }

    public ArrayList<Hike> getHikeDetails(){
        Cursor results = database.query(HikeTable.TABLE, new String[] {HikeTable.ID_COLUMN,"name","location","date","parking","length","difficulty","description","favorite","completed"},
                null,null, null,null,HikeTable.ID_COLUMN);

        ArrayList<Hike> listHike = new ArrayList<>();
        results.moveToFirst();
        while(!results.isAfterLast()){
            int hike_id = results.getInt(0);
            String name = results.getString(1);
            String location = results.getString(2);
            String date = results.getString(3);
            int parking = results.getInt(4);
            double length = results.getDouble(5);
            String difficulty = results.getString(6);
            String description = results.getString(7);
            int favorite = results.getInt(8);
            int completed = results.getInt(9);

            listHike.add(new Hike(hike_id,name,location,date,parking,length,difficulty,description,favorite,completed));
            results.moveToNext();
        }
        return listHike;
    }
}
