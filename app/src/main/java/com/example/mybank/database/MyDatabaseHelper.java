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
    private static final String DATABASE_NAME = "JavaBankSaV.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_clients";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "client_name";
    private static final String COLUMN_CPF = "client_cpf";
    private static final String COLUMN_DATE = "client_date";
    private static final String COLUMN_EMAIL = "client_email";
    private static final String COLUMN_PHONE = "client_phone";
    private static final String COLUMN_PASSWORD = "client_password";
    private static final String COLUMN_SALDO = "client_saldo";

    //ENDEREÇO
    private static final String COLUMN_CEP = "client_cep";
    private static final String COLUMN_STATE = "client_state";
    private static final String COLUMN_CITY = "client_city";
    private static final String COLUMN_DISTRICT = "client_district";
    private static final String COLUMN_ADDRESS = "client_address";
    private static final String COLUMN_NUMBER = "client_number";

    //RENDA
    private static final String COLUMN_RENDA_MENSAL = "client_renda";
    private static final String COLUMN_PATRIMONIO_LIQUIDO = "client_patrimonio";

    private static final String COLUMN_CHAVE_PIX = "client_chav_pix";

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
                        COLUMN_NUMBER + " TEXT, " +
                        COLUMN_RENDA_MENSAL + " REAL, " +
                        COLUMN_PATRIMONIO_LIQUIDO + " REAL, " +
                        COLUMN_SALDO + " REAL, " +
                        COLUMN_CHAVE_PIX + " TEXT);";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean checkEmailIsLogged(String email) {
        String query = "SELECT " + COLUMN_EMAIL + " FROM " + TABLE_NAME + " WHERE " + COLUMN_EMAIL + " = '" + email + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String emailBD = cursor.getString(0);
                if (emailBD.equals(email))
                    return true;
                else
                    return false;
            }
        }
        return false;
    }

    public Client findClientPorChavePix(String chave) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_CHAVE_PIX + " = '" + chave + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }

        return getClientData(cursor);
    }

    public boolean userLoginAuth(String email, String senha) {
        String query = "SELECT " + COLUMN_EMAIL + ", " + COLUMN_PASSWORD + " FROM " + TABLE_NAME + " WHERE " + COLUMN_EMAIL + " = '" + email + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String emailBD = cursor.getString(0);
                String senhaBD = cursor.getString(1);
                if (emailBD.equals(email)) {
                    return senhaBD.equals(senha);
                } else
                    return false;
            }
        }
        return false;
    }

    public boolean checkCpfIsLogged(String cpf) {
        String query = "SELECT " + COLUMN_CPF + " FROM " + TABLE_NAME + " WHERE " + COLUMN_CPF + " = '" + cpf + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String cpfDB = cursor.getString(0);
                if (cpfDB.equals(cpf))
                    return true;
                else
                    return false;
            }
        }
        return false;
    }

    public Client findClientByEmailAndPassword(String email, String password) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_EMAIL + " = '" + email + "'" +
                " AND " + COLUMN_PASSWORD + " = '" + password + "'";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }

        return getClientData(cursor);
    }

    public boolean addClient(Client newClient) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, newClient.getName());
        cv.put(COLUMN_CPF, newClient.getCpf());
        cv.put(COLUMN_DATE, newClient.getDate());
        cv.put(COLUMN_EMAIL, newClient.getEmail());
        cv.put(COLUMN_PHONE, newClient.getPhone());
        cv.put(COLUMN_PASSWORD, newClient.getSenha());

        cv.put(COLUMN_CEP, newClient.getCep());
        cv.put(COLUMN_STATE, newClient.getState());
        cv.put(COLUMN_CITY, newClient.getCity());
        cv.put(COLUMN_DISTRICT, newClient.getDistrict());
        cv.put(COLUMN_ADDRESS, newClient.getAddress());
        cv.put(COLUMN_NUMBER, newClient.getName());

        cv.put(COLUMN_RENDA_MENSAL, newClient.getRendaMensal());
        cv.put(COLUMN_PATRIMONIO_LIQUIDO, newClient.getPatrimonioLiquido());
        newClient.setChavePix(newClient.getEmail());
        cv.put(COLUMN_CHAVE_PIX, newClient.getChavePix());

        newClient.setSaldo(999.99d);
        cv.put(COLUMN_SALDO, newClient.getSaldo());

        Long result = db.insert(TABLE_NAME, null, cv);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public Client findClientById(Integer id) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = " + id + ";";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }

        return getClientData(cursor);
    }

    public boolean updateSaldo(Integer id, Double saldo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_SALDO, saldo);

        int result = db.update(TABLE_NAME, cv, COLUMN_ID + "=?", new String[]{id.toString()});
        if (result == -1) {
            return false;
        } else {
            return true;
        }
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

        cv.put(COLUMN_RENDA_MENSAL, newClient.getRendaMensal());
        cv.put(COLUMN_PATRIMONIO_LIQUIDO, newClient.getPatrimonioLiquido());
        cv.put(COLUMN_SALDO, newClient.getSaldo());

        int result = db.update(TABLE_NAME, cv, COLUMN_ID + "=?", new String[]{newClient.getId().toString()});
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean deleteClientById(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();

        int result = db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{id.toString()});
        if (result == -1) {
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

    private Client getClientData(Cursor cursor) {
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Client currentClient = new Client();
                Integer id;
                String name, cpf, date, phone, senha, email, chavePix;
                Double saldo;
                //Endereço
                String cep, state, city, district, address, number;
                //Renda
                Double renda, patrimonio;

                id = cursor.getInt(0);
                name = cursor.getString(1);
                cpf = cursor.getString(2);
                date = cursor.getString(3);
                email = cursor.getString(4);
                phone = cursor.getString(5);
                senha = cursor.getString(6);

                cep = cursor.getString(7);
                state = cursor.getString(8);
                city = cursor.getString(9);
                district = cursor.getString(10);
                address = cursor.getString(11);
                number = cursor.getString(12);

                renda = cursor.getDouble(13);
                patrimonio = cursor.getDouble(14);
                saldo = cursor.getDouble(15);


                currentClient.setId(id);
                currentClient.setName(name);
                currentClient.setCpf(cpf);
                currentClient.setDate(date);
                currentClient.setEmail(email);
                currentClient.setPhone(phone);
                currentClient.setSenha(senha);
                currentClient.setSaldo(saldo);

                currentClient.setCep(cep);
                currentClient.setState(state);
                currentClient.setCity(city);
                currentClient.setDistrict(district);
                currentClient.setAddress(address);
                currentClient.setNumber(number);
                currentClient.setSenha(senha);
                currentClient.setEmail(email);

                currentClient.setRendaMensal(renda);
                currentClient.setPatrimonioLiquido(patrimonio);
                return currentClient;
            }
        }
        return null;
    }
}
