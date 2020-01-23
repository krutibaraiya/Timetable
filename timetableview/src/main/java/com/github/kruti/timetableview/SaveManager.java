package com.github.kruti.timetableview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.HashMap;


public class SaveManager extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "mylist.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "mylist_data";

    public static  final String COL_ID ="_id";
    public static final String COL1 = "subject";
    public static final String COL2 = "classroom";
   public static final String COL3 = "professor";


    public SaveManager(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" +
                COL_ID + " INTEGER PRIMARY KEY, " +
                COL1 + " TEXT, " +
                COL2 + " TEXT, " +
                COL3 + " TEXT)"
        );
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertData(String COL1 , String COL2, String COL3) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put(COL_ID, id);
        contentValues.put(COL1, "subject");
        contentValues.put(COL2, "classroom");
        contentValues.put(COL3, "professor");
        db.insert(TABLE_NAME, null, contentValues);





    }

    /*public void insertData(String COL1, String COL2,String COL3){
        SQLiteDatabase db = getWritableDatabase();
        String sql= "INSERT INTO TABLE_NAME VALUES (?,?,?,NULL)";
        SQLiteStatement statement= db.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, COL1);
        statement.bindString(2, COL2);
        statement.bindString(3, COL3);
        statement.executeInsert();
    }*/

    public boolean updateData(Integer id, String subject, String classroom,String professor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, subject);
        contentValues.put(COL2, classroom);
        contentValues.put(COL3, professor);
        db.update(TABLE_NAME, contentValues,COL_ID + " = ? ",new String[]{ Integer.toString(id) });
        return true;
    }

    public Cursor getContent(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " +
                COL_ID + "=?", new String[]{Integer.toString(id)});
        return res;
    }

    public Cursor getAllContent() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME, null );

        return res;
    }


}