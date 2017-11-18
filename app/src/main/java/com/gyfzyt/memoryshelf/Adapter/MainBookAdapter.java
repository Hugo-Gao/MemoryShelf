package com.gyfzyt.memoryshelf.Adapter;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyfzyt.memoryshelf.Beans.Book;
import com.gyfzyt.memoryshelf.DB.MyDBHelper;
import com.gyfzyt.memoryshelf.Dao.BookDBUtil;
import com.gyfzyt.memoryshelf.R;
import com.gyfzyt.memoryshelf.views.GradeView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/19.
 */

public class MainBookAdapter extends RecyclerView.Adapter<MainBookAdapter.MainBookAdapterViewHolder> implements View.OnClickListener {
    private List<Book> bookList;
    private Context context;
    private LayoutInflater layoutInflater;
    private MyDBHelper dbHelper;
    private OnItemClickListener onItemClickListener;

    public MainBookAdapter(List<Book> bookList, Context context)
    {
        this.bookList = bookList;
        this.context = context;
        this.layoutInflater=LayoutInflater.from(context);
        dbHelper = new MyDBHelper(context,"shelfDB.db",null,1);
    }

    @Override
    public MainBookAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = layoutInflater.inflate(R.layout.book_card_main, parent, false);
        view.setOnClickListener(this);
        return new MainBookAdapterViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(MainBookAdapterViewHolder holder, final int position)
    {
        final Book book = bookList.get(position);

        List<String> list = new ArrayList<>(book.getAuthor());
        holder.book_author.setText(book.getTitle()+"  "+list.get(0) + " 著");
        Picasso.with(context).load(book.getImages().getLarge()).into(holder.book_pic);
        holder.fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                BookDBUtil.deleteInfoDB(book.getId(), dbHelper.getWritableDatabase());
                removeData(position);
            }
        });
        holder.gradeView.setgradeNumber(Float.parseFloat(book.getRating().getAverage()));
        holder.itemView.setTag(position);
    }

    public void removeData(int position)
    {
        bookList.remove(position);
        Log.d("haha", "删除position" + position);
        this.notifyItemRemoved(position);
        if(position != bookList.size()){
            this.notifyItemRangeChanged(position, bookList.size() - position);
        }
    }

    @Override
    public int getItemCount()
    {
        return bookList.size();
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    public static class MainBookAdapterViewHolder extends RecyclerView.ViewHolder {

        ImageView book_pic;
        TextView book_author;
        FloatingActionButton fab;
        GradeView gradeView;
        public MainBookAdapterViewHolder(View itemView, final Context context)
        {
            super(itemView);
            book_author = (TextView) itemView.findViewById(R.id.book_main_author);
            book_pic = (ImageView) itemView.findViewById(R.id.book_main_pic);
            fab = (FloatingActionButton) itemView.findViewById(R.id.book_main_delete_btn);
            gradeView = (GradeView) itemView.findViewById(R.id.star);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener
    {
        void onItemClick(View view, int position);
    }
}
