package com.eks.slrecyclerview.demo

import android.os.Handler

/**
 * 让列表展示"正在加载/没有数据"
 * 加载图--左上自定义 右下默认
 * 没有数据--左上默认 右下自定义
 * Created by Riggs on 3/17/2020
 */
class LEListActivity : DoubleListActivity() {
    override fun setRecyclerView() {
        super.setRecyclerView()
        rvOrder?.setLoadingView(R.layout.loading_bilibili)
        rvAddress?.setEmptyView(R.layout.nodata_c)
    }

    override fun setOrderData() {
        Handler().postDelayed({
            orderAdapter?.notifyDataSetChanged()
        }, 1500)
    }

    override fun setAddressData() {
        Handler().postDelayed({
            addressAdapter?.notifyDataSetChanged()
        }, 2500)
    }

}