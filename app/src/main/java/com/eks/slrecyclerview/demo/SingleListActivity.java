package com.eks.slrecyclerview.demo;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.eks.slrecyclerview.SLRecyclerView;

import org.jetbrains.annotations.NotNull;

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
        setListener();
        setData();
    }


    private void setAdapter() {
        orderAdapter = new OrderAdapter(this);
        rvOrder.setLayoutManager(new LinearLayoutManager(this));
        rvOrder.setAdapter(orderAdapter);
        orderAdapter.setData(orderList);
    }

    private void setListener() {
        rvOrder.addOnItemTouchListener(new SLRecyclerView.OnItemViewClickListener() {
            @Override
            public void onItemViewClick(@NotNull SLRecyclerView<?> SLRecyclerView, @NotNull View view, @org.jetbrains.annotations.Nullable Object data, int position) {
                Toast.makeText(SingleListActivity.this, "被点击的控件:" + view.getTag() + "\n\nitem数据:" + data.toString() + "\n\n点击item位置:" + position, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                orderList.add(new OrderBean(111, "已完成", R.drawable.ic_launcher_background));
                orderList.add(new OrderBean(222, "已售出", R.drawable.ic_launcher_background));
                orderList.add(new OrderBean(333, "已入货", R.drawable.ic_launcher_background));
                orderList.add(new OrderBean(444, "已出货", R.drawable.ic_launcher_background));
                orderList.add(new OrderBean(555, "已交接", R.drawable.ic_launcher_background));
                orderList.add(new OrderBean(666, "已发出", R.drawable.ic_launcher_background));
                orderList.add(new OrderBean(777, "待接收", R.drawable.ic_launcher_background));
                orderList.add(new OrderBean(888, "待发出", R.drawable.ic_launcher_background));
                orderList.add(new OrderBean(999, "已失败", R.drawable.ic_launcher_background));
                orderList.add(new OrderBean(101010, "已退货", R.drawable.ic_launcher_background));
                orderList.add(new OrderBean(111111, "已确认", R.drawable.ic_launcher_background));
                orderList.add(new OrderBean(121212, "已取消", R.drawable.ic_launcher_background));
                orderAdapter.notifyDataSetChanged();
            }
        }, 2000);
    }
}
