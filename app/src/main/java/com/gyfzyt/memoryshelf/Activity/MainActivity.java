package com.gyfzyt.memoryshelf.Activity;

import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.gyfzyt.memoryshelf.DB.MyDBHelper;
import com.gyfzyt.memoryshelf.R;

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
