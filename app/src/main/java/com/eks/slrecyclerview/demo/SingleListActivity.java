package com.eks.slrecyclerview.demo;

import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.eks.slrecyclerview.SLRecyclerView;

import java.util.ArrayList;


/**
 * Created by Riggs on 3/13/2020
 */
public class SingleListActivity extends AppCompatActivity {
    private SLRecyclerView<OrderBean> rvOrder;
    private OrderAdapter orderAdapter;
    private ArrayList<OrderBean> orderList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_list);
        rvOrder = findViewById(R.id.rvOrder);
        setAdapter();
        setData();
    }


    private void setAdapter() {
        orderAdapter = new OrderAdapter(this);
        rvOrder.setLayoutManager(new LinearLayoutManager(this));
        rvOrder.setAdapter(orderAdapter);
        orderAdapter.setData(orderList);
    }

    private void setData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                orderList.add(new OrderBean(123124, 42342356, R.drawable.ic_launcher_background));
                orderList.add(new OrderBean(4325, 5323, R.drawable.ic_launcher_background));
                orderList.add(new OrderBean(34534, 35235, R.drawable.ic_launcher_background));
                orderList.add(new OrderBean(7567, 443, R.drawable.ic_launcher_background));
                orderList.add(new OrderBean(324, 543534, R.drawable.ic_launcher_background));
                orderList.add(new OrderBean(546346, 543, R.drawable.ic_launcher_background));
                orderList.add(new OrderBean(6645, 1351, R.drawable.ic_launcher_background));
                orderList.add(new OrderBean(5235423, 11, R.drawable.ic_launcher_background));
                orderList.add(new OrderBean(5345, 73, R.drawable.ic_launcher_background));
                orderList.add(new OrderBean(553, 3214, R.drawable.ic_launcher_background));
                orderList.add(new OrderBean(112, 53464, R.drawable.ic_launcher_background));
                orderList.add(new OrderBean(543256, 6854, R.drawable.ic_launcher_background));
                orderAdapter.notifyDataSetChanged();
            }
        },2000);
    }
}
