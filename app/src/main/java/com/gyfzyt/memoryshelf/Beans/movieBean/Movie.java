package com.gyfzyt.memoryshelf.Beans.movieBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 张云天 on 2017/11/18.
 */

public class Movie implements Serializable {

    private MovieRating rating; //豆瓣评分
    private List<String> genres; //类型
    private String title; //中文名
    private List<Casts> casts; //主演
    private String original_title; //原名
    private List<Directors> directors; //导演
    private String year; //年代
    private Images images; //Large image
    private String summary;
    private String id;//豆瓣id

    @Override
    public String toString() {
        return "Movie{" +
                "rating=" + rating +
                ", genres=" + genres +
                ", title='" + title + '\'' +
                ", casts=" + casts +
                ", original_title='" + original_title + '\'' +
                ", directors=" + directors +
                ", year='" + year + '\'' +
                ", images=" + images +
                ", summary='" + summary + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public MovieRating getRating() {
        return rating;
    }

    public void setRating(MovieRating rating) {
        this.rating = rating;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }
    public List<String> getGenres() {
        return genres;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    public void setCasts(List<Casts> casts) {
        this.casts = casts;
    }
    public List<Casts> getCasts() {
        return casts;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }
    public String getOriginal_title() {
        return original_title;
    }

    public void setDirectors(List<Directors> directors) {
        this.directors = directors;
    }
    public List<Directors> getDirectors() {
        return directors;
    }

    public void setYear(String year) {
        this.year = year;
    }
    public String getYear() {
        return year;
    }

    public void setImages(Images images) {
        this.images = images;
    }
    public Images getImages() {
        return images;
    }

}
