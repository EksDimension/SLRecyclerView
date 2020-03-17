package com.eks.slrecyclerview.demo

import android.content.Context
import android.widget.TextView
import com.eks.slrecyclerview.SLAdapter
import com.eks.slrecyclerview.SLHolder

/**
 * Created by Riggs on 3/17/2020
 */
class AddressAdapter(context: Context) : SLAdapter<AddressBean>(context) {
    override fun setItemLayoutResId(): Int =R.layout.item_address

    override fun onBindViewHolder(holder: SLHolder, data: AddressBean?, position: Int) {
        getView<TextView>(R.id.tvName)?.text = data?.name
        getView<TextView>(R.id.tvAddress)?.text = data?.address
    }
}