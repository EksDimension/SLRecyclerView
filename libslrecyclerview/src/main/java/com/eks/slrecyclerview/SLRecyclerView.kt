package com.eks.slrecyclerview

import android.content.Context
import android.util.AttributeSet
import android.view.*
import android.widget.RelativeLayout
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Riggs on 2020/3/13
 */
class SLRecyclerView<T> : RelativeLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    /**
     * 真实的recyclerView
     */
    @JvmField
    var recyclerView: RecyclerView? = null
    /**
     * 当数据为空时展示的View
     */
    private var mEmptyView: RelativeLayout? = null
    /**
     * 正在加载时展示的View
     */
    private var mLoadingView: RelativeLayout? = null
    /**
     * 正在加载失败的View
     */
    private var mLoadFailedView: RelativeLayout? = null
    /**
     * 是否首次加载
     */
    private var isfirst = true
    /**
     * 滑动距离起步值
     */
    private var scaledTouchSlop: Int


    init {
        val layoutView = LayoutInflater.from(context).inflate(R.layout.layout_lerecycler, this)
        layoutView?.let {
            recyclerView = it.findViewById(R.id.rv)
            mEmptyView = it.findViewById(R.id.rl_empty)
            mLoadingView = it.findViewById(R.id.rl_loading)
            mLoadFailedView = it.findViewById(R.id.rl_load_failed)
        }
        recyclerView?.addOnScrollListener(ImageAutoLoadScrollListener())
        scaledTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }

    /**
     * @param emptyViewRes 空数据资源文件
     */
    fun setEmptyView(emptyViewRes: Int) {
        mEmptyView?.removeAllViews()
//        mEmptyView?.addView(LayoutInflater.from(context).inflate(emptyView, null))
        LayoutInflater.from(context).inflate(emptyViewRes, mEmptyView)
    }

    /**
     * @param loadingViewRes 加载中资源文件
     */
    fun setLoadingView(loadingViewRes: Int) {
        mLoadingView?.removeAllViews()
//        mLoadingView?.addView(LayoutInflater.from(context).inflate(loadingView, null))
        LayoutInflater.from(context).inflate(loadingViewRes, mLoadingView)
    }

    /**
     * @param loadFailedViewRes 加载失败资源文件
     */
    fun setLoadFailedView(loadFailedViewRes: Int) {
        mLoadFailedView?.removeAllViews()
        LayoutInflater.from(context).inflate(loadFailedViewRes, mLoadFailedView)
    }

    /**
     * @param loadFailedView 加载失败资源文件
     */
    fun setLoadFailedView(loadFailedView: View) {
        mLoadFailedView?.removeAllViews()
        loadFailedView.layoutParams = mLoadFailedView?.layoutParams
        mLoadFailedView?.addView(loadFailedView)
    }

    /**
     * 显示加载中
     */
    fun showLoadingView() {
        mEmptyView?.visibility = View.GONE
        recyclerView?.visibility = View.GONE
        mLoadingView?.visibility = View.VISIBLE
        mLoadFailedView?.visibility = View.GONE
    }

    /**
     * 显示加载失败
     */
    fun showLoadFailed() {
        mEmptyView?.visibility = View.GONE
        recyclerView?.visibility = View.GONE
        mLoadingView?.visibility = View.GONE
        mLoadFailedView?.visibility = View.VISIBLE
    }

    /**
     * 创建一个观察者
     * 因为每次notifyDataChanged的时候，系统都会调用这个观察者的onChange函数
     * 我们大可以在这个观察者这里判断我们的逻辑，就是显示隐藏
     */
    private val emptyObserver = object : RecyclerView.AdapterDataObserver() {
        override fun onChanged() {
            super.onChanged()
            val adapter = recyclerView?.adapter //这种写发跟之前我们之前看到的ListView的是一样的，判断数据为空否，再进行显示或者隐藏
            if (adapter != null && mEmptyView != null) {
                if (adapter.itemCount == 0) {
                    mEmptyView?.visibility = View.VISIBLE
                    recyclerView?.visibility = View.GONE
                    mLoadingView?.visibility = View.GONE
                    mLoadFailedView?.visibility = View.GONE
                } else {
                    mEmptyView?.visibility = View.GONE
                    recyclerView?.visibility = View.VISIBLE
                    mLoadingView?.visibility = View.GONE
                    mLoadFailedView?.visibility = View.GONE
                }
                if (isfirst) {
                    isfirst = false
                    recyclerView?.visibility = View.GONE
                    mEmptyView?.visibility = View.GONE
                    mLoadingView?.visibility = View.VISIBLE
                    mLoadFailedView?.visibility = View.GONE
                }
            }
        }
    }

    var mAdapter: SLAdapter<T>? = null

    fun setAdapter(adapter: SLAdapter<T>?) {
        mAdapter = adapter
        recyclerView?.adapter = mAdapter
        mAdapter?.let {
            //这里用了观察者模式，同时把这个观察者添加进去，
            // 至于这个模式怎么用，谷歌一下，不多讲了，因为这个涉及到了Adapter的一些原理，感兴趣可以点进去看看源码，还是受益匪浅的
            adapter?.registerAdapterDataObserver(emptyObserver)
            //当setAdapter的时候也调一次（实际上，经我粗略验证，不添加貌似也可以。不行就给添上呗，多大事嘛）
            emptyObserver.onChanged()
        }
    }

    /**
     * 直接显示数据setAdapter(即无异步等待notifyDataChanged)
     *
     * @param adapter 适配器
     */
    fun setAdapterAndNotify(adapter: SLAdapter<T>?) {
        mAdapter = adapter
        isfirst = false
        recyclerView?.adapter = adapter
        if (adapter != null) {
            adapter.registerAdapterDataObserver(emptyObserver)
            emptyObserver.onChanged()
        }
    }

    /**
     * 隐藏加载进度及无数据的setAdapter(不执行notifyDataChanged前是不会显示无数据)
     *
     * @param adapter 适配器
     */
    fun setAdapterWithoutLoading(adapter: SLAdapter<T>?) {
        mAdapter = adapter
        mEmptyView?.visibility = View.GONE
        mLoadingView?.visibility = View.GONE
        mLoadFailedView?.visibility = View.GONE
        recyclerView?.adapter = adapter
        adapter?.registerAdapterDataObserver(emptyObserver)
        isfirst = false
    }

    fun setLayoutManager(linearLayoutManager: LinearLayoutManager) {
        recyclerView?.layoutManager = linearLayoutManager
    }

    fun setOnScrollListener(onScrollListener: RecyclerView.OnScrollListener) {
        recyclerView?.addOnScrollListener(onScrollListener)
    }

    fun getChildViewHolder(view: View): SLHolder? {
        return recyclerView?.getChildViewHolder(view) as SLHolder?
    }

    //监听滚动来对图片加载进行判断处理
    inner class ImageAutoLoadScrollListener : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//            srolled = true
            super.onScrolled(recyclerView, dx, dy)
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            when (newState) {
                RecyclerView.SCROLL_STATE_IDLE // The RecyclerView is not currently scrolling.
                ->
                    //当屏幕停止滚动，加载图片
                    try {
//                        context?.let { Glide.with(it).resumeRequests() }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                RecyclerView.SCROLL_STATE_DRAGGING // The RecyclerView is currently being dragged by outside input such as user touch input.
                ->
                    //当屏幕滚动且用户使用的触碰或手指还在屏幕上，停止加载图片
                    try {
//                        context?.let { Glide.with(it).pauseRequests() }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                RecyclerView.SCROLL_STATE_SETTLING // The RecyclerView is currently animating to a final position while not under outside control.
                ->
                    //由于用户的操作，屏幕产生惯性滑动，停止加载图片
                    try {
//                        context?.let { Glide.with(it).pauseRequests() }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
            }
        }
    }

//    interface OnItemClickListener<T> {
//        fun onItemClick(view: View, data: T?, position: Int)
//    }

    //    fun setOnItemClickListener(onItemClick: OnItemClickListener<T>) {
//        mAdapter?.setOnItemClickListener(onItemClick)
//    }
//    var srolled: Boolean = false

    open inner class RecyclerViewItemTouchListener() : RecyclerView.OnItemTouchListener {
        private var mClickListener: OnItemViewClickListener? = null
        private var mGestureDetector: GestureDetectorCompat? = null

        constructor(clickListener: OnItemViewClickListener?) : this() {
            mClickListener = clickListener
            mGestureDetector = GestureDetectorCompat(recyclerView?.context, object : GestureDetector.SimpleOnGestureListener() {
                override fun onSingleTapUp(e: MotionEvent?): Boolean {
//                    val childView = recyclerView?.findChildViewUnder(e?.x ?: 0f, e?.y ?: 0f)
//                    val childAdapterPosition = if (childView != null) {
//                        recyclerView?.getChildAdapterPosition(childView) ?: -1
//                    } else {
//                        -1
//                    }
//                    if (recyclerView != null && childView != null && childAdapterPosition != -1) {
//                        val data = mAdapter?.getData()?.get(childAdapterPosition)
//                        mClickListener?.onItemViewClick(this@SLRecyclerView, childView, data, childAdapterPosition)
//                    }
                    return true
                }
            })
        }

        var lastY: Float = 0.0f
        var lastX: Float = 0.0f

        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
            when (e.action) {
                MotionEvent.ACTION_DOWN -> {//手指按下,记录下点击X Y坐标
                    lastY = e.rawY
                    lastX = e.rawX
//                    println("<><><>${e.rawX} ${e.rawY}")
//                    srolled = false
                }
                MotionEvent.ACTION_UP -> {
//                    println("<><><>$lastX $lastY       ${e.rawX} ${e.rawY}")
                    //抬起手指,看看松开时X Y坐标和记录时的X Y坐标
                    //考虑到手指可能轻微移动,所以准许有少许像素的容差,超出容差就不触发点击. 容差值就是滚动触发距离
                    if (Math.abs(e.rawY - lastY) > scaledTouchSlop || Math.abs(e.rawX - lastX) > scaledTouchSlop) {
//                        ToastUtils.getInstance().showLong("超出啦Y" + Math.abs(e.y - lastY) + "  X" + Math.abs(e.x - lastX))
                        return false
                    }
                    lastY = 0.0f
                    lastX = 0.0f
//                    if (srolled) {
//                        return false
//                    }
//                    srolled = false
                    // 通过x y获取item
                    val itemView = recyclerView?.findChildViewUnder(e.x, e.y)
                    if (itemView != null) {
                        //获取item中所有的控件
                        views.clear()
                        collectChildViews(itemView)
                        //遍历控件集合,找出x y所点击到的控件
//                        val clickedView = findClickedView(e, itemView) ?: itemView
                        val clickedViews = findClickedViews(e, itemView)
                        val clickedView: View
                        clickedView = if (clickedViews.isEmpty()) {
                            itemView
                        } else {
                            clickedViews[0]
                        }
                        val childAdapterPosition = recyclerView?.getChildAdapterPosition(itemView)
                                ?: -1
                        if (recyclerView != null && childAdapterPosition != -1) {
                            val data = mAdapter?.getData()?.get(childAdapterPosition)
                            val clickBean = ClickBean(this@SLRecyclerView, clickedView, clickedViews, data, childAdapterPosition)
                            mClickListener?.onItemViewClick(clickBean)
                            return false
                        }
                    }
                }
                MotionEvent.ACTION_CANCEL -> {//取消时 把上次的X Y值恢复为0
                    lastY = 0.0f
                    lastX = 0.0f
//                    srolled = false
                }
            }

            return false
        }

        private fun findClickedViews(e: MotionEvent, itemView: View): ArrayList<View> {
            val clickViews = ArrayList<View>()
//            //该item顶边所处Y坐标
//            val itemBottom = itemView.bottom
//            //该item底边所处Y坐标
//            val itemTop = itemView.top
//            val clickY = when {
//                itemBottom > itemView.height -> {//如果底部坐标大于高度,就表示往下走了, 要减去多余空间
//                    val deltaY = itemBottom - itemView.height
//                    e.y - deltaY
//                }
//                itemTop < 0 -> {//如果顶部坐标为负数, 就表示往上走了, 补回缺少空间
//                    val deltaY = -itemTop
//                    e.y + deltaY
//                }
//                else -> //否则就是正好处于最顶部
//                    e.y
//            }

            views.forEach { view ->
                totalOffsetX = 0.0f
                totalOffsetY = 0.0f
                getTotalOffset(view, itemView)
                val translationX = view.translationX
                val translationY = view.translationY
                if (e.x >= view.left + totalOffsetX + translationX + (recyclerView?.scrollX
                                ?: 0)
                        && e.x <= view.right + totalOffsetX + translationX + (recyclerView?.scrollX
                                ?: 0)
                        && e.y >= view.top + totalOffsetY + translationY + (recyclerView?.scrollY
                                ?: 0)
                        && e.y <= view.bottom + totalOffsetY + translationY + (recyclerView?.scrollY
                                ?: 0)) {
                    if (!clickViews.contains(view)) clickViews.add(view)
//                    return view
                }
            }
            if (!clickViews.contains(itemView)) clickViews.add(itemView)
            return clickViews
        }

        private var totalOffsetX = 0.0f
        private var totalOffsetY = 0.0f

        /**
         * 获取所有父级控件的所有总体偏移量
         */
        private fun getTotalOffset(view: View, itemView: View) {
            if (view != itemView) {
                val parentView = view.parent as? View
//                if (parentView != null && parentView != itemView) {
                if (parentView != null) {
                    totalOffsetX += parentView.left
                    totalOffsetY += parentView.top
                    getTotalOffset(parentView, itemView)
                }
            }
        }

        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        }

        override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
        }
    }

    var views: ArrayList<View> = arrayListOf()

    /**
     * 收集item中所有控件(包括所有子控件)
     *
     * @param view 控件
     */
    private fun collectChildViews(view: View) {
        if (view is ViewGroup) {//如果是viewGroup,表示可能还有子控件
            views.add(0, view)
            for (i in 0 until view.childCount) {
                collectChildViews(view.getChildAt(i))
            }
        } else {//如果只是个控件 那就添加到集合去
            views.add(0, view)
        }
    }

    interface OnItemViewClickListener {
        //        fun onItemViewClick(SLRecyclerView: SLRecyclerView<T>, viewOnClick: View, viewsOnClick: ArrayList<View>, data: T?, position: Int)
        fun onItemViewClick(clickBean: ClickBean<*>)
    }

    fun addOnItemViewClickListener(onItemViewClickListener: OnItemViewClickListener?) {
        recyclerView?.addOnItemTouchListener(RecyclerViewItemTouchListener(onItemViewClickListener))
    }
}