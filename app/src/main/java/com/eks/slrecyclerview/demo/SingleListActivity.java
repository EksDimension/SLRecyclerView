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
import java.util.Objects;


/**
 * 单一列表案例
 * Created by Riggs on 3/13/2020
 */
public class SingleListActivity extends AppCompatActivity {
    /**
     * SLRecyclerView对象,泛型要指明为列表数据item的bean类型
     */
    protected SLRecyclerView<OrderBean> rvOrder;
    /**
     * 继承于SLAdapter的列表适配器
     */
    protected OrderAdapter orderAdapter;
    /**
     * 列表数据,要以ArrayList为类型,注意泛型要与SLRecyclerView指定的一致,即列表数据item的bean类型
     */
    protected ArrayList<OrderBean> orderList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_list);
        initView();
        setAdapter();
        setListener();
        setData();
    }

    private void initView() {
        //findview没啥好说
        rvOrder = findViewById(R.id.rvOrder);
    }

    protected void setAdapter() {
        //RecyclerView和adapter的构造及 LayoutManager设置之类的, 跟原始RecyclerView和Adapter基本一致.
        //没什么特别
        orderAdapter = new OrderAdapter(this);
        rvOrder.setLayoutManager(new LinearLayoutManager(this));
        rvOrder.setAdapter(orderAdapter);

        //关键代码adapter.setData(dataList)
        //执行该方法进行数据绑定, 建议在执行setAdapter()后 且请求数据之前执行
        orderAdapter.setData(orderList);
    }

    private void setListener() {
        //itemView点击监听的设置, 传入OnItemViewClickListener接口
        rvOrder.addOnItemViewClickListener(new SLRecyclerView.OnItemViewClickListener() {
            /**
             * 点击回调监听
             * @param clickBean 点击回调封装信息,里面封装了所有与点击相关的信息.详见{@link ClickBean}
             *
             *                  注:在这里取出data数据时,类型为Object,是为了让一个回调兼容所有数据类型
             *                  在多列表展示的例子{@link DoubleListActivity.mOnItemViewClickListener}中有体现
             *                  开发者可以通过instanceof进行自行判断
             */
            @Override
            public void onItemViewClick(@NotNull ClickBean<?> clickBean) {
                StringBuilder sb = new StringBuilder();
                for (View oneView : clickBean.viewsOnClick) {
                    sb.append(oneView.getTag()).append("\n");
                }
                Toast.makeText(SingleListActivity.this, "直接点击控件:" + clickBean.viewOnClick.getTag() + "\n\n点击范围所有控件:\n" + sb.toString()+ "\n\nitem数据类型:" + Objects.requireNonNull(clickBean.data).getClass().getSimpleName() + "\n\nitem数据:" + clickBean.data + "\n\n点击item位置:" + clickBean.position, Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * 这里只是模拟异步加载数据.
     * 只要在怼数据源进行修改后, 执行adapter.notifyDataSetChanged()即可.
     */
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
