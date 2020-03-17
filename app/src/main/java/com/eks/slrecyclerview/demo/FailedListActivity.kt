package com.eks.slrecyclerview.demo

import android.os.Handler

/**
 * 让列表展示"加载失败"
 * 左上自定义 右下默认
 * Created by Riggs on 3/17/2020
 */
class FailedListActivity : DoubleListActivity() {
    override fun setRecyclerView() {
        super.setRecyclerView()
        rvOrder?.setLoadFailedView(R.layout.failed_bilibili)
    }

    override fun setOrderData() {
        Handler().postDelayed({
            rvOrder?.showLoadFailed()
        }, 1500)
    }

    override fun setAddressData() {
        Handler().postDelayed({
            rvAddress?.showLoadFailed()
        }, 2500)
    }

}