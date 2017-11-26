package com.gyfzyt.memoryshelf.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.gyfzyt.memoryshelf.R;

/**
 * Created by 高云帆 on 2017/11/26.
 */

public class WelcomeActivity extends Activity
{
    static Activity instance ;
    private TextView title;
    private android.os.Handler handler = new android.os.Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            return true;
        }
    });
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wel_activity);
        instance = this;
        title = (TextView) findViewById(R.id.title);
        AssetManager mgr=getAssets();// 得到 AssetManager
        Typeface tf= Typeface.createFromAsset(mgr, "fonts/Pacifico.ttf");// 根据路径得到 Typeface
        title.setTypeface(tf);// 设置字体
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(2000);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }).start();
    }
}
