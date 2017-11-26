package com.gyfzyt.memoryshelf.Beans.movieBean;

import java.io.Serializable;

/**
 * 导演
 */
public class Directors implements Serializable {

    private String name;

    public Directors(String name) {
        this.name = name;
    }

    public Directors() {
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Directors{" +
                "name='" + name + '\'' +
                '}';
    }
}
