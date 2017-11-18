package com.gyfzyt.memoryshelf.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.gyfzyt.memoryshelf.R;

/**
 * Created by 张云天 on 2017/10/25.
 */

public class GradeView extends View {

    private final int STAR_COUNT = 5;  //星星个数
    private int starSize;     //大小
    private int starDistance; //间距
    private float gradeNumber = 2.5f;   //评分
    private float gradeText = gradeNumber * 2; //显示评分文字
    private Bitmap grade;
    private Drawable nullGrade;
    private Paint paint;
    private Paint textPaint;


    public GradeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    /**
     * 初始化UI组件
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs){

        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.GradeBar);
        this.starDistance = (int) mTypedArray.getDimension(R.styleable.GradeBar_starDistance, 0);
        this.starSize = (int) mTypedArray.getDimension(R.styleable.GradeBar_starSize, 20);
        this.nullGrade = mTypedArray.getDrawable(R.styleable.GradeBar_nullGrade);
        this.grade =  drawableToBitmap(mTypedArray.getDrawable(R.styleable.GradeBar_Grade));


        mTypedArray.recycle();

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(grade, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));

        textPaint = new Paint();
        textPaint.setColor(Color.GRAY);
        textPaint.setStyle(Paint.Style.FILL);
        //textPaint.setStrokeWidth(5);
        textPaint.setTextSize(starSize-5);
    }

    /**
     * 设置显示的星星的分数
     *
     * @param number 分数
     */
    public void setgradeNumber(float number){

        gradeNumber = number/2;
        gradeText = number;
        invalidate();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(starSize * (STAR_COUNT + 2) + starDistance * STAR_COUNT, starSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (grade == null || nullGrade == null) {
            return;
        }
        for (int i = 0;i < STAR_COUNT;i++) {
            nullGrade.setBounds((starDistance + starSize) * i, 0, (starDistance + starSize) * i + starSize, starSize);
            nullGrade.draw(canvas);
        }
        if (gradeNumber > 1) {
            canvas.drawRect(0, 0, starSize, starSize, paint);
            if(gradeNumber-(int)(gradeNumber) == 0) {
                for (int i = 1; i < gradeNumber; i++) {
                    canvas.translate(starDistance + starSize, 0);
                    canvas.drawRect(0, 0, starSize, starSize, paint);
                }
                canvas.translate((6 - (int)gradeNumber)*starSize + (6 - (int)gradeNumber)*starDistance, 0);
            }else {
                for (int i = 1; i < gradeNumber - 1; i++) {
                    canvas.translate(starDistance + starSize, 0);
                    canvas.drawRect(0, 0, starSize, starSize, paint);
                }
                canvas.translate(starDistance + starSize, 0);
                canvas.drawRect(0, 0, starSize * (Math.round((gradeNumber - (int) (gradeNumber))*10)*1.0f/10), starSize, paint);
                canvas.translate((5 - (int)gradeNumber)*starSize + (5 - (int)gradeNumber)*starDistance, 0);
            }
        }else {
            canvas.drawRect(0, 0, starSize * gradeNumber, starSize, paint);
            canvas.translate(5 * starSize + 5 * starDistance, 0);
        }
        canvas.drawText("  "+gradeText, 0, starSize, textPaint);
    }

    /**
     * drawable转bitmap
     *
     * @param drawable
     * @return
     */
    private Bitmap drawableToBitmap(Drawable drawable)
    {
        if (drawable == null)return null;
        Bitmap bitmap = Bitmap.createBitmap(starSize, starSize, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, starSize, starSize);
        drawable.draw(canvas);
        return bitmap;
    }

}
