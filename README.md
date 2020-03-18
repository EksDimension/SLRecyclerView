# 一款拥有Item自监听能力的RecyclerView

### 你是否有过这种烦恼？
###### 在MVC、MVP模式碰上列表页面开发时，特别是Item复杂，按键事件交互繁多的需求时，需要在Adapter对每个控件进行setOnClickListener()进行监听设置。

###### 又为了把事件传递给Activity/Fragment，专门设置接口进行交互而产生了接口地狱，又或者动用到广播导致注册/注销难以管理、甚至乎使用eventbus第三方框架。

###### 最终导致列表代码繁杂，维护成本提高。

#### 如果真的有，那很有幸能在此相识。 I got a present for ya.
###### 因为这里有一款也许能帮到你的控件 —— SLRecyclerView


