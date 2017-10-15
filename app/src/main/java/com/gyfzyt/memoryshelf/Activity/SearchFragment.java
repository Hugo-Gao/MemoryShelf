package com.gyfzyt.memoryshelf.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyfzyt.memoryshelf.R;
import com.unstoppable.submitbuttonview.SubmitButton;

/**
 * Created by Administrator on 2017/10/14.
 */

public class SearchFragment extends android.support.v4.app.Fragment
{

    private SubmitButton movieBtn;
    private SubmitButton bookBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View view= inflater.inflate(R.layout.search_frag,container,false);
        initView(view);
        return view;
    }

    private void initView(View view)
    {
        movieBtn = (SubmitButton) view.findViewById(R.id.movie_btn);
        bookBtn = (SubmitButton) view.findViewById(R.id.book_btn);
        bookBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                bookBtn.doResult(true);
            }
        });
        bookBtn.setOnResultEndListener(new SubmitButton.OnResultEndListener()
        {
            @Override
            public void onResultEnd()
            {
                bookBtn.reset();
            }
        });
    }
}
