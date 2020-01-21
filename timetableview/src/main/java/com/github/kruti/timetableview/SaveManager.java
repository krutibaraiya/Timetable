package com.github.kruti.timetableview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;




public class SaveManager extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "mylist.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "mylist_data";
    public static final String COL1 = "subject";
    public static final String COL2 = "classroom";
   public static final String COL3 = "professor";


    public SaveManager(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" +
                COL1 + " TEXT PRIMARY KEY, " +
                COL2 + " TEXT, " +
                COL3 + " TEXT)"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String COL1 , String COL2, String COL3) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, "subject");
        contentValues.put(COL2, "classroom");
        contentValues.put(COL3, "professor");
        db.insert(TABLE_NAME, null, contentValues);
        return true;




    }

    public boolean updateData(String subject, String classroom,String professor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, subject);
        contentValues.put(COL2, classroom);
        contentValues.put(COL3, professor);
        db.update(TABLE_NAME, contentValues,COL1 + " = ? ",new String[]{ String.valueOf(COL1) });
        return true;
    }

    public Cursor getAllContent() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME, null );
        return res;
    }
}