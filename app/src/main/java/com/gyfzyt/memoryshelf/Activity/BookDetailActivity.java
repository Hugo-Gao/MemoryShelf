package com.gyfzyt.memoryshelf.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyfzyt.memoryshelf.Beans.bookBean.Book;
import com.gyfzyt.memoryshelf.R;
import com.gyfzyt.memoryshelf.views.GradeView;
import com.squareup.picasso.Picasso;

/**
 * Created by 高云帆 on 2017/11/18.
 * 书籍详情界面
 */

public class BookDetailActivity extends Activity {

    private TextView bookName;
    private ImageView bookImg;
    private TextView authorName;
    private TextView price;
    private GradeView star;
    private TextView pages;
    private TextView authorIntro;
    private TextView bookSummary;
    private TextView publisher;
    private TextView publishDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_detail_layout);
        Intent intent = getIntent();
        Book book = (Book) intent.getSerializableExtra("book");
        initView(book);


    }

    private void initView(Book book)
    {
        bookName = (TextView) findViewById(R.id.book_name);
        bookName.setText(book.getTitle());
        bookImg = (ImageView) findViewById(R.id.book_img);
        Picasso.with(this).load(book.getImages().getLarge()).into(bookImg);
        authorName = (TextView) findViewById(R.id.author_name);
        authorName.setText(book.getAuthor().get(0));
        price = (TextView) findViewById(R.id.book_price);
        price.setText(book.getPrice());
        star = (GradeView) findViewById(R.id.star);
        star.setgradeNumber(Float.parseFloat(book.getRating().getAverage()));
        pages = (TextView) findViewById(R.id.book_pages);
        pages.setText(book.getPages());
        authorIntro = (TextView) findViewById(R.id.author_intro);
        authorIntro.setText(book.getAuthor_intro());
        bookSummary = (TextView) findViewById(R.id.book_summary);
        bookSummary.setText(book.getSummary());
        publisher = (TextView) findViewById(R.id.publisher);
        publisher.setText(book.getPublisher());
        publishDate = (TextView) findViewById(R.id.publish_date);
        publishDate.setText(book.getPubdate()+"出版");
    }
}
