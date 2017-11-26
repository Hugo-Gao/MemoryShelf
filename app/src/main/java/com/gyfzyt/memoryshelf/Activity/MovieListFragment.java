package com.gyfzyt.memoryshelf.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyfzyt.memoryshelf.Adapter.MainMovieAdapter;
import com.gyfzyt.memoryshelf.Beans.movieBean.Movie;
import com.gyfzyt.memoryshelf.DB.MyDBHelper;
import com.gyfzyt.memoryshelf.Dao.MovieDBUtil;
import com.gyfzyt.memoryshelf.R;

import java.util.List;

/**
 * Created by Administrator on 2017/10/14.
 * 主界面电影列表
 */

public class MovieListFragment extends android.support.v4.app.Fragment
{
    public RecyclerView recyclerView;
    private MyDBHelper dbHelper;
    private SwipeRefreshLayout refreshLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View view= inflater.inflate(R.layout.movielist_frag,container,false);
        initView(view);
        return view;
    }

    private void initView(View view)
    {
        dbHelper = new MyDBHelper(getActivity(),"shelfDB.db",null,1);
        recyclerView = (RecyclerView) view.findViewById(R.id.movie_list);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.layout_swipe_refresh);
        final List<Movie> movieList = MovieDBUtil.searchForAll(dbHelper.getReadableDatabase());
        MainMovieAdapter mainMovieAdapter=new MainMovieAdapter(movieList, getActivity());
        mainMovieAdapter.setOnItemClickListener(new MainMovieAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("movie", movieList.get(position));
                intent.setClass(getActivity(), MovieDetailActivity.class);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(mainMovieAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                List<Movie> movieList = MovieDBUtil.searchForAll(dbHelper.getReadableDatabase());
                recyclerView.setAdapter(new MainMovieAdapter(movieList, getActivity()));
                refreshLayout.setRefreshing(false);
            }
        });

    }

}
