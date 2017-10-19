package com.gyfzyt.memoryshelf.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Administrator on 2017/10/15.
 */

public class MyDBHelper extends SQLiteOpenHelper
{
    public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String sql="CREATE TABLE IF NOT EXISTS Books(_id integer PRIMARY KEY AUTOINCREMENT not NULL ," +
                "bookName TEXT," +
                "publishTime TEXT," +
                "rating REAL," +
                "tags TEXT," +
                "imgUrl TEXT," +
                "authorIntro TEXT," +
                "author TEXT," +
                "bookSummary TEXT," +
                "publisher TEXT," +
                "pages INTEGER," +
                "price TEXT," +
                "book_id TEXT)";
        db.execSQL(sql);
        Log.d("haah", "数据库执行完毕");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {

    }
}