# SLRecyclerView

### 你是否有过这种烦恼？
在MVC、MVP模式碰上列表页面开发时，特别是Item复杂，按键事件交互繁多的需求时，需要在Adapter对每个控件进行setOnClickListener()进行监听设置。

又为了把事件传递给Activity/Fragment，专门设置接口进行交互而产生了接口地狱，又或者动用到广播导致注册/注销难以管理、甚至乎使用eventbus第三方框架。

最终导致列表代码繁杂，维护成本提高。

#### 如果有，那希望能帮到你。 I've got a present for ya.
###### 这里有一款也许能帮到你的控件 —— SLRecyclerView

------------

### 简介：

#### SLRecyclerView是什么、自监听是啥、使用复杂么？

SLRecyclerView是一款针对在传统MVC、MVP开发模式下（熟悉MVVM流程的小伙伴们应该都知道我为啥只点MVC、MVP的名），解决因为Item交互繁杂而带来的代码激增问题的控件。

开发者只需要按文档提示，设置指定接口，便能全自动监听指定列表中item上所有view的点击事件，从此摆脱Adapter与Activity/Fragment的交互耦合，也不用再为控件的点击监听而操心。

除了增加少量规则代码外，其余使用流程均向原生RecyclerView靠拢，简单易用、确保开发者在短时间内能快速适应并使用。

而作为改造封装控件，对一些原生功能也进行了简化处理，让开发者用起来更为便利。

#### 除了自监听，这SLRecyclerView还有什么特点？

除了自监听功能，这款控件还支持“正在加载”、“无数据”及“加载失败”三种界面的切换展示功能。控件中已附带最基础的默认样式，同时提供API供开发者自行设置，只需传入对应布局文件或View即可生效。

当然，针对一些静态数据的列表，可能用不着上述切换展示的功能，所以也提供了对应的API进行使用，后面将会讲到。

------------
###[演示Demo.apk下载](https://github.com/EksDimension/ProjectResouce/blob/master/SLRecyclerView/testDemo.apk?raw=true)

------------

### 引用依赖：
| [ ![Download](https://api.bintray.com/packages/eksdimension/maven/SLRecyclerView/images/download.svg)](https://bintray.com/eksdimension/maven/SLRecyclerView/_latestVersionn) | latestVersion|
|--------|----|
##### Gradle工程
```gradle
implementation 'com.eks.view:SLRecyclerView:latestVersion'
```
##### Maven工程
```xml
<dependency>
  <groupId>com.eks.view</groupId>
  <artifactId>SLRecyclerView</artifactId>
  <version>latestVersion</version>
  <type>pom</type>
</dependency>
```

------------
### 基本使用：

> #### 案例一：单一列表，监听列表点击事件

![案例一](https://github.com/EksDimension/ProjectResouce/blob/master/SLRecyclerView/singlelist.gif?raw=true "案例一")

##### Activity XML布局文件
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".SingleListActivity">

    <com.eks.slrecyclerview.SLRecyclerView
        android:id="@+id/rvOrder"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

##### Activity 类
```java
public class SingleListActivity extends AppCompatActivity {
    /**
     * SLRecyclerView对象,泛型要指明为列表数据item的bean类型
     */
    protected SLRecyclerView<OrderBean> rvOrder;
    /**
     * 继承于SLAdapter的列表适配器
     */
    protected OrderAdapter orderAdapter;
    /**
     * 列表数据,要以ArrayList为类型,注意泛型要与SLRecyclerView指定的一致,即列表数据item的bean类型
     */
    protected ArrayList<OrderBean> orderList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_list);
        initView();
        setAdapter();
        setListener();
        setData();
    }

    private void initView() {
        //findview没啥好说
        rvOrder = findViewById(R.id.rvOrder);
    }

    protected void setAdapter() {
        //RecyclerView和adapter的构造及 LayoutManager设置之类的, 跟原始RecyclerView和Adapter基本一致.
        //没什么特别
        orderAdapter = new OrderAdapter(this);
        rvOrder.setLayoutManager(new LinearLayoutManager(this));
        rvOrder.setAdapter(orderAdapter);

        //关键代码adapter.setData(dataList)
        //执行该方法进行数据绑定, 建议在执行setAdapter()后 且请求数据之前执行
        orderAdapter.setData(orderList);
    }

    private void setListener() {
        //核心代码，监听列表
        //itemView点击监听的设置, 传入OnItemViewClickListener接口
        rvOrder.addOnItemViewClickListener(new SLRecyclerView.OnItemViewClickListener() {
            /**
             * 点击回调监听
             * @param clickBean 点击回调封装信息,里面封装了所有与点击相关的信息.
             */
            @Override
            public void onItemViewClick(@NotNull ClickBean<?> clickBean) {
                StringBuilder sb = new StringBuilder();
                for (View oneView : clickBean.viewsOnClick) {
                    sb.append(oneView.getTag()).append("\n");
                }
                Toast.makeText(SingleListActivity.this, "直接点击控件:" + clickBean.viewOnClick.getTag() + "\n\n点击范围所有控件:\n" + sb.toString()+ "\n\nitem数据类型:" + Objects.requireNonNull(clickBean.data).getClass().getSimpleName() + "\n\nitem数据:" + clickBean.data + "\n\n点击item位置:" + clickBean.position, Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * 这里异步请求，只需要在对dataList进行修改操作后调用notifyDataSetChanged()，即可展示列表数据。
     */
    protected void setData() {
        ...
        orderAdapter.notifyDataSetChanged();
    }
}
```

##### Item XML布局文件
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="100dp"
    android:layout_margin="10dp"
    android:background="#4312"
    android:tag="Item最底层背景">

    <ImageView
        android:id="@+id/ivProductImg"
        android:layout_width="50dp"
        android:layout_height="50dp"
        android:layout_marginStart="20dp"
        android:tag="图片ImageView"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        tools:src="@drawable/ic_launcher_background" />

    <TextView
        android:id="@+id/tvOrderSn"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:background="#0000ff"
        android:tag="蓝色TextView"
        android:layout_marginStart="5dp"
        android:textColor="#ffffff"
        android:textSize="18sp"
        app:layout_constraintStart_toEndOf="@+id/ivProductImg"
        app:layout_constraintTop_toTopOf="@+id/ivProductImg"
        tools:text="SN" />

    <TextView
        android:id="@+id/tvOrderType"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginStart="25dp"
        android:background="#ff0000"
        android:tag="红色TextView"
        android:textColor="#ffffff"
        android:textSize="20sp"
        app:layout_constraintStart_toStartOf="@+id/ivProductImg"
        android:layout_marginTop="10dp"
        app:layout_constraintTop_toBottomOf="@+id/tvOrderSn"
        tools:text="Type" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

##### Aapter 类
```java
public class OrderAdapter extends SLAdapter<OrderBean> {

    public OrderAdapter(@Nullable Context context) {
        super(context);
    }

    /**
     * 在这里指定布局样式
     */
    @Override
    public int setItemLayoutResId() {
        return R.layout.item_order;
    }

    /**
     * 在这里进行控件数据设值
     */
    @Override
    public void onBindViewHolder(@NotNull SLHolder holder, @Nullable OrderBean data, int position) {
        /**
         * 这里提供一个叫 getView(Int resId) 的方法,作用类似于findViewById
         * 而该方法拥有复用功能,多次执行也不用担心浪费资源损耗性能.推荐使用.
         */
        ((ImageView) getView(R.id.ivProductImg)).setImageResource(data.getProductImg());
        ((TextView) getView(R.id.tvOrderSn)).setText("单号:" + data.getOrderSn() + "");
        ((TextView) getView(R.id.tvOrderType)).setText(data.getOrderType());
    }
}
```

##### 那么Holder类呢？不用写啦~已经封好搞定了。






> #### 案例二：同一个接口实现监听双列表（多个也行）点击事件。

![](https://github.com/EksDimension/ProjectResouce/blob/master/SLRecyclerView/doublelist.gif?raw=true)

##### 为节省篇幅，从本案例起，仅列出Activity示范类，其余Adapter、xml暂不展示

```java
open class DoubleListActivity : AppCompatActivity() {
    ...
    其他的这些大同小异,无非就是多了一个列表
    ...

    /**
     * 2个不同的SLRecyclerView设置了同一个监听
     */
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
            Toast.makeText(this@DoubleListActivity, "当前列表:" + clickBean.sLRecyclerView.tag + "\n\n直接点击控件:" + clickBean.viewOnClick.tag + "\n\n点击范围所有控件:\n" + sb.toString() + "\n\nitem数据类型:" + clickBean.data?.javaClass?.simpleName + "\n\nitem数据:" + clickBean.data + "\n\n点击item位置:" + clickBean.position, Toast.LENGTH_LONG).show()
        }
    }
    
    ...
    都是没什么区别的
    ...
    
}
```


------------

### 自定义“正在加载”与“没有数据”及“加载失败”界面

> #### 案例一：让列表展示"正在加载/没有数据"。
加载图--左上自定义 右下默认
无数据--左上默认 右下自定义。

![](https://github.com/EksDimension/ProjectResouce/blob/master/SLRecyclerView/lelist.gif?raw=true)
