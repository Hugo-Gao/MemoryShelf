package com.gyfzyt.memoryshelf.Dao;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 高云帆 on 2017/10/19.
 * 此类为了判断是否在搜索界面中有新的书籍加入到数据库，如果有，则返回true通知刷新主界面
 */

public class SPUtil {

    public static int addBookChangeNum(Context context) {
        SharedPreferences sp = context.getSharedPreferences("dataChange", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        int addNum = sp.getInt("bookAddNum", 0);
        editor.remove("bookAddNum");
        addNum++;
        editor.putInt("bookAddNum", addNum);
        editor.apply();
        return addNum;
    }

    public static int deleteBookChangeNum(Context context) {
        SharedPreferences sp = context.getSharedPreferences("dataChange", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        int addNum = sp.getInt("bookAddNum", 0);
        if(addNum>0)
            addNum--;
        editor.putInt("bookAddNum", addNum);
        editor.apply();
        return addNum;
    }

    public static boolean getBookChangeNum(Context context) {
        SharedPreferences sp = context.getSharedPreferences("dataChange", Context.MODE_PRIVATE);
        int addNum = sp.getInt("bookAddNum", 0);
        return addNum > 0;
    }

    public static void resetBookChangeNum(Context context) {
        SharedPreferences sp = context.getSharedPreferences("dataChange", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("bookAddNum", 0);
        editor.apply();
    }



    public static int addMovieChangeNum(Context context) {
        SharedPreferences sp = context.getSharedPreferences("dataChange", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        int addNum = sp.getInt("movieAddNum", 0);
        editor.remove("movieAddNum");
        addNum++;
        editor.putInt("movieAddNum", addNum);
        editor.apply();
        return addNum;
    }

    public static int deleteMovieChangeNum(Context context) {
        SharedPreferences sp = context.getSharedPreferences("dataChange", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        int addNum = sp.getInt("movieAddNum", 0);
        if(addNum>0)
            addNum--;
        editor.putInt("movieAddNum", addNum);
        editor.apply();
        return addNum;
    }

    public static boolean getMovieChangeNum(Context context) {
        SharedPreferences sp = context.getSharedPreferences("dataChange", Context.MODE_PRIVATE);
        int addNum = sp.getInt("movieAddNum", 0);
        return addNum > 0;
    }

    public static void resetMovieChangeNum(Context context) {
        SharedPreferences sp = context.getSharedPreferences("dataChange", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("movieAddNum", 0);
        editor.apply();
    }
}
