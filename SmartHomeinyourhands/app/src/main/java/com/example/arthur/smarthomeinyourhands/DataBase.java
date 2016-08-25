package com.example.arthur.smarthomeinyourhands;

/**
 * Created by arthur on 28/12/2015.
 */
public class DataBase {

    protected final static String databaseName = "SignIn.db";

     public final static String SIGN_IN_TABLE = "Sign_In";
     public final static String COLUMN_USERS_NAME = "sname";
     public final static String COLUMN_USERS_PASSWORD = "password";

    protected final static String CREATE_TABLE_SIGN_IN = "CREATE TABLE " + SIGN_IN_TABLE + "( "
            + COLUMN_USERS_PASSWORD + " TEXT NOT NULL , "
            + COLUMN_USERS_NAME + " TEXT PRIMARY KEY)";


    public final static String ROOM_TABLE = "Rooms";
    public final static String COLUMN_ROOM_USER = "userRoom";
    public final static String COLUMN_ROOM_NAME = "rname";

    protected final static String CREATE_TABLE_ROOMS = "CREATE TABLE " + ROOM_TABLE + "( "
            + COLUMN_ROOM_USER+ " TEXT, "
            + COLUMN_ROOM_NAME + " TEXT ,"
            + "PRIMARY KEY (" + COLUMN_ROOM_NAME + ", " +COLUMN_ROOM_USER+ "))";



    public final static String BUTTON_TABLE = "buttons";
    public final static String COLUMN_BUTTON_ROOM_NAME = "_brname";
    public final static String COLUMN_BUTTON_NAME = "_bname";
    public final static String COLUMN_BUTTON_PORT = "_port";

    protected final static String CREATE_TABLE_BUTTON = "CREATE TABLE " + BUTTON_TABLE + "( "
            + COLUMN_USERS_NAME + " TEXT, "
            + COLUMN_BUTTON_ROOM_NAME + " TEXT NOT NULL, "
            + COLUMN_BUTTON_NAME + " TEXT, "
            + COLUMN_BUTTON_PORT + " INT NOT NULL, "
            + "PRIMARY KEY (" + COLUMN_BUTTON_PORT + ", " + COLUMN_BUTTON_ROOM_NAME +" ,"+COLUMN_USERS_NAME + "))";
}
