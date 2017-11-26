package com.gyfzyt.memoryshelf.Beans;

import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.gyfzyt.memoryshelf.Beans.bookBean.Book;
import com.gyfzyt.memoryshelf.Beans.movieBean.Casts;
import com.gyfzyt.memoryshelf.Beans.movieBean.Directors;
import com.gyfzyt.memoryshelf.Beans.movieBean.Movie;
import com.gyfzyt.memoryshelf.Beans.movieBean.OneMovie;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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

    /**
     * 处理搜索到的电影信息的5条信息
     * @param response json字符串
     * @return Movie
     */
    public static  List<Movie> handleMovieResponse(String response){
        final List<Movie> res = new ArrayList<>();
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonBookArray = jsonObject.getJSONArray("subjects");
            //解析subjects标签的数据
            int n;
            n = (jsonBookArray.length() < 5 ? jsonBookArray.length() : 5);
            final AtomicInteger count=new AtomicInteger(n);
            for (int i = 0; i < n; i++) {
                String movieContent = jsonBookArray.getJSONObject(i).toString();
                final Movie movie = new Gson().fromJson(movieContent, Movie.class);
                if (movie.getCasts().size() == 0) {
                    List<Casts> castses = new ArrayList<>();
                    castses.add(new Casts(""));
                    movie.setCasts(castses);
                }
                if (movie.getDirectors().size() == 0) {
                    List<Directors> castses = new ArrayList<>();
                    castses.add(new Directors(""));
                    movie.setDirectors(castses);
                }
                res.add(movie);
                final int index = i;
                OkHttpClient client = new OkHttpClient();
                String summaryUrl = "https://api.douban.com/v2/movie/subject/"+movie.getId();
                Request request=new Request.Builder().url(summaryUrl).build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("haha", "获取Summary失败");
                        count.decrementAndGet();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String jsonString = response.body().string();
                        OneMovie oneMovie = HandleResponse.handleOneMovieResponse(jsonString);
                        res.get(index).setSummary(oneMovie.getSummary());
                        Log.d("haha", "summary获取成功");
                        count.decrementAndGet();
                    }
                });
            }
            while (count.get()!=0)
            {

            }
            return res;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 处理由电影id查询到的json数据
     * @param response json
     * @return OneMovie
     */
    public static OneMovie handleOneMovieResponse(String response){
        try{
            JSONObject jsonObject = new JSONObject(response);
            String oneMovieContent = jsonObject.toString();
            return new Gson().fromJson(oneMovieContent, OneMovie.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
