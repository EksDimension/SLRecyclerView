package com.eks.slrecyclerview.demo

import android.os.Handler

/**
 * 让列表展示"正在加载/没有数据"
 * Created by Riggs on 3/17/2020
 */
class LEListActivity : DoubleListActivity() {
    override fun setRecyclerView() {
        super.setRecyclerView()
        rvOrder?.setLoadingView(R.layout.loading_bilibili)
        rvAddress?.setLoadingView(R.layout.loading_c)
        rvAddress?.setEmptyView(R.layout.nodata_c)
    }

    override fun setAddressData() {
        Handler().postDelayed({
            addressAdapter?.notifyDataSetChanged()
        }, 2500)
    }

}