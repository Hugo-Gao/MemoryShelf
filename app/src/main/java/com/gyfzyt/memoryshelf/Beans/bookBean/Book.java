package com.gyfzyt.memoryshelf.Beans.bookBean;

import java.io.Serializable;
import java.util.List;

public class Book implements Serializable {

    private Ratings rating;         //豆瓣评分
    private List<String> author;    //作者
    private String pubdate;         //出版时间
    private List<Tags> tags;
    private String origin_title;    //原作名
    private String pages;           //页数
    private Images images;          //Large Image
    private String publisher;       //出版社
    private String title;           //书名
    private String author_intro;    //作者简介
    private String summary;         //书籍简介
    private String price;           //价格
    private String id;
    private String tagStr;

    public String getTagStr()
    {
        return tagStr;
    }

    public void setTagStr(String tagStr)
    {
        this.tagStr = tagStr;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return "Book{" +
                "rating=" + rating +
                ", author=" + author +
                ", pubdate='" + pubdate + '\'' +
                ", tags=" + tags +
                ", origin_title='" + origin_title + '\'' +
                ", pages='" + pages + '\'' +
                ", images=" + images +
                ", publisher='" + publisher + '\'' +
                ", title='" + title + '\'' +
                ", author_intro='" + author_intro + '\'' +
                ", summary='" + summary + '\'' +
                ", price='" + price + '\'' +
                ", id='" + id + '\'' +
                ", tagStr='" + tagStr + '\'' +
                '}';
    }

    public Ratings getRating() {
        return rating;
    }

    public void setRating(Ratings rating) {
        this.rating = rating;
    }

    public String getAuthor_intro() {
        return author_intro;
    }

    public void setAuthor_intro(String author_intro) {
        this.author_intro = author_intro;
    }


    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public String getOrigin_title() {
        return origin_title;
    }

    public void setOrigin_title(String origin_title) {
        this.origin_title = origin_title;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public List<Tags> getTags() {
        return tags;
    }

    public void setTags(List<Tags> tags) {
        this.tags = tags;
    }

    public List<String> getAuthor() {
        return author;
    }

    public void setAuthor(List<String> author) {
        this.author = author;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
