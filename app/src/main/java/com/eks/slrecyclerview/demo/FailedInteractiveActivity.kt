package com.eks.slrecyclerview.demo

import android.os.Handler
import android.view.View
import android.widget.Button

/**
 * 让列表展示自定义的"加载失败"界面,并拥有交互性
 *
 * Created by Riggs on 3/17/2020
 */
class FailedInteractiveActivity : SingleListActivity() {

    override fun setAdapter() {
        super.setAdapter()
        //同样是执行setLoadFailedView()方法,但以View作为传入参数.
        //在View上自定义交互控件以及编写交互事件,即可在加载失败后进行交互操作.
        val failedView = View.inflate(this, R.layout.failed_bilibili_interactive, null)
        failedView.findViewById<Button>(R.id.btnRetry).setOnClickListener { retry() }
        rvOrder?.setLoadFailedView(failedView)
    }


    override fun setData() {
        Handler().postDelayed({
            rvOrder?.showLoadFailed()
        }, 1500)
    }

    /**
     * 此处模拟加载失败后,点击按钮进行重试的情形
     * 而重试过程中需要显示"正在加载",因此需要手动调用showLoadingView()
     */
    private fun retry() {
        rvOrder?.showLoadingView()
        Handler().postDelayed({
            orderList.add(OrderBean(111, "已完成", R.drawable.ic_launcher_background))
            orderList.add(OrderBean(222, "已售出", R.drawable.ic_launcher_background))
            orderList.add(OrderBean(333, "已入货", R.drawable.ic_launcher_background))
            orderList.add(OrderBean(444, "已出货", R.drawable.ic_launcher_background))
            orderList.add(OrderBean(555, "已交接", R.drawable.ic_launcher_background))
            orderList.add(OrderBean(666, "已发出", R.drawable.ic_launcher_background))
            orderList.add(OrderBean(777, "待接收", R.drawable.ic_launcher_background))
            orderList.add(OrderBean(888, "待发出", R.drawable.ic_launcher_background))
            orderList.add(OrderBean(999, "已失败", R.drawable.ic_launcher_background))
            orderList.add(OrderBean(101010, "已退货", R.drawable.ic_launcher_background))
            orderList.add(OrderBean(111111, "已确认", R.drawable.ic_launcher_background))
            orderList.add(OrderBean(121212, "已取消", R.drawable.ic_launcher_background))
            orderAdapter.notifyDataSetChanged()
        }, 1500)
    }
}