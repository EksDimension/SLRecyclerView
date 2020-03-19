package com.eks.slrecyclerview

import android.view.View

/**
 * 点击响应体封装类
 * Created by Riggs on 3/16/2020
 */
data class ClickBean<T>(
        /**
         * 所点击的SLRecyclerView对象
         */
        @JvmField var sLRecyclerView: SLRecyclerView<T>
        /**
         * 当前item上被直接点击的View
         */
        , @JvmField var viewOnClick: View
        /**
         * 当前item中,摆放在点击位置的上所有的View集合
         */
        , @JvmField var viewsOnClick: ArrayList<View>
        /**
         * 当前item的数据
         */
        , @JvmField var data: Any?
        /**
         * 当前item的position位置
         */
        , @JvmField var position: Int)