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

    /**
     * 在这里指定布局样式
     */
    @Override
    public int setItemLayoutResId() {
        return R.layout.item_order;
    }

    /**
     * 在这里进行控件数据设值
     */
    @Override
    public void onBindViewHolder(@NotNull SLHolder holder, @Nullable OrderBean data, int position) {
        /**
         * 这里提供一个叫 getView(Int resId) 的方法,作用类似于findViewById
         * 而该方法拥有复用功能,多次执行也不用担心浪费资源损耗性能.推荐使用.
         */
        ((ImageView) getView(R.id.ivProductImg)).setImageResource(data.getProductImg());
        ((TextView) getView(R.id.tvOrderSn)).setText("单号:" + data.getOrderSn() + "");
        ((TextView) getView(R.id.tvOrderType)).setText(data.getOrderType());
    }
}
