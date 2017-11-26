package com.gyfzyt.memoryshelf.Beans.movieBean;

import java.io.Serializable;

/**
 * 由搜索到的电影id，查询具体的电影信息的实体类
 */

public class OneMovie implements Serializable{
    private String summary;//电影简介

    @Override
    public String toString() {
        return "OneMovie{" +
                "summary='" + summary + '\'' +
                '}';
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
