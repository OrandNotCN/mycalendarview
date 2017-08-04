package com.lacuz.cherish.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lacuz.cherish.R;
import com.lacuz.cherish.greendao.entity.Birth;
import com.prolificinteractive.materialcalendarview.CalendarUtil2;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lacuz on 2017/7/18.
 */

public class BirthListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Birth> list;
    Context mContext;

    public BirthListAdapter(List<Birth> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_birth_list, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final Birth bean = list.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
//        viewHolder.ivPhoto.setImageResource(bean.getPhotoUrl());
        viewHolder.tvTitle.setText(bean.getName());
        viewHolder.tvBirth.setText(CalendarUtil2.getDate(bean.getBirth_time(),bean.getIs_lunar()));
        viewHolder.tvAddress.setText(bean.getAddress());
        viewHolder.tvPhone.setText(bean.getPhone());


    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public void updateData(List<Birth> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivPhoto)
        ImageView ivPhoto;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvAddress)
        TextView tvAddress;
        @BindView(R.id.tvPhone)
        TextView tvPhone;
        @BindView(R.id.tvBirth)
        TextView tvBirth;
        View rootView;

        ViewHolder(View view) {
            super(view);
            this.rootView = view;
            ButterKnife.bind(this, view);
        }
    }
}

