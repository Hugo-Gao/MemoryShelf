package com.gyfzyt.memoryshelf.Dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gyfzyt.memoryshelf.Beans.movieBean.Casts;
import com.gyfzyt.memoryshelf.Beans.movieBean.Directors;
import com.gyfzyt.memoryshelf.Beans.movieBean.Images;
import com.gyfzyt.memoryshelf.Beans.movieBean.Movie;
import com.gyfzyt.memoryshelf.Beans.movieBean.MovieRating;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by 高云帆 on 2017/11/26.
 * 电影数据库操作类
 */

public class MovieDBUtil {
    public static void insertIntoDB(Movie movie, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put("movieName", movie.getTitle());
        values.put("rating", movie.getRating().getAverage());
        values.put("genres", movie.getGenres().toString());
        values.put("casts", movie.getCasts().toString());
        values.put("directors", movie.getDirectors().toString());
        values.put("year", movie.getYear());
        values.put("imgUrl", movie.getImages().getLarge());
        values.put("summary", movie.getSummary());
        values.put("movie_id", movie.getId());
        db.insert("movies", null, values);
        db.close();
    }


    public static void deleteInfoDB(Movie movie, SQLiteDatabase db) {
        String sql = "delete from Movies where movie_id = " + movie.getId();
        db.execSQL(sql);
        db.close();
    }

    public static void deleteInfoDB(String movie_id, SQLiteDatabase db) {
        String sql = "delete from Movies where movie_id = " + movie_id;
        db.execSQL(sql);
        db.close();
    }

    //获取所有行的id
    public static List<String> searchForId(SQLiteDatabase db) {
        List<String> res = new ArrayList<>();
        Cursor cursor = db.query("Movies", new String[]{"movie_id"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            res.add(cursor.getString(cursor.getColumnIndex("movie_id")));
        }
        cursor.close();
        db.close();
        return res;
    }


    /**
     * 返回数据库中所有数据
     * ContentValues values = new ContentValues();
     * values.put("movieName", movie.getTitle());
     * values.put("rating", movie.getRating().getAverage());
     * values.put("genres",movie.getGenres().toString());
     * values.put("casts",movie.getCasts().toString());
     * values.put("directors", movie.getDirectors().toString());
     * values.put("year", movie.getYear());
     * values.put("imgUrl",movie.getImages().getLarge());
     * values.put("summary", movie.getSummary());
     * values.put("movie_id",movie.getId());
     * db.insert("movies", null, values);
     * db.close();
     *
     * @param db
     * @return
     */
    public static List<Movie> searchForAll(SQLiteDatabase db)
    {
        List<Movie> res = new ArrayList<>();
        Cursor cursor = db.query("Movies", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Movie movie = new Movie();
            movie.setId(cursor.getString(cursor.getColumnIndex("movie_id")));
            movie.setTitle(cursor.getString(cursor.getColumnIndex("movieName")));
            movie.setRating(new MovieRating(cursor.getDouble(cursor.getColumnIndex("rating"))));
            movie.setGenres(ArrayToList(cursor.getString(cursor.getColumnIndex("genres")).split(",")));
            movie.setCasts(ArrayToCastsList(cursor.getString(cursor.getColumnIndex("casts")).split(",")));
            movie.setDirectors(ArrayToDirectorsList(cursor.getString(cursor.getColumnIndex("directors")).split(",")));
            movie.setYear(cursor.getString(cursor.getColumnIndex("year")));
            movie.setImages(new Images(cursor.getString(cursor.getColumnIndex("imgUrl"))));
            movie.setSummary(cursor.getString(cursor.getColumnIndex("summary")));
            res.add(movie);
        }
        cursor.close();
        db.close();
        Collections.reverse(res);
        return res;
    }

    private static List<String> ArrayToList(String[] strs) {
        List<String> list = new ArrayList<>();
        for (String str : strs) {
            str = str.trim();
            if(str.charAt(0)=='[')
            {
                list.add(str.substring(1));
            } else if (str.charAt(str.length() - 1) == ']') {
                list.add(str.substring(0,str.length()-1));
            }else {
                list.add(str);
            }
        }
        return list;
    }
    private static List<Casts> ArrayToCastsList(String[] strs) {
        List<String> list = new ArrayList<>();
        for (String str : strs) {
            str = str.trim();
            if(str.charAt(0)=='[')
            {
                list.add(str.substring(1));
            } else if (str.charAt(str.length() - 1) == ']') {
                list.add(str.substring(0,str.length()-1));
            }else {
                list.add(str);
            }
        }
        List<Casts> result = new ArrayList<>();
        for (String s : list)
        {
            result.add(new Casts(s));
        }
        return result;
    }
    private static List<Directors> ArrayToDirectorsList(String[] strs) {
        List<String> list = new ArrayList<>();
        for (String str : strs) {
            str = str.trim();
            if(str.charAt(0)=='[')
            {
                list.add(str.substring(1));
            } else if (str.charAt(str.length() - 1) == ']') {
                list.add(str.substring(0,str.length()-1));
            }else {
                list.add(str);
            }
        }
        List<Directors> result = new ArrayList<>();
        for (String s : list)
        {
            result.add(new Directors(s));
        }
        return result;
    }


}
