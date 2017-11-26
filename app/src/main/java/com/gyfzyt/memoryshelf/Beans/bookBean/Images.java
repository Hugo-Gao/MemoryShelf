package com.gyfzyt.memoryshelf.Beans.bookBean;

import java.io.Serializable;

public class Images implements Serializable{

    private String large;

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public Images(String large)
    {
        this.large = large;
    }

    @Override
    public String toString()
    {
        return "Images{" +
                "large='" + large + '\'' +
                '}';
    }
}
