package com.gyfzyt.memoryshelf.Beans;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HandleResponse {

    public static List<Book> handleBookResponse(String response){
        List<Book> res = new ArrayList<>();
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonBookArray = jsonObject.getJSONArray("books");
            if(jsonBookArray.length()>=5)
            {
                //解析books标签的数据
                for (int i = 0; i < 5; i++)
                {
                    String bookContent = jsonBookArray.getJSONObject(i).toString();
                    res.add(new Gson().fromJson(bookContent, Book.class));
                }
            }else
            {
                for (int i = 0; i < jsonBookArray.length(); i++)
                {
                    String bookContent = jsonBookArray.getJSONObject(i).toString();
                    res.add(new Gson().fromJson(bookContent, Book.class));
                }
            }
            return res;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
