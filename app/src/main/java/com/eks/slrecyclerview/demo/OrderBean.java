package com.eks.slrecyclerview.demo;

/**
 * Created by Riggs on 3/13/2020
 */
public class OrderBean {

    private int orderSn;
    private String orderType;
    private int productImg;

    public int getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(int orderSn) {
        this.orderSn = orderSn;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public int getProductImg() {
        return productImg;
    }

    public void setProductImg(int productImg) {
        this.productImg = productImg;
    }

    public OrderBean(int orderSn, String orderType, int productImg) {
        this.orderSn = orderSn;
        this.orderType = orderType;
        this.productImg = productImg;
    }

    @Override
    public String toString() {
        return "OrderBean{" +
                "orderSn=" + orderSn +
                ", orderType=" + orderType +
                ", productImg=" + productImg +
                '}';
    }
}
