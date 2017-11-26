package com.gyfzyt.memoryshelf.Beans.movieBean;

import java.io.Serializable;

/**
 * 电影封面（large）
 */
public class Images implements Serializable {

    @Override
    public String toString() {
        return "Images{" +
                "large='" + large + '\'' +
                '}';
    }

    private String large;

    public Images(String large) {
        this.large = large;
    }

    public Images() {
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }


}
