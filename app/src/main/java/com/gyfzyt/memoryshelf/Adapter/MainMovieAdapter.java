package com.gyfzyt.memoryshelf.Adapter;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyfzyt.memoryshelf.Beans.movieBean.Movie;
import com.gyfzyt.memoryshelf.DB.MyDBHelper;
import com.gyfzyt.memoryshelf.Dao.MovieDBUtil;
import com.gyfzyt.memoryshelf.R;
import com.gyfzyt.memoryshelf.views.GradeView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/19.
 */

public class MainMovieAdapter extends RecyclerView.Adapter<MainMovieAdapter.MainMovieAdapterViewHolder> implements View.OnClickListener {
    private List<Movie> movieList;
    private Context context;
    private LayoutInflater layoutInflater;
    private MyDBHelper dbHelper;
    private OnItemClickListener onItemClickListener;

    public MainMovieAdapter(List<Movie> movieList, Context context)
    {
        this.movieList = movieList;
        this.context = context;
        this.layoutInflater=LayoutInflater.from(context);
        dbHelper = new MyDBHelper(context,"shelfDB.db",null,1);
    }

    @Override
    public MainMovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = layoutInflater.inflate(R.layout.movie_card_main, parent, false);
        view.setOnClickListener(this);
        return new MainMovieAdapterViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(MainMovieAdapterViewHolder holder, final int position)
    {
        final Movie movie = movieList.get(position);
        holder.movie_actor.setText(movie.getCasts().get(0).getName());
        Picasso.with(context).load(movie.getImages().getLarge()).into(holder.movie_pic);
        holder.fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                MovieDBUtil.deleteInfoDB(movie.getId(), dbHelper.getWritableDatabase());
                removeData(position);
            }
        });
        holder.gradeView.setgradeNumber((float) movie.getRating().getAverage());
        holder.itemView.setTag(position);
    }

    public void removeData(int position)
    {
        movieList.remove(position);
        Log.d("haha", "删除position" + position);
        this.notifyItemRemoved(position);
        if(position != movieList.size()){
            this.notifyItemRangeChanged(position, movieList.size() - position);
        }
    }

    @Override
    public int getItemCount()
    {
        return movieList.size();
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    public static class MainMovieAdapterViewHolder extends RecyclerView.ViewHolder {

        ImageView movie_pic;
        TextView movie_actor;
        FloatingActionButton fab;
        GradeView gradeView;
        public MainMovieAdapterViewHolder(View itemView, final Context context)
        {
            super(itemView);
            movie_actor = (TextView) itemView.findViewById(R.id.movie_main_actor);
            movie_pic = (ImageView) itemView.findViewById(R.id.movie_main_pic);
            fab = (FloatingActionButton) itemView.findViewById(R.id.movie_main_delete_btn);
            gradeView = (GradeView) itemView.findViewById(R.id.star);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener
    {
        void onItemClick(View view, int position);
    }
}
