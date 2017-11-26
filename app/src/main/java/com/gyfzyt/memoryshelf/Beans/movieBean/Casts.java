package com.gyfzyt.memoryshelf.Beans.movieBean;

import java.io.Serializable;

/**
 * 主演
 */
public class Casts implements Serializable {

    private String name;

    public Casts() {
    }

    public Casts(String name) {

        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Casts{" +
                "name='" + name + '\'' +
                '}';
    }
}
