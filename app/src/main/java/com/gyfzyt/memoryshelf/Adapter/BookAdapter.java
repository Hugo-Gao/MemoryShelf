package com.gyfzyt.memoryshelf.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dd.CircularProgressButton;
import com.gyfzyt.memoryshelf.Beans.bookBean.Book;
import com.gyfzyt.memoryshelf.DB.MyDBHelper;
import com.gyfzyt.memoryshelf.Dao.BookDBUtil;
import com.gyfzyt.memoryshelf.Dao.SPUtil;
import com.gyfzyt.memoryshelf.R;
import com.gyfzyt.memoryshelf.views.GradeView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/15.
 */

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookAdapterViewHolder> implements View.OnClickListener {
    private List<Book> bookList;
    private Context context;
    private LayoutInflater layoutInflater;
    private MyDBHelper dbHelper;
    private List<String> idList;
    private OnItemClickListener onItemClickListener;



    public BookAdapter(List<Book> books, Context context) {
        bookList = new ArrayList<>(books);
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        dbHelper = new MyDBHelper(context, "shelfDB.db", null, 1);
        idList = BookDBUtil.searchForId(dbHelper.getReadableDatabase());
    }

    @Override
    public BookAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.book_card, parent, false);
        view.setOnClickListener(this);
        return new BookAdapterViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(final BookAdapterViewHolder holder, final int position) {
        final Book book = bookList.get(position);
        List<String> list = new ArrayList<>(book.getAuthor());
        if (list.size() > 0)
            holder.book_author.setText(list.get(0) + " 著");
        holder.book_name.setText(book.getTitle());
        holder.book_price.setText("￥" + book.getPrice());
        if (idList.contains(book.getId())) {
            holder.button.setProgress(100);
        }
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.button.getProgress() == 100) {
                    BookDBUtil.deleteInfoDB(book, dbHelper.getWritableDatabase());
                    holder.button.setProgress(0);
                    SPUtil.deleteBookChangeNum(context);
                } else {
                    BookDBUtil.insertIntoDB(book, dbHelper.getWritableDatabase());
                    holder.button.setProgress(100);
                    SPUtil.addBookChangeNum(context);
                    Log.d("haha", "加上去");
                }

            }
        });
        Picasso.with(context).load(book.getImages().getLarge()).into(holder.book_pic);
        holder.gradeView.setgradeNumber(Float.parseFloat(book.getRating().getAverage()));
        holder.itemView.setTag(position);
        Log.d("haha", "adapter执行完毕");
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }


    public static class BookAdapterViewHolder extends RecyclerView.ViewHolder {

        ImageView book_pic;
        TextView book_name;
        TextView book_price;
        TextView book_author;
        CircularProgressButton button;
        GradeView gradeView;

        public BookAdapterViewHolder(View itemView, final Context context) {
            super(itemView);
            book_pic = (ImageView) itemView.findViewById(R.id.book_pic);
            book_name = (TextView) itemView.findViewById(R.id.book_name);
            book_price = (TextView) itemView.findViewById(R.id.book_price);
            book_author = (TextView) itemView.findViewById(R.id.author_name);
            button = (CircularProgressButton) itemView.findViewById(R.id.add_book_btn);
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
