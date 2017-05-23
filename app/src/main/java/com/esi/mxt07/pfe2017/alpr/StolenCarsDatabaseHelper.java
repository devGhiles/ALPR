package com.esi.mxt07.pfe2017.alpr;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StolenCarsDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "stolen_cars"; // the name of our database
    private static final int DB_VERSION = 1; // the version of the database

    public StolenCarsDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateDatabase(db, oldVersion, newVersion);
    }

    private void updateDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            db.execSQL("CREATE TABLE Plate ("
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "PlateNumber TEXT);");
            insertPlate(db, "AAA111");
            insertPlate(db, "AAA222");
            insertPlate(db, "AAA333");
            insertPlate(db, "AAA444");
            insertPlate(db, "AAA555");
            insertPlate(db, "AAA666");
            insertPlate(db, "AAA777");
            insertPlate(db, "AAA888");
            insertPlate(db, "AAA999");
            insertPlate(db, "AAA000");
            insertPlate(db, "BBB111");
            insertPlate(db, "BBB222");
            insertPlate(db, "BBB333");
            insertPlate(db, "BBB444");
            insertPlate(db, "BBB555");
            insertPlate(db, "BBB666");
        }
    }

    public void insertPlate(SQLiteDatabase db, String plateNumber) {
        ContentValues plateValues = new ContentValues();
        plateValues.put("PlateNumber", plateNumber);
        db.insert("Plate", null, plateValues);
    }

    public void deletePlate(SQLiteDatabase db, String plateNumber) {
        db.delete("Plate", "PlateNumber = ?", new String[] {plateNumber});
    }
}
