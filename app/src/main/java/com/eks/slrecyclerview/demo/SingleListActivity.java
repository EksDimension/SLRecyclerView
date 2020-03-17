package com.eks.slrecyclerview.demo;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.eks.slrecyclerview.ClickBean;
import com.eks.slrecyclerview.SLRecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


/**
 * Created by Riggs on 3/13/2020
 */
public class SingleListActivity extends AppCompatActivity {
    protected SLRecyclerView<OrderBean> rvOrder;
    protected OrderAdapter orderAdapter;
    protected ArrayList<OrderBean> orderList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_list);
        rvOrder = findViewById(R.id.rvOrder);
        setAdapter();
        setListener();
        setData();
    }


    protected void setAdapter() {
        orderAdapter = new OrderAdapter(this);
        rvOrder.setLayoutManager(new LinearLayoutManager(this));
        rvOrder.setAdapter(orderAdapter);
        orderAdapter.setData(orderList);
    }

    private void setListener() {
        rvOrder.addOnItemTouchListener(new SLRecyclerView.OnItemViewClickListener<OrderBean>() {
            @Override
            public void onItemViewClick(@NotNull ClickBean<OrderBean> clickBean) {
                StringBuilder sb = new StringBuilder();
                for (View oneView : clickBean.viewsOnClick) {
                    sb.append(oneView.getTag()).append("\n");
                }
                Toast.makeText(SingleListActivity.this, "直接点击控件:" + clickBean.viewOnClick.getTag() + "\n\n点击范围所有控件:\n" + sb.toString() + "\n\nitem数据:" + clickBean.data + "\n\n点击item位置:" + clickBean.position, Toast.LENGTH_LONG).show();
            }
        });
    }

    protected void setData() {
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
