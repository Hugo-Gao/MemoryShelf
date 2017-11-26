package com.gyfzyt.memoryshelf.Activity;

import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.gyfzyt.memoryshelf.Adapter.MainBookAdapter;
import com.gyfzyt.memoryshelf.Adapter.MainMovieAdapter;
import com.gyfzyt.memoryshelf.Beans.bookBean.Book;
import com.gyfzyt.memoryshelf.Beans.movieBean.Movie;
import com.gyfzyt.memoryshelf.DB.MyDBHelper;
import com.gyfzyt.memoryshelf.Dao.BookDBUtil;
import com.gyfzyt.memoryshelf.Dao.MovieDBUtil;
import com.gyfzyt.memoryshelf.Dao.SPUtil;
import com.gyfzyt.memoryshelf.R;

import java.util.List;

/**
 * 主Activity
 */
public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener
{
    private BottomNavigationView btView;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private HomeFragment homeFragment = new HomeFragment();
    private SearchFragment searchFragment = new SearchFragment();
    private MeFragment meFragment = new MeFragment();
    private SQLiteOpenHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new MyDBHelper(this,"shelfDB.db",null,1);
        initView();
    }

    private void initView()
    {
        btView = (BottomNavigationView) findViewById(R.id.bt_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.addOnPageChangeListener(this);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager())
        {
            @Override
            public Fragment getItem(int position)
            {
                switch (position)
                {
                    case 0:
                        return homeFragment;
                    case 1:
                        return searchFragment;
                    case 2:
                        return meFragment;
                }
                return null;
            }

            @Override
            public int getCount()
            {
                return 3;
            }
        });

        btView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                Log.d("haha", item.getOrder()+"");
                viewPager.setCurrentItem(item.getOrder());
                if(item.getOrder()==0)
                {
                    if (SPUtil.getBookChangeNum(MainActivity.this))
                    {
                        final List<Book> bookList = BookDBUtil.searchForAll(dbHelper.getReadableDatabase());
                        MainBookAdapter mainBookAdapter=new MainBookAdapter(bookList, MainActivity.this);
                        mainBookAdapter.setOnItemClickListener(new MainBookAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent = new Intent();
                                intent.putExtra("book", bookList.get(position));
                                intent.setClass(MainActivity.this, BookDetailActivity.class);
                                startActivity(intent);
                            }
                        });
                        homeFragment.bookListFragment.recyclerView.setAdapter(mainBookAdapter);
                        homeFragment.bookListFragment.recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        SPUtil.resetBookChangeNum(MainActivity.this);
                        Log.d("haha","重新加载");
                    }
                    if (SPUtil.getMovieChangeNum(MainActivity.this))
                    {
                        final List<Movie> movieList = MovieDBUtil.searchForAll(dbHelper.getReadableDatabase());
                        MainMovieAdapter mainMovieAdapter=new MainMovieAdapter(movieList, MainActivity.this);
                        mainMovieAdapter.setOnItemClickListener(new MainMovieAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent = new Intent();
                                intent.putExtra("movie", movieList.get(position));
                                intent.setClass(MainActivity.this, MovieDetailActivity.class);
                                startActivity(intent);
                            }
                        });
                        homeFragment.movieListFragment.recyclerView.setAdapter(mainMovieAdapter);
                        homeFragment.movieListFragment.recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        SPUtil.resetMovieChangeNum(MainActivity.this);
                        Log.d("haha","重新加载");
                    }
                }
                return true;
            }
        });
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
    {

    }

    @Override
    public void onPageSelected(int position)
    {
        btView.getMenu().getItem(position).setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state)
    {

    }


}
