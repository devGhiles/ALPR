package com.esi.mxt07.pfe2017.alpr;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StolenCarsDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "stolen_cars"; // the name of our database
    private static final int DB_VERSION = 1; // the version of the database
    private static final String TABLE_NAME = "Plate";
    private static final String COLUMN_NAME = "PlateNumber";

    public StolenCarsDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateDatabase(db, oldVersion, newVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateDatabase(db, 0, DB_VERSION);
    }

    private void updateDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            db.execSQL("CREATE TABLE Plate (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "PlateNumber TEXT);");
            insertPlate(db, "AZE987");
            insertPlate(db, "WXC321");
        }
    }

    private void insertPlate(SQLiteDatabase db, String plateNumber) {
        ContentValues plateValues = new ContentValues();
        plateValues.put(COLUMN_NAME, plateNumber);
        db.insert(TABLE_NAME, null, plateValues);
    }
}
