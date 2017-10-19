package com.gyfzyt.memoryshelf.Dao;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/10/19.
 */

public class SPUtil
{
    public static int addBookChangeNum(Context context)
    {
        SharedPreferences sp = context.getSharedPreferences("dataChange", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        int addNum = sp.getInt("bookAddNum", 0);
        addNum++;
        editor.putInt("bookAddNum", addNum);
        editor.apply();
        return addNum;
    }

    public static int deleteBookChangeNum(Context context)
    {
        SharedPreferences sp = context.getSharedPreferences("dataChange", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        int addNum = sp.getInt("bookAddNum", 0);
        addNum--;
        editor.putInt("bookAddNum", addNum);
        editor.apply();
        return addNum;
    }

    public static boolean getBookChangeNum(Context context)
    {
        SharedPreferences sp = context.getSharedPreferences("dataChange", Context.MODE_PRIVATE);
        int addNum = sp.getInt("bookAddNum", 0);
        return addNum > 0;
    }
}
