package com.eks.slrecyclerview.demo

import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.eks.slrecyclerview.ClickBean
import com.eks.slrecyclerview.SLRecyclerView
import com.eks.slrecyclerview.SLRecyclerView.OnItemViewClickListener
import java.util.*

/**
 * 双列表展示,主要体现用同一个接口实现来接收不同列表的监听事件
 * Created by Riggs on 3/16/2020
 */
open class DoubleListActivity : AppCompatActivity() {
    protected var rvOrder: SLRecyclerView<OrderBean>? = null
    protected var orderAdapter: OrderAdapter? = null
    private val orderList = ArrayList<OrderBean>()

    protected var rvAddress: SLRecyclerView<AddressBean>? = null
    protected var addressAdapter: AddressAdapter? = null
    private val addressList = ArrayList<AddressBean>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_double_list)
        initView()
        setAdapter()
        setListener()
        setOrderData()
        setAddressData()
    }

    open fun initView() {
        rvOrder = findViewById(R.id.rvOrder)
        rvAddress = findViewById(R.id.rvAddress)
    }


    private fun setAdapter() {
        orderAdapter = OrderAdapter(this)
        orderAdapter?.setData(orderList)
        rvOrder?.setLayoutManager(LinearLayoutManager(this))
        rvOrder?.setAdapter(orderAdapter)

        addressAdapter = AddressAdapter(this)
        addressAdapter?.setData(addressList)
        rvAddress?.setLayoutManager(LinearLayoutManager(this))
        rvAddress?.setAdapter(addressAdapter)
    }

    private fun setListener() {
        rvOrder?.addOnItemViewClickListener(mOnItemViewClickListener)
        rvAddress?.addOnItemViewClickListener(mOnItemViewClickListener)
    }

    /**
     * 同一个接口实现,给2个不同数据类型的列表进行同监听
     * clickBean.data的类型为Any(Java中的Object).
     */
    private var mOnItemViewClickListener = object : OnItemViewClickListener {
        override fun onItemViewClick(clickBean: ClickBean<*>) {
            val sb = StringBuilder()
            for (oneView in clickBean.viewsOnClick) {
                sb.append(oneView.tag).append("\n")
            }
            Toast.makeText(this@DoubleListActivity, "当前列表:${clickBean.sLRecyclerView.tag}\n\n直接点击控件:${clickBean.viewOnClick.tag}\n\n点击范围所有控件:\n${sb}\n\nitem数据类型:${clickBean.data?.javaClass?.simpleName}\n\nitem数据:${clickBean.data}\n\n点击item位置:${clickBean.position}", Toast.LENGTH_LONG).show()
        }
    }

    open fun setOrderData() {
        orderList.clear()
        Handler().postDelayed({
            orderList.add(OrderBean(111, "已完成", R.drawable.ic_launcher_background))
            orderList.add(OrderBean(222, "已售出", R.drawable.ic_launcher_background))
            orderList.add(OrderBean(333, "已入货", R.drawable.ic_launcher_background))
            orderList.add(OrderBean(444, "已出货", R.drawable.ic_launcher_background))
            orderList.add(OrderBean(555, "已交接", R.drawable.ic_launcher_background))
            orderList.add(OrderBean(666, "已发出", R.drawable.ic_launcher_background))
            orderList.add(OrderBean(777, "待接收", R.drawable.ic_launcher_background))
            orderList.add(OrderBean(888, "待发出", R.drawable.ic_launcher_background))
            orderList.add(OrderBean(999, "已失败", R.drawable.ic_launcher_background))
            orderList.add(OrderBean(101010, "已退货", R.drawable.ic_launcher_background))
            orderList.add(OrderBean(111111, "已确认", R.drawable.ic_launcher_background))
            orderList.add(OrderBean(121212, "已取消", R.drawable.ic_launcher_background))
            orderAdapter?.notifyDataSetChanged()
        }, 1500)
    }

    open fun setAddressData() {
        addressList.clear()
        Handler().postDelayed({
            addressList.add(AddressBean("小明", "广州"))
            addressList.add(AddressBean("小黄", "东莞"))
            addressList.add(AddressBean("小王", "深圳"))
            addressList.add(AddressBean("小何", "茂名"))
            addressList.add(AddressBean("小朱", "珠海"))
            addressList.add(AddressBean("小余", "佛山"))
            addressList.add(AddressBean("小张", "汕头"))
            addressList.add(AddressBean("小陈", "韶关"))
            addressList.add(AddressBean("小胡", "河源"))
            addressAdapter?.notifyDataSetChanged()
        }, 2500)
    }
}