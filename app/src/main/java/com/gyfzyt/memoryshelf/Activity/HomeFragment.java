package com.gyfzyt.memoryshelf.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyfzyt.memoryshelf.R;

/**
 * Created by Administrator on 2017/10/14.
 * 包含bookListFragment和movieListFragment的主页
 */

public class HomeFragment extends android.support.v4.app.Fragment
{
    private ViewPager viewPager;
    private TabLayout tabLayout;
    public BookListFragment bookListFragment = new BookListFragment();
    public MovieListFragment movieListFragment = new MovieListFragment();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View view= inflater.inflate(R.layout.home_frag,container,false);

        initView(view);

        return view;
    }

    private void initView(View view)
    {
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);

        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager())
        {
            @Override
            public Fragment getItem(int position)
            {
                switch (position)
                {
                    case 0:
                        return bookListFragment;
                    case 1:
                        return movieListFragment;
                }
                return null;
            }
            @Override
            public int getCount()
            {
                return 2;
            }
        });
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                if(tab.getText().equals("书柜"))
                {
                    viewPager.setCurrentItem(0);
                }
                else if (tab.getText().equals("影史"))
                {
                    viewPager.setCurrentItem(1);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {

            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {
                tabLayout.getTabAt(position).select();
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });
    }


}
