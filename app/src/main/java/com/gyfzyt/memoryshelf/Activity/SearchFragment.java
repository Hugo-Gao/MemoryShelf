package com.gyfzyt.memoryshelf.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dd.CircularProgressButton;
import com.gyfzyt.memoryshelf.Adapter.BookAdapter;
import com.gyfzyt.memoryshelf.Adapter.MovieAdapter;
import com.gyfzyt.memoryshelf.Beans.bookBean.Book;
import com.gyfzyt.memoryshelf.Beans.HandleResponse;
import com.gyfzyt.memoryshelf.Beans.movieBean.Movie;
import com.gyfzyt.memoryshelf.R;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
/**
 * Created by Administrator on 2017/10/14.
 * 搜索界面
 */

public class SearchFragment extends android.support.v4.app.Fragment
{

    private CircularProgressButton movieBtn;
    private CircularProgressButton bookBtn;
    private MaterialEditText editText;
    private RecyclerView recyclerView;
    private static String BOOK_SEARCH_URL = "https://api.douban.com/v2/book/search?q=";
    private static String MOVIE_SEARCH_URL = "https://api.douban.com/v2/movie/search?q=";
    private OkHttpClient client = new OkHttpClient();
    private android.os.Handler handler=new android.os.Handler(new android.os.Handler.Callback()
    {
        @Override
        public boolean handleMessage(Message message)
        {
            switch (message.what)
            {
                case 0:
                    CircularProgressButton btn = (CircularProgressButton) message.obj;
                    btn.setErrorText("网络错误");
                    btn.setProgress(-1);
                    break;
                case 1://搜索图书成功,通知刷新界面
                    bookBtn.setProgress(100);
                    final List<Book> books = (List<Book>) message.obj;
                    BookAdapter adapter = new BookAdapter(books, getActivity());
                    adapter.setOnItemClickListener(new BookAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position)
                        {
                            Intent intent = new Intent();
                            intent.putExtra("book", books.get(position));
                            intent.setClass(getActivity(), BookDetailActivity.class);
                            startActivity(intent);
                        }
                    });
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    break;
                case 2://搜索电影成功,通知刷新界面
                    movieBtn.setProgress(100);
                    final List<Movie> movieList = (List<Movie>) message.obj;
                    MovieAdapter movieAdapter = new MovieAdapter(movieList, getActivity());
                    movieAdapter.setOnItemClickListener(new MovieAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position)
                        {
                            Intent intent = new Intent();
                            intent.putExtra("movie", movieList.get(position));
                            intent.setClass(getActivity(), MovieDetailActivity.class);
                            startActivity(intent);
                        }
                    });
                    recyclerView.setAdapter(movieAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            }
            return true;
        }
    });
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View view= inflater.inflate(R.layout.search_frag,container,false);
        initView(view);
        return view;
    }

    private void initView(View view)
    {
        movieBtn = (CircularProgressButton) view.findViewById(R.id.movie_btn);
        bookBtn = (CircularProgressButton) view.findViewById(R.id.book_btn);
        editText = (MaterialEditText) view.findViewById(R.id.edit_text);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        editText.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                bookBtn.setProgress(0);
                movieBtn.setProgress(0);
            }
        });
        bookBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(bookBtn.getProgress()!=0)
                {
                    bookBtn.setProgress(0);
                    return;
                }

                bookBtn.setProgress(50);
                String searchContent = String.valueOf(editText.getText());
                if(searchContent.length()==0)
                {
                    bookBtn.setProgress(-1);
                    bookBtn.setErrorText("请输入内容");
                    return ;
                }
                searchBook(searchContent);
            }
        });
        movieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(movieBtn.getProgress()!=0)
                {
                    movieBtn.setProgress(0);
                    return;
                }

                movieBtn.setProgress(50);
                String searchContent = String.valueOf(editText.getText());
                if(searchContent.length()==0)
                {
                    movieBtn.setProgress(-1);
                    movieBtn.setErrorText("请输入内容");
                    return ;
                }
                searchMovie(searchContent);
            }
        });
    }

    private void searchMovie(String text)
    {
        final String url=MOVIE_SEARCH_URL+text+"&count=5";
        Log.d("haha", url);
        Request request=new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = handler.obtainMessage();
                message.what=0;
                message.obj = movieBtn;
                handler.sendMessage(message);
                Log.d("haha", "解析失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonString = response.body().string();
                List<Movie> movies = HandleResponse.handleMovieResponse(jsonString);
                if(movies==null)
                {
                    Message message = handler.obtainMessage();
                    message.what=0;
                    message.obj=movieBtn;
                    handler.sendMessage(message);
                }else {
                    Log.d("haha", "获取" + movies.size() + "个条目");
                    Message message = handler.obtainMessage();
                    message.what=2;
                    message.obj=movies;
                    handler.sendMessage(message);
                }


            }
        });
    }

    private void searchBook(String text)
    {
        final String url=BOOK_SEARCH_URL+text+"&count=5";
        Log.d("haha", url);
        final Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                Message message = handler.obtainMessage();
                message.what=0;
                message.obj = bookBtn;
                handler.sendMessage(message);
                Log.d("haha", "解析失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                String jsonString = response.body().string();
                List<Book> books = HandleResponse.handleBookResponse(jsonString);
                if(books!=null)
                {
                    Message message = handler.obtainMessage();
                    message.what=1;
                    message.obj=books;
                    handler.sendMessage(message);
                }
                else
                {
                    Message message = handler.obtainMessage();
                    message.what=0;
                    message.obj = bookBtn;
                    handler.sendMessage(message);
                    Log.d("haha", "解析出错");
                }


            }
        });
    }


}
