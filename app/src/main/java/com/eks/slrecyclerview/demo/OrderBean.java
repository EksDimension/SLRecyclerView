package com.eks.slrecyclerview.demo;

/**
 * Created by Riggs on 3/13/2020
 */
public class OrderBean {

    private int orderSn;
    private int orderType;
    private int productImg;

    public int getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(int orderSn) {
        this.orderSn = orderSn;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public int getProductImg() {
        return productImg;
    }

    public void setProductImg(int productImg) {
        this.productImg = productImg;
    }

    public OrderBean(int orderSn, int orderType, int productImg) {
        this.orderSn = orderSn;
        this.orderType = orderType;
        this.productImg = productImg;
    }
}
