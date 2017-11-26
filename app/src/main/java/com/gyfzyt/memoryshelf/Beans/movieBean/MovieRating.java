package com.gyfzyt.memoryshelf.Beans.movieBean;

import java.io.Serializable;

import static android.R.attr.max;

/**
 * Created by 张云天 on 2017/11/20.
 */

public class MovieRating implements Serializable{
    @Override
    public String toString() {
        return "MovieRating{" +
                "average=" + average +
                '}';
    }

    private double average; //豆瓣评分

    public MovieRating() {
    }

    public MovieRating(double average) {
        this.average = average;
    }

    public void setAverage(double average) {
        this.average = average;
    }
    public double getAverage() {
        return average;
    }
}
