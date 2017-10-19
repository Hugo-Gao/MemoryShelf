package com.gyfzyt.memoryshelf.Beans;

/**
 * 豆瓣评分
 */
public class Ratings {

    public Ratings(String average)
    {
        this.average = average;
    }

    private String average;

    @Override
    public String toString()
    {
        return "Ratings{" +
                "average='" + average + '\'' +
                '}';
    }

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }

}
