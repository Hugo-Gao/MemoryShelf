package com.gyfzyt.memoryshelf.Activity;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.gyfzyt.memoryshelf.Beans.bookBean.Book;
import com.gyfzyt.memoryshelf.Beans.movieBean.Movie;
import com.gyfzyt.memoryshelf.DB.MyDBHelper;
import com.gyfzyt.memoryshelf.Dao.BookDBUtil;
import com.gyfzyt.memoryshelf.Dao.MovieDBUtil;
import com.gyfzyt.memoryshelf.R;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by Administrator on 2017/10/14.
 * 用户资料展示界面
 */

public class MeFragment extends android.support.v4.app.Fragment
{
    private View bookPage_include;
    private View movies_include;
    private View money_include;
    private View favoriteYear_include;
    private MyDBHelper dbHelper;
    private List<Book> bookList;
    private List<Movie> movieList;

    private LineChartView lineChart;
    int[] months = new int[6];//X轴的标注
    int[] pages = new int[6];//折线图中的坐标

    private List<PointValue> pointValues;
    private List<AxisValue> axisValues;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.my_frag, container, false);

        //从本地数据库中获取数据
        dbHelper = new MyDBHelper(getContext(), "shelfDB.db", null, 1);
        Log.i("tag", getContext().toString());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        bookList = BookDBUtil.searchForAll(db);
        db = dbHelper.getReadableDatabase();
        movieList = MovieDBUtil.searchForAll(db);

        initView(view);

        lineChart = (LineChartView)view.findViewById(R.id.line_chart_view);

        pages = new int[]{0,0,0,0,0,0};
        setMonths();
        setPages();

        setAxisLables();//设置x轴坐标
        setAxisPoints();//设置坐标
        initLineChart();//初始化

        db.close();
        return view;
    }

    /**
     * 设置pages数据
     */
    private void setPages(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("Books", null, null, null, null, null, null);
        List<Integer> page = new ArrayList<>();
        List<String> month = new ArrayList<>();
        while (cursor.moveToNext()) {
            page.add(cursor.getInt(cursor.getColumnIndex("pages")));
            month.add(cursor.getString(cursor.getColumnIndex("add_book_date")).split("-")[1]);
        }
        Log.i("tag", "month = "+month.toString());
        if (month.size() > 40){

        }else{
            for (int i = month.size()-1; i >= 0; i--){
                if(Integer.valueOf(month.get(i)) == months[0]){
                    pages[0] += page.get(i);
                    continue;
                }else {
                    if(Integer.valueOf(month.get(i)) == months[1]){
                        pages[1] += page.get(i);
                        continue;
                    }else {
                        if(Integer.valueOf(month.get(i)) == months[2]){
                            pages[2] += page.get(i);
                            continue;
                        }else {
                            if(Integer.valueOf(month.get(i)) == months[3]){
                                pages[3] += page.get(i);
                                continue;
                            }else{
                                if(Integer.valueOf(month.get(i)) == months[4]){
                                    pages[4] += page.get(i);
                                    continue;
                                }else{
                                    if(Integer.valueOf(month.get(i)) == months[5]){
                                        pages[5] += page.get(i);
                                        continue;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        cursor.close();
        db.close();
    }

    /**
     * 设置x轴数据（通过当前月份计算最近六个月）
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setMonths(){
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH);
        for (int i = 5; i >= 0; i--){
            if(month == 1){
                months[i] = 1;
                month = 13;
            }else{
                months[i] = month;
            }
            month--;
        }
    }

    /**
     * x轴坐标
     */
    public void setAxisLables() {
        axisValues = new ArrayList<AxisValue>();
        for (int i = 0; i < months.length; i++) {
            axisValues.add(new AxisValue(i).setLabel(String.valueOf(months[i])));
        }
    }

    public void setAxisPoints(){
        pointValues = new ArrayList<>();
        for (int i = 0; i < pages.length; i++) {
            pointValues.add(new PointValue(i, pages[i]));
        }
    }

    public void initLineChart(){
        Line line = new Line(pointValues).setColor(Color.WHITE).setCubic(false);  //折线的颜色
        List<Line> lines = new ArrayList<Line>();
        line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.SQUARE）
        line.setCubic(true);//曲线是否平滑
        line.setFilled(true);//是否填充曲线的面积
//      line.setHasLabels(true);//曲线的数据坐标是否加上备注
        line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line.setHasLines(true);//是否用直线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);

        //坐标轴
        Axis axisX = new Axis(); //X轴
        axisX.setTextColor(Color.WHITE);  //设置字体颜色
        axisX.setName("月份");  //表格名称
        axisX.setTextSize(7);//设置字体大小
        axisX.setMaxLabelChars(7);  //最多几个X轴坐标
        axisX.setValues(axisValues);  //填充X轴的坐标名称
        data.setAxisXBottom(axisX); //x 轴在底部


        Axis axisY = new Axis();  //Y轴
        axisY.setMaxLabelChars(7); //默认是3，只能看最后三个数字
        axisY.setName("总页数");//y轴标注
        axisY.setTextSize(7);//设置字体大小

        data.setAxisYLeft(axisY);  //Y轴设置在左边

        //设置行为属性，支持缩放、滑动以及平移
        lineChart.setInteractive(true);
        lineChart.setZoomType(ZoomType.HORIZONTAL_AND_VERTICAL);
        lineChart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        lineChart.setLineChartData(data);
        lineChart.setVisibility(View.VISIBLE);
    }
    private void initView(View v){
        bookPage_include = v.findViewById(R.id.book_page);
        TextView leftTextView = (TextView) bookPage_include.findViewById(R.id.me_item_left_text);
        TextView rightTextView = (TextView) bookPage_include.findViewById(R.id.me_item_right_text);
        leftTextView.setText("累计阅读");
        rightTextView.setText(computeReadPages(bookList)+"页");

        movies_include = v.findViewById(R.id.movies);
        TextView leftTextView2 = (TextView) movies_include.findViewById(R.id.me_item_left_text);
        TextView rightTextView2 = (TextView) movies_include.findViewById(R.id.me_item_right_text);
        leftTextView2.setText("累计观影");
        rightTextView2.setText(""+movieList.size());

        money_include = v.findViewById(R.id.book_money);
        TextView leftTextView3 = (TextView) money_include.findViewById(R.id.me_item_left_text);
        TextView rightTextView3 = (TextView) money_include.findViewById(R.id.me_item_right_text);
        leftTextView3.setText("书籍总花费");
        rightTextView3.setText("￥"+computeReadMoney(bookList));

        favoriteYear_include = v.findViewById(R.id.favorite_year);
        TextView leftTextView4 = (TextView) favoriteYear_include.findViewById(R.id.me_item_left_text);
        TextView rightTextView4 = (TextView) favoriteYear_include.findViewById(R.id.me_item_right_text);
        leftTextView4.setText("偏爱的年代");
        if(bookList.size() == 0 && movieList.size() == 0){
            rightTextView4.setText(" ");
        }else {
            int time =  computeTime(bookList, movieList);
            rightTextView4.setText((time/10+1)+"世纪"+time%10+"0年代");
        }

    }
    /**
     * 计算阅读总数
     * @param bookList bookList
     * @return pages
     */
    public int computeReadPages(List<Book> bookList){
        int pages = 0;
        for (Book book:bookList){
            pages += Integer.valueOf(book.getPages());
        }
        return pages;
    }
    /**
     * 计算阅读花费
     * @param bookList bookList
     * @return money
     */
    public float computeReadMoney(List<Book> bookList){
        float money = 0;
        String s;
        for (Book book:bookList){
            s = book.getPrice();
            if(s != null && s.length() > 0){
                char c = s.substring(0, 1).toCharArray()[0];
                if(c >= 'A' && c <= 'Z'){
                    money += Float.valueOf(s.substring(4, s.length()-1));
                }else{
                    money += Float.valueOf(s.substring(0, s.length()-2));
                }
            }
        }
        return money;
    }

    /**
     * 通过用户观看的书籍和电影发行年份，计算用户偏爱年代（1920-2020）
     * @param bookList
     * @param movieList
     * @return 年代（例：198 表示20世纪80年代）
     */
    public int computeTime(List<Book> bookList, List<Movie> movieList){
        List<Integer> timeList = new ArrayList<>();
        String s;
        int[] times = new int[10];

        for (Book book:bookList){
            s = book.getPubdate().substring(0, 4);
            timeList.add(Integer.valueOf(s));
        }
        for (Movie movie : movieList){
            s = movie.getYear().substring(0, 4);
            timeList.add(Integer.valueOf(s));
        }
        for(Integer time : timeList){
            if(time>=1920 && time<1930){
                times[0]++;
            }else if(time >= 1930 && time < 1940){
                times[1]++;
            }else if(time >= 1940 && time < 1950){
                times[2]++;
            }else if(time >= 1950 && time < 1960){
                times[3]++;
            }else if(time >= 1960 && time < 1970){
                times[4]++;
            }else if(time >= 1970 && time < 1980){
                times[5]++;
            }else if(time >= 1980 && time < 1990){
                times[6]++;
            }else if(time >= 1990 && time < 2000){
                times[7]++;
            }else if(time >= 2000 && time < 2010){
                times[8]++;
            }else if(time >= 2010 && time < 2020){
                times[9]++;
            }
        }
        int max = times[0];
        int index = 0;
        for (int i = 1; i < 10; i++){
            if(max < times[i]){
                index = i;
                max = times[i];
            }
        }
        return 192+index;
    }
}
