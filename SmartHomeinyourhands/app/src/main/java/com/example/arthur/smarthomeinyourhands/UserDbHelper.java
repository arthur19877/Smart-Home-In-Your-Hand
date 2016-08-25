package com.example.arthur.smarthomeinyourhands;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by arthur on 29/12/2015.
 */
public class UserDbHelper {

    private static UserDbHelper instance = null;
    Context context;

    public static UserDbHelper getInstance(Context c) {
        if (instance == null) {
            instance = new UserDbHelper(c);
        }
        return instance;
    }

    DataBaseHelper helper;

    public UserDbHelper(Context c) {
        context = c;
        helper = new DataBaseHelper(context, DataBase.databaseName, null, 1);
    }

    private class DataBaseHelper extends SQLiteOpenHelper {
        public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(DataBase.CREATE_TABLE_SIGN_IN);
            sqLiteDatabase.execSQL(DataBase.CREATE_TABLE_BUTTON);
            sqLiteDatabase.execSQL(DataBase.CREATE_TABLE_ROOMS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }

        boolean addUser(String name, String password) {
            SQLiteDatabase userDB = helper.getWritableDatabase();
            ContentValues userVal = new ContentValues(2);
            userVal.put(DataBase.COLUMN_USERS_NAME, name);
            userVal.put(DataBase.COLUMN_USERS_PASSWORD, password);
            if (userDB.insert(DataBase.SIGN_IN_TABLE, null, userVal) == -1)
                return false;
            return true;

        }
        public Cursor checkUser(UserDbHelper db){
            SQLiteDatabase userDB = helper.getReadableDatabase();
            String [] coloumns = {DataBase.COLUMN_USERS_NAME, DataBase.COLUMN_USERS_PASSWORD};
            Cursor CR = userDB.query(DataBase.SIGN_IN_TABLE, coloumns, null, null, null, null, null);
            return CR;
        }

        public Cursor buttonList(UserDbHelper db){
            SQLiteDatabase userDB = helper.getReadableDatabase();
            String [] coloumns = {DataBase.COLUMN_BUTTON_NAME, DataBase.COLUMN_BUTTON_PORT, DataBase.COLUMN_BUTTON_ROOM_NAME};
            Cursor CR = userDB.query(DataBase.BUTTON_TABLE, coloumns, DataBase.COLUMN_USERS_NAME+"=?" + " and " + DataBase.COLUMN_BUTTON_ROOM_NAME+"=?", new String[] { UserDetails.getNameAndPass()[0],RoomDetails.getRoomName() }, null, null, null);
            return CR;
        }

    public Cursor roomList(UserDbHelper db){
        SQLiteDatabase userDB = helper.getReadableDatabase();
        String [] coloumns = {DataBase.COLUMN_ROOM_NAME, DataBase.COLUMN_ROOM_USER};
        Cursor CR = userDB.query(DataBase.ROOM_TABLE, coloumns,  DataBase.COLUMN_ROOM_USER+"=?", new String[] { UserDetails.getNameAndPass()[0] }, null, null, null);
        return CR;
    }

    public int roomListNumber(UserDbHelper db){
        SQLiteDatabase userDB = helper.getReadableDatabase();
        String [] coloumns = {DataBase.COLUMN_ROOM_NAME, DataBase.COLUMN_ROOM_USER};
        Cursor CR = userDB.query(DataBase.ROOM_TABLE, coloumns,  DataBase.COLUMN_ROOM_USER+"=?", new String[] { UserDetails.getNameAndPass()[0] }, null, null, null);
        return CR.getCount();
    }
        boolean addButton(String name, String port, String userName, String roomName) {
            SQLiteDatabase userDB = helper.getWritableDatabase();
            ContentValues userVal = new ContentValues(2);
            userVal.put(DataBase.COLUMN_BUTTON_ROOM_NAME, roomName);
            userVal.put(DataBase.COLUMN_USERS_NAME, userName);
            userVal.put(DataBase.COLUMN_BUTTON_NAME, name);
            userVal.put(DataBase.COLUMN_BUTTON_PORT, Integer.parseInt(port));
            if (userDB.insert(DataBase.BUTTON_TABLE, null, userVal) == -1)
                return false;
            return true;
    }

    boolean addRoom(String name, String userName) {
        SQLiteDatabase userDB = helper.getWritableDatabase();
        ContentValues userVal = new ContentValues(2);
        userVal.put(DataBase.COLUMN_ROOM_USER, userName);
        userVal.put(DataBase.COLUMN_ROOM_NAME, name);
        if (userDB.insert(DataBase.ROOM_TABLE, null, userVal) == -1)
            return false;
        return true;
    }
}
