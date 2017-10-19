package com.gyfzyt.memoryshelf.Dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gyfzyt.memoryshelf.Beans.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/15.
 */

public class BookDBUtil
{

    public static void insertIntoDB(Book book, SQLiteDatabase db)
    {
        ContentValues values = new ContentValues();
        values.put("bookName", book.getTitle());
        values.put("publishTime", book.getPubdate());
        values.put("rating",Double.parseDouble(book.getRating().getAverage()));
        values.put("tags",book.getTags().toString());
        values.put("imgUrl", book.getImages().getLarge());
        values.put("authorIntro", book.getAuthor_intro());
        values.put("author",book.getAuthor().toString());
        values.put("bookSummary", book.getSummary());
        values.put("publisher", book.getPublisher());
        values.put("pages", book.getPages());
        values.put("price", book.getPrice());
        values.put("book_id",book.getId());
        db.insert("Books", null, values);
        db.close();
    }

    public static void deleteInfoDB(Book book, SQLiteDatabase db)
    {
        String sql = "delete from Books where book_id = " + book.getId();
        db.execSQL(sql);
        db.close();
    }

    public static List<String> searchForId(SQLiteDatabase db)
    {
        List<String> res = new ArrayList<>();
        Cursor cursor = db.query("Books", new String[] { "book_id"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            res.add(cursor.getString(cursor.getColumnIndex("book_id")));
        }
        cursor.close();
        db.close();
        return res;
    }
}
