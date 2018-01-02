package com.gyfzyt.memoryshelf.Dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.gyfzyt.memoryshelf.Beans.bookBean.Book;
import com.gyfzyt.memoryshelf.Beans.bookBean.Images;
import com.gyfzyt.memoryshelf.Beans.bookBean.Ratings;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.media.CamcorderProfile.get;

/**
 * Created by Administrator on 2017/10/15.
 * 书籍数据库操作类
 */

public class BookDBUtil
{

    @RequiresApi(api = Build.VERSION_CODES.N)
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
        //获取系统日期
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        values.put("add_book_date", year+"-"+month+"-"+day);//添加用户加入书籍的日期
        db.insert("Books", null, values);
        db.close();
    }

    public static void deleteInfoDB(Book book, SQLiteDatabase db)
    {
        String sql = "delete from Books where book_id = " + book.getId();
        db.execSQL(sql);
        db.close();
    }
    public static void deleteInfoDB(String book_id, SQLiteDatabase db)
    {
        String sql = "delete from Books where book_id = " + book_id;
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


    /**
     * 返回数据库中所有数据
     * @param db
     * @return
     */
    public static List<Book> searchForAll(SQLiteDatabase db)
    {
        List<Book> res = new ArrayList<>();
        Cursor cursor = db.query("Books", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Book book = new Book();
            book.setTitle(cursor.getString(cursor.getColumnIndex("bookName")));
            book.setPubdate(cursor.getString(cursor.getColumnIndex("publishTime")));
            book.setRating(new Ratings(cursor.getDouble(cursor.getColumnIndex("rating"))+""));
            book.setTagStr(cursor.getString(cursor.getColumnIndex("tags")));
            book.setImages(new Images(cursor.getString(cursor.getColumnIndex("imgUrl"))));
            book.setAuthor_intro(cursor.getString(cursor.getColumnIndex("authorIntro")));
            String[] authors = cursor.getString(cursor.getColumnIndex("author")).split(",");
            List<String> authorList = new ArrayList<>();
            for (String author : authors)
            {
                authorList.add(author);
            }
            book.setAuthor(authorList);
            book.setSummary(cursor.getString(cursor.getColumnIndex("bookSummary")));
            book.setPublisher(cursor.getString(cursor.getColumnIndex("publisher")));
            book.setPages(cursor.getInt(cursor.getColumnIndex("pages"))+"");
            book.setPrice(cursor.getString(cursor.getColumnIndex("price")));
            book.setId(cursor.getString(cursor.getColumnIndex("book_id")));
            res.add(book);
        }
        cursor.close();
        db.close();
        Collections.reverse(res);
        return res;
    }
}
