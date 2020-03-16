package com.eks.slrecyclerview

import android.view.View

/**
 * 点击响应体
 * Created by Riggs on 3/16/2020
 */
data class ClickBean<T>(
        /**
         * 所点击的SLRecyclerView对象
         */
        @JvmField var SLRecyclerView: SLRecyclerView<T>
        /**
         * 当前item上被直接点击的View
         */
        , @JvmField var viewOnClick: View
        /**
         * 当前item上,点击位置中所有被被涵盖的View集合
         */
        , @JvmField var viewsOnClick: ArrayList<View>
        /**
         * 当前item的数据
         */
        , @JvmField var data: T?
        /**
         * 当前item的position位置
         */
        , @JvmField var position: Int)