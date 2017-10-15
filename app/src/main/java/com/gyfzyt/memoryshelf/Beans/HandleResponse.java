package com.gyfzyt.memoryshelf.Beans;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

public class HandleResponse {

    public static Book handleBookResponse(String response){
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonBookArray = jsonObject.getJSONArray("books");
            //解析books标签的数据
            String bookContent = jsonBookArray.getJSONObject(0).toString();
            return new Gson().fromJson(bookContent, Book.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
