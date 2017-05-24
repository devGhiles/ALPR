package com.esi.mxt07.pfe2017.alpr;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UsersDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "users";
    private static final int DB_VERSION = 1;

    public UsersDatabaseHelper(Context context) {
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
            db.execSQL("CREATE TABLE User ("
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "Username TEXT, "
                    + "PasswordSHA256 TEXT, "
                    + "IsAdmin NUMERIC);");
            insertUser(db, new User(0, "admin", "admin", true));
            insertUser(db, new User(0, "user", "user", false));
        }
    }

    public User getUser(SQLiteDatabase db, int id) {
        Cursor cursor = db.query("User", new String[]{"_id", "Username", "PasswordSHA256", "IsAdmin"},
                "_id = ?", new String[]{Integer.toString(id)}, null, null, null);
        if (cursor.moveToFirst()) {
            String username = cursor.getString(1);
            String passwordSha256 = cursor.getString(2);
            boolean isAdmin = (cursor.getInt(3) != 0);
            cursor.close();
            return new User(id, username, passwordSha256, isAdmin);
        } else {
            return null;
        }
    }

    public User getUser(SQLiteDatabase db, String username, String password) {
        String passwordSha256 = sha256(password);
        Log.v("Ghiles", passwordSha256);  // TODO: remove this line
        Cursor cursor = db.query("User", new String[]{"_id", "IsAdmin"},
                "Username = ? AND PasswordSHA256 = ?", new String[]{username, passwordSha256},
                null, null, null);
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            boolean isAdmin = (cursor.getInt(1) != 0);
            cursor.close();
            return new User(id, username, passwordSha256, isAdmin);
        } else {
            return null;
        }
    }

    public void insertUser(SQLiteDatabase db, User user) {
        ContentValues values = new ContentValues();
        values.put("Username", user.getUsername());
        values.put("PasswordSHA256", user.getPasswordSha256());
        values.put("IsAdmin", user.isAdmin());
        db.insert("User", null, values);
    }

    public void updateUser(SQLiteDatabase db, User user) {
        ContentValues values = new ContentValues();
        values.put("Username", user.getUsername());
        values.put("PasswordSHA256", user.getPasswordSha256());
        values.put("IsAdmin", user.isAdmin());
        db.update("User", values, "_id = ?", new String[]{Integer.toString(user.getId())});
    }

    public void deleteUser(SQLiteDatabase db, User user) {
        db.delete("User", "_id = ?", new String[]{Integer.toString(user.getId())});
    }

    private String sha256(String s) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(s.getBytes(Charset.forName("UTF-8")));
            return hash.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
