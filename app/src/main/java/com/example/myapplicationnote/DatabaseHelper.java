package com.example.myapplicationnote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import org.mindrot.jbcrypt.BCrypt;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "users.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_PASSWORD + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }


    public boolean addUser(String name, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Vérifier si l'utilisateur existe déjà
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_EMAIL + " = ?", new String[]{email});
        if (cursor.moveToFirst()) {
            cursor.close();
            return false; // Utilisateur déjà existant
        }

        // Hacher le mot de passe avant de l'enregistrer
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, hashedPassword);  // Stocker le mot de passe haché

        long result = db.insert(TABLE_USERS, null, values);
        cursor.close();

        return result != -1; // Retourne true si l'insertion est réussie
    }


    public boolean authenticateUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_EMAIL + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});

        boolean result = false;

        if (cursor.moveToFirst()) {
            String storedHashedPassword = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));

            // Comparer le mot de passe fourni avec le mot de passe haché stocké
            if (BCrypt.checkpw(password, storedHashedPassword)) {
                result = true;  // Authentification réussie
            }
        }

        cursor.close();
        return result;
    }
}