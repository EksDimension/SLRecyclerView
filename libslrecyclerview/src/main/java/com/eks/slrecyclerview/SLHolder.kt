package com.eks.slrecyclerview

import android.util.SparseArray
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Riggs on 2020/3/13
 */
class SLHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var mItemView: View = itemView

    var viewsArray: SparseArray<View> = SparseArray()

    fun <T : View> getView(viewId: Int): T? {
        var view = viewsArray[viewId]
        if (view == null) {
            view = mItemView.findViewById<T>(viewId)
            viewsArray.put(viewId, view)
        }
        @Suppress("UNCHECKED_CAST")
        return view as? T
    }
}