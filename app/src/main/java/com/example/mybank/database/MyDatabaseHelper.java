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
    private static final String DATABASE_NAME = "Client.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_clients";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "client_name";
    private static final String COLUMN_CPF = "client_cpf";
    private  static final String COLUMN_DATE = "client_date";
    private  static final String COLUMN_EMAIL = "client_email";
    private  static final String COLUMN_PHONE = "client_phone";
    private  static final String COLUMN_PASSWORD = "client_password";

    //ENDEREÇO
    private  static final String COLUMN_CEP = "client_cep";
    private  static final String COLUMN_STATE = "client_state";
    private  static final String COLUMN_CITY = "client_city";
    private  static final String COLUMN_DISTRICT = "client_district";
    private  static final String COLUMN_ADDRESS = "client_address";
    private  static final String COLUMN_NUMBER = "client_number";

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
                        COLUMN_PASSWORD + " TEXT, " +
                        COLUMN_CEP + " TEXT, " +
                        COLUMN_STATE + " TEXT, " +
                        COLUMN_CITY + " TEXT, " +
                        COLUMN_DISTRICT + " TEXT, " +
                        COLUMN_ADDRESS + " TEXT, " +
                        COLUMN_NUMBER + " TEXT);";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Cursor findClientByEmailAndPassword(String email, String password) {
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

        cv.put(COLUMN_CEP, newClient.getCep());
        cv.put(COLUMN_STATE, newClient.getState());
        cv.put(COLUMN_CITY, newClient.getCity());
        cv.put(COLUMN_DISTRICT, newClient.getDistrict());
        cv.put(COLUMN_ADDRESS, newClient.getAddress());

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

    public boolean updateClient(Client newClient) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, newClient.getName());
        cv.put(COLUMN_CPF, newClient.getCpf());
        cv.put(COLUMN_DATE, newClient.getDate());
        cv.put(COLUMN_EMAIL, newClient.getEmail());
        cv.put(COLUMN_PHONE, newClient.getPhone());

        cv.put(COLUMN_CEP, newClient.getCep());
        cv.put(COLUMN_STATE, newClient.getState());
        cv.put(COLUMN_CITY, newClient.getCity());
        cv.put(COLUMN_DISTRICT, newClient.getDistrict());
        cv.put(COLUMN_ADDRESS, newClient.getAddress());

        int result = db.update(TABLE_NAME, cv, COLUMN_ID + "=?", new String[]{newClient.getId().toString()});
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
