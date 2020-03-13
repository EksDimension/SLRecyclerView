package com.eks.slrecyclerview.demo;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.eks.slrecyclerview.SLAdapter;
import com.eks.slrecyclerview.SLHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Riggs on 3/13/2020
 */
public class OrderAdapter extends SLAdapter<OrderBean> {

    public OrderAdapter(@Nullable Context context) {
        super(context);
    }

    @Override
    public int setItemLayoutResId() {
        return R.layout.item_order;
    }

    @Override
    public void onBindViewHolder(@NotNull SLHolder holder, @Nullable OrderBean data, int position) {
        ImageView ivProductImg = holder.itemView.findViewById(R.id.ivProductImg);
        TextView tvOrderSn = holder.itemView.findViewById(R.id.tvOrderSn);
        TextView tvOrderType = holder.itemView.findViewById(R.id.tvOrderType);

        ivProductImg.setImageResource(data.getProductImg());
        tvOrderSn.setText(data.getOrderSn()+"");
        tvOrderType.setText(data.getOrderType()+"");
    }
}