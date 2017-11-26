package com.gyfzyt.memoryshelf.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyfzyt.memoryshelf.Beans.movieBean.Casts;
import com.gyfzyt.memoryshelf.Beans.movieBean.Movie;
import com.gyfzyt.memoryshelf.R;
import com.gyfzyt.memoryshelf.views.GradeView;
import com.rengwuxian.materialedittext.validation.RegexpValidator;
import com.squareup.picasso.Picasso;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 高云帆 on 2017/11/18.
 * 电影详情界面
 */

public class MovieDetailActivity extends Activity
{
    private TextView movieName;
    private ImageView moviePic;
    private TextView movieDirector;
    private TextView movieGenres;
    private GradeView movieStar;
    private TextView movieCasts;
    private TextView movieSummary;
    private TextView moviePublishYear;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail_layout);
        Intent intent = getIntent();
        Movie movie = (Movie) intent.getSerializableExtra("movie");
        initView(movie);


    }

    private void initView(Movie movie)
    {
        movieName = (TextView) findViewById(R.id.movie_name);
        movieName.setText(movie.getTitle());

        moviePic = (ImageView) findViewById(R.id.movie_img);
        Picasso.with(this).load(movie.getImages().getLarge()).into(moviePic);

        movieDirector = (TextView) findViewById(R.id.director_name);
        String director = movie.getDirectors().get(0).getName();
        director=handleDirector(director);
        director = director + " 导演";
        movieDirector.setText(director);

        movieGenres = (TextView) findViewById(R.id.movie_genres);
        StringBuilder genres = new StringBuilder(movie.getGenres().toString());
        movieGenres.setText(genres.substring(1, genres.length() - 1));

        movieStar = (GradeView) findViewById(R.id.star);
        movieStar.setgradeNumber((float) movie.getRating().getAverage());

        movieCasts = (TextView) findViewById(R.id.movie_casts);
        StringBuilder casts = new StringBuilder();
        for (Casts cast : movie.getCasts()) {
            casts.append(cast.getName()).append(",");
        }
        casts.deleteCharAt(casts.length() - 1);
        movieCasts.setText(handleCasts(casts.toString()));

        movieSummary = (TextView) findViewById(R.id.movie_summary);
        movieSummary.setText(movie.getSummary());

        moviePublishYear = (TextView) findViewById(R.id.publish_year);
        String year = movie.getYear() + "年上映";
        moviePublishYear.setText(year);

    }

    private String handleDirector(String director)
    {
        return getContent(director);
    }

    private String handleCasts(String casts)
    {
        StringBuilder result = new StringBuilder();
        String[] castsOrigin = casts.split(",");
        for (String s : castsOrigin) {
            result.append(getContent(s)).append(",");
        }
        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }

    private String getContent(String origin) {
        int start = origin.indexOf("'");
        if(start==-1)
            start = origin.indexOf("\"");
        int end = origin.lastIndexOf("'");
        if(end==-1)
            end = origin.lastIndexOf("\"");
        if(end!=-1&&start!=-1)
            return origin.substring(start + 1, end);
        else return origin;
    }
}
