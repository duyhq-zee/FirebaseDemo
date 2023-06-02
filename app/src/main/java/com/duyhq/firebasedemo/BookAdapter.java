package com.duyhq.firebasedemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class BookAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Book> bookList;

    public BookAdapter(Context context, int layout, List<Book> bookList) {
        this.context = context;
        this.layout = layout;
        this.bookList = bookList;
    }

    @Override
    public int getCount() {
        return bookList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(layout, null);

        TextView nameTextView = convertView.findViewById(R.id.name_tv);
        ImageView image_delete = convertView.findViewById(R.id.image_delete);
        ImageView image_edit = convertView.findViewById(R.id.image_edit);

        Book b = bookList.get(position);

        nameTextView.setText(b.name);

        image_edit.setOnClickListener(v -> {
            MainActivity.getInstance().onUpdateButtonClick(b.key);
        });

        image_delete.setOnClickListener(v -> {
            MainActivity.getInstance().deleteBookByPosition(position);
        });

        return convertView;
    }
}
