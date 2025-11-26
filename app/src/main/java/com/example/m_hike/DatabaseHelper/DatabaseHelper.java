package com.example.m_hike.DatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.m_hike.Hike.Hike;
import com.example.m_hike.Observation.Observation;

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

    public static class ObservationTable implements BaseColumns{
        public static final String TABLE = "observations";
        public static final String ID_COLUMN ="observation_id";
        public static final String OBSERVATION_COLUMN = "observation";
        public static final String DATE_COLUMN ="date";
        public static final String TYPE_COLUMN ="type";
        public static final String DESCRIPTION_COLUMN = "description";
        public static final String HIKE_ID_COLUMN ="hike_id";
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

    private static final String OBSERVATION_TABLE_CREATE = String.format(
            "CREATE TABLE %s (" +
                "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "%s TEXT NOT NULL, " +
                "%s TEXT NOT NULL, " +
                "%s TEXT, " +
                "%s TEXT, " +
                "%s INTEGER, " +
                "FOREIGN KEY(%s) REFERENCES %s(%s) ON DELETE CASCADE);",
            ObservationTable.TABLE,
            ObservationTable.ID_COLUMN,
            ObservationTable.OBSERVATION_COLUMN,
            ObservationTable.DATE_COLUMN,
            ObservationTable.TYPE_COLUMN,
            ObservationTable.DESCRIPTION_COLUMN,
            ObservationTable.HIKE_ID_COLUMN,
            ObservationTable.HIKE_ID_COLUMN,

            HikeTable.TABLE, HikeTable.ID_COLUMN
    );

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,2);
        database =getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase database){
//        ! REMEMBER TO ADD THE SECOND TABLE !
        try {
            database.execSQL(HIKE_TABLE_CREATE);
            database.execSQL(OBSERVATION_TABLE_CREATE);
        } catch (Exception e) {
            Log.d("DatabaseHelper", "Error creating databases");
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //        Automatically called if db version number changes
        db.execSQL("DROP TABLE IF EXISTS " + HikeTable.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ObservationTable.TABLE);

        Log.w(this.getClass().getName(), DATABASE_NAME + " database has upgraded to version " + newVersion + " - old data lost");
        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }


    //    HIKE TABLE
    public long insertHikeDetails(Hike hike){
        ContentValues rowValues = NewHikeContentValues(hike);
        return database.insertOrThrow(HikeTable.TABLE,null,rowValues);
    }

    public void updateHikeNameById(Hike hike, int id){
        ContentValues rowValues = NewHikeContentValues(hike);
        database.update(HikeTable.TABLE,rowValues,HikeTable.ID_COLUMN+ " = ?", new String[]{String.valueOf(id)});
    }

    public void updateFavoriteStatus(int id, int favoriteStatus){
        ContentValues values = new ContentValues();
        values.put(HikeTable.FAVORITE_COLUMN, favoriteStatus);
        database.update(HikeTable.TABLE,values,HikeTable.ID_COLUMN+ " = ?", new String[]{String.valueOf(id)});
    }

    public Hike findHikeById(int id){
        String query = String.format("SELECT * FROM %s WHERE %s = ?",HikeTable.TABLE, HikeTable.ID_COLUMN);
        String[] selectionArgs = new String[]{String.valueOf(id)};
        Cursor result = database.rawQuery(query,selectionArgs);

        if(result.moveToFirst()){
            Hike hike = retrieveHike(result);
            result.close();
            return hike;
        }

        result.close();
        return null;
    }

    public void deleteHike(int id){
        database.delete(HikeTable.TABLE, HikeTable.ID_COLUMN + " = ?", new String[]{String.valueOf(id)});
    }
    public ArrayList<Hike> getHikes(){
        Cursor results = database.query(HikeTable.TABLE, null,
                null,null, null,null,HikeTable.ID_COLUMN);

        ArrayList<Hike> listHike = new ArrayList<>();
        results.moveToFirst();
        while(!results.isAfterLast()){

            listHike.add(retrieveHike(results));
            results.moveToNext();
        }
        return listHike;
    }

    private Hike retrieveHike(Cursor result){
        int hike_id = result.getInt(0);
        String name = result.getString(1);
        String location = result.getString(2);
        String date = result.getString(3);
        int parking = result.getInt(4);
        double length = result.getDouble(5);
        String difficulty = result.getString(6);
        String description = result.getString(7);
        int favorite = result.getInt(8);
        int completed = result.getInt(9);

        return new Hike(hike_id,name,location,date,parking,length,difficulty,description,favorite,completed);
    }
    private ContentValues NewHikeContentValues(Hike hike){
        ContentValues values = new ContentValues();
        values.put(HikeTable.NAME_COLUMN, hike.getName());
        values.put(HikeTable.LOCATION_COLUMN, hike.getLocation());
        values.put(HikeTable.DATE_COLUMN, hike.getDate());
        values.put(HikeTable.PARKING_COLUMN, hike.getParking());
        values.put(HikeTable.LENGTH_COLUMN, hike.getLength());
        values.put(HikeTable.DIFFICULTY_COLUMN, hike.getDifficulty());
        values.put(HikeTable.DESCRIPTION_COLUMN, hike.getDescription());
        values.put(HikeTable.FAVORITE_COLUMN, hike.getFavorite());
        values.put(HikeTable.COMPLETED_COLUMN, hike.getCompleted());

        return values;
    }

//    OBSERVATION TABLE
    public long insertObservation(Observation observation){
        ContentValues values = new ContentValues();
        values.put(ObservationTable.OBSERVATION_COLUMN,observation.getObservation());
        values.put(ObservationTable.DATE_COLUMN, observation.getDate());
        values.put(ObservationTable.TYPE_COLUMN,observation.getType());
        values.put(ObservationTable.DESCRIPTION_COLUMN,observation.getDescription());
        values.put(ObservationTable.HIKE_ID_COLUMN,observation.getHike_id());

        return database.insertOrThrow(ObservationTable.TABLE,null,values);
    }

    public ArrayList<Observation> getObservation(int hike_id){
        String selection = String.format("%s = ?", ObservationTable.HIKE_ID_COLUMN);
        String[] selectionArgs = new String[]{String.valueOf(hike_id)};
        String[] columns = new String[]{ObservationTable.OBSERVATION_COLUMN, ObservationTable.DATE_COLUMN, ObservationTable.TYPE_COLUMN, ObservationTable.DESCRIPTION_COLUMN};
        Cursor results = database.query(ObservationTable.TABLE,null,selection,selectionArgs,null,null,null);

        ArrayList<Observation> observations = new ArrayList<>();
//        results.moveToFirst();
        while (results.moveToNext()){
            observations.add(retrieveObservation(results));
//            results.moveToNext();
        }
        results.close();
        return observations;
    }

    private Observation retrieveObservation(Cursor cursor){
        int observation_id = cursor.getInt(0);
        String observation = cursor.getString(1);
        String date = cursor.getString(2);
        String type = cursor.getString(3);
        String description = cursor.getString(4);
        int hike_id = cursor.getInt(5);

        return new Observation(observation_id,observation,date,type,description,hike_id);
    }

    public void deleteObservation(int id){
        database.delete(ObservationTable.TABLE,ObservationTable.ID_COLUMN + " = ?", new String[]{String.valueOf(id)});
    }
}
