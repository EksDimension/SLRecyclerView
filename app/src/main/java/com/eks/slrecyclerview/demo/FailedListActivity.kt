package com.eks.slrecyclerview.demo

import android.os.Handler

/**
 * 让列表展示"加载失败"
 * 左上自定义 右下默认
 *
 * 注:"加载失败"页面需要手动执行showLoadFailed()方法来显示
 * 若后续调用notifyDataSetChanged()方法后,"加载失败"页面会自动隐藏,无需手动隐藏.详情可参考{@link FailedInteractiveActivity.retry()}
 *
 * Created by Riggs on 3/17/2020
 */
class FailedListActivity : DoubleListActivity() {
    override fun initView() {
        super.initView()
        rvOrder?.setLoadFailedView(R.layout.failed_bilibili)//设置自定义的资源布局作为加载失败页
    }

    override fun setOrderData() {
        Handler().postDelayed({
            //执行showLoadFailed()会自动切换成加载失败页面
            rvOrder?.showLoadFailed()
        }, 1500)
    }

    override fun setAddressData() {
        Handler().postDelayed({
            //执行showLoadFailed()会自动切换成加载失败页面
            rvAddress?.showLoadFailed()
        }, 2500)
    }

}