package com.eks.slrecyclerview


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Riggs on 2020/3/13
 */
abstract class SLAdapter<T>(context: Context?) : RecyclerView.Adapter<SLHolder>() {
    @JvmField
    var mContext = context
    @JvmField
    protected var mDataList: List<T>? = null
    @JvmField
    protected var mHolder: SLHolder? = null

    fun setData(dataList: List<T>?) {
        mDataList = dataList
    }

    fun getData() = mDataList

    abstract fun setItemLayoutResId(): Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SLHolder {
        val holder = SLHolder(LayoutInflater.from(parent.context).inflate(setItemLayoutResId(), parent, false))
//        setHolderListener(holder)
        return holder
    }

//    private var mOnItemClickListener: LERecyclerView.OnItemClickListener<T>? = null

//    fun setOnItemClickListener(onItemClickListener: LERecyclerView.OnItemClickListener<T>) {
//        mOnItemClickListener = onItemClickListener
//    }

//    /**
//     * 给item设置监听器
//     * @param holder Holder对象
//     */
//    private fun setHolderListener(holder: SLHolder) {
//        holder.mItemView.setOnClickListener {
//            val position = holder.adapterPosition
//            if (position != -1) mOnItemClickListener?.onItemClick(holder.mItemView, mDataList?.get(position), position)
//        }
//        holder.mItemView.setOnLongClickListener(object : View.OnLongClickListener {
//            override fun onLongClick(v: View?): Boolean {
//                return false
//            }
//        })
//    }

    override fun getItemCount() = mDataList?.size ?: 0

    override fun onBindViewHolder(holder: SLHolder, position: Int) {
        mHolder = holder
        val data = if (mDataList != null) {
            if (mDataList!!.isNotEmpty()) {
                mDataList!![position]
            } else {
                null
            }
        } else {
            null
        }
        onBindViewHolder(holder, data, position)
    }

    abstract fun onBindViewHolder(holder: SLHolder, data: T?, position: Int)

    protected fun <V : View> getView(viewResId: Int) = mHolder?.getView<V>(viewResId)

    fun <V : View> findViewById(viewResId: Int) = mHolder?.getView<V>(viewResId)
}