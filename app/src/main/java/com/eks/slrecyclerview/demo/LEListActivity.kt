package com.eks.slrecyclerview.demo

import android.os.Handler

/**
 * 让列表展示"正在加载/没有数据"
 * 加载图--左上自定义 右下默认
 * 没有数据--左上默认 右下自定义
 *
 * 注:由于本SLRecyclerView控件,在通过setAdapter来设置适配器的情况下,默认就会显示"正在加载"页面.无需手动调用.
 * 若在需求中需要再次显示"正在加载",可调用showLoadingView()方法.
 * 具体用例可参考{@link FailedInteractiveActivity.setData()}
 * Created by Riggs on 3/17/2020
 */
class LEListActivity : DoubleListActivity() {
    override fun initView() {
        super.initView()
        rvOrder?.setLoadingView(R.layout.loading_bilibili)//设置自定义的资源布局作为加载页
        rvAddress?.setEmptyView(R.layout.nodata_c)//设置自定义的资源布局作为空数据页
    }

    override fun setOrderData() {
        Handler().postDelayed({
            //执行该方法后,会因data数据为空,而自动从加载中切换成无数据页面
            //若data数据不为空,则会展示实际列表
            orderAdapter?.notifyDataSetChanged()
        }, 1500)
    }

    override fun setAddressData() {
        Handler().postDelayed({
            //执行该方法后,会因data数据为空,而自动从加载中切换成无数据页面
            //若data数据不为空,则会展示实际列表
            addressAdapter?.notifyDataSetChanged()
        }, 2500)
    }

}