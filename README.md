# SLRecyclerView

### 一、你是否有过这种烦恼？
在MVC、MVP模式碰上列表页面开发时，特别是Item复杂，按键事件交互繁多的需求时，需要在Adapter对每个控件进行setOnClickListener()进行监听设置。

又为了把事件传递给Activity/Fragment，专门设置接口进行交互而产生了接口地狱，又或者动用到广播导致注册/注销难以管理、甚至乎使用eventbus第三方框架。

最终导致列表代码繁杂，维护成本提高。

#### 如果有，那希望能帮到你。 I've got a present for ya.
###### 这里有一款也许能帮到你的控件 —— SLRecyclerView

### [演示Demo.apk下载](https://github.com/EksDimension/ProjectResouce/blob/master/SLRecyclerView/testDemo.apk?raw=true)

------------

### 二、简介：

#### SLRecyclerView是什么、自监听是啥、使用复杂么？

SLRecyclerView是一款针对在传统MVC、MVP开发模式下（熟悉MVVM流程的小伙伴们应该都知道我为啥只点MVC、MVP的名），解决因为Item交互繁杂而带来的代码激增问题的控件。

开发者只需要按文档提示，设置指定接口，便能全自动监听指定列表中item上所有view的点击事件，从此摆脱Adapter与Activity/Fragment的交互耦合，也不用再为控件的点击监听而操心。

除了增加少量规则代码外，其余使用流程均向原生RecyclerView靠拢，简单易用、确保开发者在短时间内能快速适应并使用。

而作为改造封装控件，对一些原生功能也进行了简化处理，让开发者用起来更为便利。

#### 除了自监听，这SLRecyclerView还有什么特点？

除了自监听功能，这款控件还支持“正在加载”、“无数据”及“加载失败”三种界面的切换展示功能。控件中已附带最基础的默认样式，同时提供API供开发者自行设置，只需传入对应布局文件或View即可生效。

当然，针对一些静态数据的列表，可能用不着上述切换展示的功能，所以也提供了对应的API进行使用，后面将会讲到。

------------

### 三、引用依赖：
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
### 四、基本使用：

> ### 案例一：单一列表，监听列表点击事件

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
     * 列表数据,要以集合为类型,注意泛型要与SLRecyclerView指定的一致,即列表数据item的bean类型
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
        //关键代码adapter.setData(dataList)
        //执行该方法进行数据绑定
        orderAdapter.setData(orderList);
        rvOrder.setLayoutManager(new LinearLayoutManager(this));
        rvOrder.setAdapter(orderAdapter);
    }

    private void setListener() {
        //核心代码，监听列表
        //itemView点击监听的设置, 传入OnItemViewClickListener接口
        rvOrder.addOnItemViewClickListener(new SLRecyclerView.OnItemViewClickListener() {
            /**
             * 点击回调监听
             * @param clickBean 点击回调封装信息,里面封装了所有与点击相关的信息.
             *                  ClickBean详解请见本文后方的“API”小节
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






> ### 案例二：同一个接口实现监听双列表（多个也行）点击事件。

![](https://github.com/EksDimension/ProjectResouce/blob/master/SLRecyclerView/doublelist.gif?raw=true)

##### 为节省篇幅，从本案例起，仅列出Activity示范类，并使用kotlin演示。其余Adapter、xml暂不展示。

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

### 五、进阶使用，自定义“正在加载”与“没有数据”及“加载失败”界面：

> ### 案例一：让列表展示"正在加载/没有数据"。
加载图--左上自定义 右下默认
无数据--左上默认 右下自定义。

![](https://github.com/EksDimension/ProjectResouce/blob/master/SLRecyclerView/lelist.gif?raw=true)

```java
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
```

> ### 案例二：让列表展示“加载失败”。
左上自定义 右下默认

![](https://github.com/EksDimension/ProjectResouce/blob/master/SLRecyclerView/failedlist.gif?raw=true)

```java
class FailedListActivity : DoubleListActivity() {
    override fun initView() {
        super.initView()
        rvOrder?.setLoadFailedView(R.layout.failed_bilibili)//设置自定义的资源布局作为加载失败页
    }

    override fun setOrderData() {
        Handler().postDelayed({
            //执行showLoadFailed()会自动切换成加载失败页面
            rvOrder?.showLoadFailed()
        }, 1500)
    }

    override fun setAddressData() {
        Handler().postDelayed({
            //执行showLoadFailed()会自动切换成加载失败页面
            rvAddress?.showLoadFailed()
        }, 2500)
    }

}
```


> ### 案例三：让列表展示自定义的"加载失败"界面,并拥有交互性。

![](https://github.com/EksDimension/ProjectResouce/blob/master/SLRecyclerView/failedinteractivelist.gif?raw=true)

```java
class FailedInteractiveActivity : SingleListActivity() {

    override fun setAdapter() {
        super.setAdapter()
        //同样是执行setLoadFailedView()方法,但以View作为传入参数.
        //在View上自定义交互控件以及编写交互事件,即可在加载失败后进行交互操作.
        val failedView = View.inflate(this, R.layout.failed_bilibili_interactive, null)
        failedView.findViewById<Button>(R.id.btnRetry).setOnClickListener { retry() }
        rvOrder?.setLoadFailedView(failedView)
    }


    override fun setData() {
        Handler().postDelayed({
            rvOrder?.showLoadFailed()
        }, 1500)
    }

    /**
     * 此处模拟加载失败后,点击按钮进行重试的情形
     * 而重试过程中需要显示"正在加载",因此需要手动调用showLoadingView()
     */
    private fun retry() {
        rvOrder?.showLoadingView()
        Handler().postDelayed({
            ...
            ...
            orderAdapter.notifyDataSetChanged()
        }, 1500)
    }
}
```

------------

### 六、API：

##### 下方出现的< T >泛型，特指传入列表的数据类型

##### 类SLRecyclerView< T >

| 方法 | 意义  |  参数说明  |
| ------------ | ------------ | ------------ |
| setAdapter(adapter: SLAdapter< T >)  |  为该列表设置适配器。执行该方法后，数据展示之前，会自动展示“正在加载”界面。一般用于异步加载。  | 继承自SLAdapter的适配器 |
| setAdapterWithoutLoading(adapter: SLAdapter< T >)  |  为该列表设置适配器。执行该方法后，会默认隐藏“正在加载”界面。一般用于异步加载。  | 继承自SLAdapter的适配器 |
| setAdapterAndNotify(adapter: SLAdapter< T >)  |  为该列表设置适配器。该方法会直接展示列表数据，同样默认隐藏“正在加载”界面，一般用于同步加载。比如静态数据加载。  | 继承自SLAdapter的适配器 |
| setEmptyView(emptyViewRes: Int)  |  为该列表添加“无数据”展示界面，不执行则以默认样式展示。  | 要求传入layout资源布局 |
| setLoadingView(loadingViewRes: Int)  |  为该列表添加“正在加载”展示界面，不执行则以默认样式展示。  |  要求传入layout资源布局 |
| setLoadFailedView(loadFailedViewRes: Int)  |  为该列表添加“加载失败”展示界面，不执行则以默认样式展示。  |  要求传入layout资源布局 |
| setLoadFailedView(loadFailedView: View)  |  为该列表添加“加载失败”展示界面，并可在界面上作自定义交互事件，不执行则以默认样式展示。  |  要求传入View  |
| showLoadingView()  |  手动展示“正在加载”界面。注：只有通过setAdapter()设置适配器，才会setAdapter后首次自动展示，其余情况均必须手动执行才能展示。  |    |
| showLoadFailed()  |  手动展示“加载”界面。  |    |
| addOnItemViewClickListener(onItemViewClickListener: OnItemViewClickListener)  |  对该列表item中所有view进行监听器，。  |  OnItemViewClickListener实现对象  |


##### 接口OnItemViewClickListener中的回调返回对象ClickBean< T >

| 属性 | 意义  |
| ------------ | ------------ |
| sLRecyclerView: SLRecyclerView< T >  |  当前点击所处的RecylerView对象  |
| viewOnClick: View  |  当前item上被直接点击的View  |
| viewsOnClick: ArrayList<View>  |  当前item中,摆放在点击位置的上所有的View集合  |
| data: Any?  |  当前item的数据，考虑到通用性，故不会限制为T泛型对应的类型，而是转为Any（Java中的Object），开发者可自行转型  |
| position: Int  |  当前item的position位置  |


##### 类SLAdapter< T >

| 方法 | 意义  |  参数说明  | 返回值说明 |
| ------------ | ------------ | ------------ | ------------ |
| setData(dataList: List< T >)  |  为适配器设置数据，只需设置一次，建议在RecyclerView.setAdapter()前执行  | 数据集合 |  |
| getData():List< T >  |  获取当前数据集合  |  | 返回当前数据集合 |
| setItemLayoutResId() : Int  |  设置item的布局资源Id(抽象重写方法)  |  | 在返回值传入xml布局资源即可 |
| onBindViewHolder(holder: SLHolder, data: T?, position: Int)  |  item绑定(抽象重写方法，item的数据设值在这里进行)  | 1.holder：当前Adapter所创建的viewHolder对象。  2.data：当前position位置的数据实体。   3.position：当前item位置。| |
| <V : View> getView(viewResId: Int): V?  |  相当于做了复用优化的findViewById，返回View对象  | view资源Id | 返回View对象 |


------------

### 六、大家可能会问：

> #### Q:横着来的列表可以监听到么？
##### A:肯定可以，只是文档篇幅太长没展示罢了。

> #### Q:为何不加入下拉刷新、上拉加载功能？这不是很流行么？
##### A:考虑因素很多：
###### 1.本控件专注于提供解决因item控件交互繁杂而面临的代码激增的解决方案，而刷新加载功能不在解决方案之中。
###### 2.下拉刷新、上拉加载这种功能，有大量成熟第三方框架可供使用，毕竟闻道有先后，术业有专攻。那些第三方框架在这刷新加载领域更为专业。
###### 3.本控件作为自身已经是第三方控件，从原则来分析就应该尽量少引用第三方的控件。否则很可能会对开发者带来严重的第三方框架冲突问题，可谓得不偿失。也避免本控件变得过于臃肿。
###### 4.并非所有含有列表页面都有刷新、加载功能。情形繁多复杂，应该由开发者自身决定是对界面进行刷新/加载还是对列表进行刷新/加载操作，再结合实际自行接入专用第三方框架予以实现。这样才更为灵活。


> #### Q:一个SLAdapter只允许设置一个布局了？不支持多样式item么？
##### A:目前阶段来说，暂时就是这样的。
###### 因为考虑到dataList数据集合的类型是完全自由的，本控件暂时不方便强制要求开发者传入item的类型标记（没有item类型标记，就没有多样式。众所周知，多样式列表的最基本原则，就是根据指定的类型标记动态切换不同的item样式，既然暂不方便强制要求传入，那自然就暂无法判别样式类型，也就自然不支持多样式item了）。
###### 当然，不排除本控件在日后的新版本支中持该功能。
