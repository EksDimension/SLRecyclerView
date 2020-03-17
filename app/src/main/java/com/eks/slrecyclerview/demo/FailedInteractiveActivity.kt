package com.eks.slrecyclerview.demo

import android.os.Handler
import android.view.View
import android.widget.Button

/**
 * 让列表展示自定义的"加载失败"界面,并拥有交互性
 * Created by Riggs on 3/17/2020
 */
class FailedInteractiveActivity : SingleListActivity() {

    override fun setAdapter() {
        super.setAdapter()
        val failedView = View.inflate(this, R.layout.failed_bilibili_interactive, null)
        failedView.findViewById<Button>(R.id.btnRetry).setOnClickListener { setData() }
        rvOrder?.setLoadFailedView(failedView)
    }

    override fun setData() {
        rvOrder?.showLoadingView()
        Handler().postDelayed({
            rvOrder?.showLoadFailed()
        }, 1500)
    }
}