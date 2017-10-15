package com.gyfzyt.memoryshelf.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyfzyt.memoryshelf.R;

/**
 * Created by Administrator on 2017/10/14.
 */

public class MeFragment extends android.support.v4.app.Fragment
{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View view= inflater.inflate(R.layout.my_frag,container,false);

        return view;
    }
}
