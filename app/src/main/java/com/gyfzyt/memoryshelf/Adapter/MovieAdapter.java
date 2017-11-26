package com.gyfzyt.memoryshelf.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dd.CircularProgressButton;
import com.gyfzyt.memoryshelf.Beans.movieBean.Movie;
import com.gyfzyt.memoryshelf.DB.MyDBHelper;
import com.gyfzyt.memoryshelf.Dao.MovieDBUtil;
import com.gyfzyt.memoryshelf.Dao.SPUtil;
import com.gyfzyt.memoryshelf.R;
import com.gyfzyt.memoryshelf.views.GradeView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 高云帆 on 2017/11/26.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> implements View.OnClickListener
{
    private List<Movie> movieList;
    private Context context;
    private LayoutInflater layoutInflater;
    private MyDBHelper dbHelper;
    private List<String> idList;
    private OnItemClickListener onItemClickListener;

    public MovieAdapter(List<Movie> movies, Context context) {
        movieList = new ArrayList<>(movies);
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        dbHelper = new MyDBHelper(context, "shelfDB.db", null, 1);
        idList = MovieDBUtil.searchForId(dbHelper.getReadableDatabase());
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.movie_card, parent, false);
        view.setOnClickListener(this);
        return new MovieAdapterViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(final MovieAdapterViewHolder holder, int position) {
        final Movie movie = movieList.get(position);
        holder.movieName.setText(movie.getTitle());
        if(movie.getDirectors().size()>0)
            holder.movieDirector.setText(movie.getDirectors().get(0).getName());
        else
            holder.movieDirector.setText("");
        if(movie.getCasts().size()>0)
            holder.movieActor.setText(movie.getCasts().get(0).getName());
        else
            holder.movieActor.setText("");
        if (idList.contains(movie.getId())) {
            holder.button.setProgress(100);
        }
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.button.getProgress() == 100) {
                    MovieDBUtil.deleteInfoDB(movie, dbHelper.getWritableDatabase());
                    holder.button.setProgress(0);
                    SPUtil.deleteMovieChangeNum(context);
                } else {
                    MovieDBUtil.insertIntoDB(movie, dbHelper.getWritableDatabase());
                    holder.button.setProgress(100);
                    SPUtil.addMovieChangeNum(context);
                    Log.d("haha", "加上去");
                }

            }
        });
        Picasso.with(context).load(movie.getImages().getLarge()).into(holder.moviePic);
        holder.gradeView.setgradeNumber((float) movie.getRating().getAverage());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }


    public static class MovieAdapterViewHolder extends RecyclerView.ViewHolder {

        ImageView moviePic;
        TextView movieName;
        TextView movieDirector;
        TextView movieActor;
        CircularProgressButton button;
        GradeView gradeView;

        public MovieAdapterViewHolder(View itemView, final Context context) {
            super(itemView);
            moviePic = (ImageView) itemView.findViewById(R.id.movie_pic);
            movieName = (TextView) itemView.findViewById(R.id.movie_name);
            movieDirector = (TextView) itemView.findViewById(R.id.movie_director);
            movieActor = (TextView) itemView.findViewById(R.id.movie_actor_name);
            button = (CircularProgressButton) itemView.findViewById(R.id.add_movie_btn);
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
