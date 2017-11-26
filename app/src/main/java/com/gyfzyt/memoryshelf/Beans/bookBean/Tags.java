package com.gyfzyt.memoryshelf.Beans.bookBean;

import java.io.Serializable;

/**
 * 标签（分类）
 */
public class Tags implements Serializable{

    private String name;
    private String title;

    @Override
    public String toString()
    {
        return "Tags{" +
                "name='" + name + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
