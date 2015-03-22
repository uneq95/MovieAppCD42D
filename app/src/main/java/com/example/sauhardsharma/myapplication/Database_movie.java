package com.example.sauhardsharma.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SANTOSH on 3/22/2015.
 */
public class Database_movie {

    static final String KEY_ROWID="_id";
    static final String KEY_MOV_NAME="mov_name";
    static final String KEY_MOV_URL="mov_url";

    static final String DATABASE_NAME = "data_movie";
    static final String DATABASE_TABLE = "mov_watchlist";
    static final int DATABASE_VERSION = 1;

    static final String DATABASE_CREATE = "create table "+DATABASE_TABLE+" ("+KEY_ROWID+" integer primary key autoincrement, "+KEY_MOV_NAME+" text, "+KEY_MOV_URL+" text not null);";
    final Context context;
    SQLiteDatabase db;
    DatabaseHelper dbhlp;

    public Database_movie(Context c){
        this.context=c;
        dbhlp= new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context){
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) throws SQLException {
            // TODO Auto-generated method stub
            db.execSQL(DATABASE_CREATE);
            //Log.d("OnCreate","Database on create method");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) throws SQLException {
            // TODO Auto-generated method stub
            db.execSQL("DROP TABLE IF EXISTS attendance");
            onCreate(db);

        }
    }

    public Database_movie open() throws SQLException{
        db=dbhlp.getWritableDatabase();
        //questionsfeed();
        return this;
    }
    public void close(){
        dbhlp.close();
    }

    public void insertDetails(String mov_nam,String mov_url){
        ContentValues contentValues=new ContentValues();
        contentValues.put(KEY_MOV_NAME, mov_nam);
        contentValues.put(KEY_MOV_URL, mov_url);

        db.insert(DATABASE_TABLE, null, contentValues);
    }

    public Cursor deleteItem(String id){

        Cursor c=null;
        try {
            c = db.rawQuery("DELETE FROM "+DATABASE_TABLE+" WHERE "+KEY_ROWID+"="+id+";", null);
            c.moveToFirst();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return c;
    }

    public List<String> displaylist()
    {
        Cursor c=null;
        List<String> mov_list=new ArrayList<String>();
        c = db.rawQuery(" SELECT "+KEY_MOV_NAME+" FROM "+DATABASE_TABLE+";", null);
        if(c.moveToFirst())
        {
            do {
                mov_list.add(c.getString(c.getColumnIndex(KEY_MOV_NAME)));


            }while(c.moveToNext());

        }
        else
             mov_list=null;

        return mov_list;
    }


}
