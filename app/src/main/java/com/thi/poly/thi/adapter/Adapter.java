package com.thi.poly.thi.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.thi.poly.thi.listener.OnClick;
import com.thi.poly.thi.listener.OnDelete;
import com.thi.poly.thi.model.Phone;
import com.thi.poly.thi.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;
import java.util.Locale;


public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private List<Phone> bookList;
    private final OnDelete onDelete;
    private final OnClick onClick;

    public Adapter(List<Phone> bookList, OnDelete onDelete, OnClick onClick) {
        this.bookList = bookList;
        this.onDelete = onDelete;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @SuppressLint({"RecyclerView", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Phone phone = bookList.get(position);
        holder.tvName.setText( phone.getName());

        holder.tvID.setText((position+1)+"."+ phone.getID());

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDelete.onDelete(position);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (bookList == null) return 0;
        return bookList.size();
    }

    public void changeDataset(List<Phone> items) {
        this.bookList = items;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imgDelete;
        final TextView tvName;

        final TextView tvID;

        ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);

            tvID = itemView.findViewById(R.id.tvID);
            imgDelete = itemView.findViewById(R.id.imgDelete);
        }
    }

}
