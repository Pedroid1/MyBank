package com.example.mybank.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.mybank.model.Client;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "Clients.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_clients";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "client_name";
    private static final String COLUMN_CPF = "client_cpf";
    private  static final String COLUMN_DATE = "client_date";
    private  static final String COLUMN_EMAIL = "client_email";
    private  static final String COLUMN_PHONE = "client_phone";
    private  static final String COLUMN_PASSWORD = "client_password";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME + " TEXT, " +
                        COLUMN_CPF + " TEXT, " +
                        COLUMN_DATE + " TEXT, " +
                        COLUMN_EMAIL + " TEXT, " +
                        COLUMN_PHONE + " TEXT, " +
                        COLUMN_PASSWORD + " INTEGER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Cursor findClientByEmailAndPassword(String email, Integer password) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_EMAIL + " = " + email +
                " AND " + COLUMN_PASSWORD + " = " + password;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public boolean addClient(Client newClient) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, newClient.getName());
        cv.put(COLUMN_CPF, newClient.getCpf());
        cv.put(COLUMN_DATE, newClient.getDate());
        cv.put(COLUMN_EMAIL, newClient.getEmail());
        cv.put(COLUMN_PHONE, newClient.getPhone());

        Long result = db.insert(TABLE_NAME, null, cv);

        if(result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor findClientById(Integer id) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public boolean updateClient(Client client) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, client.getName());
        cv.put(COLUMN_CPF, client.getCpf());
        cv.put(COLUMN_DATE, client.getDate());
        cv.put(COLUMN_EMAIL, client.getEmail());
        cv.put(COLUMN_PHONE, client.getPhone());

        int result = db.update(TABLE_NAME, cv, COLUMN_ID + "=?", new String[]{client.getId().toString()});
        if(result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean deleteClientById(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();

        int result = db.delete(TABLE_NAME,COLUMN_ID + "=?", new String[]{id.toString()});
        if(result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public void deleteAllData() {
        String query = "DELETE FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
    }
}
