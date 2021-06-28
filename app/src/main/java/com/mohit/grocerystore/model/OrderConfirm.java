package com.mohit.grocerystore.model;

public class OrderConfirm {
    String name;
    String id;
    String orderNo;
    String currentDateandTime;
    Double _totalAmount;

    OrderConfirm(){
    }

    public OrderConfirm(String name, String id, String orderNo, String currentDateandTime, Double _totalAmount) {
        this.name = name;
        this.id = id;
        this.orderNo = orderNo;
        this.currentDateandTime = currentDateandTime;
        this._totalAmount = _totalAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCurrentDateandTime() {
        return currentDateandTime;
    }

    public void setCurrentDateandTime(String currentDateandTime) {
        this.currentDateandTime = currentDateandTime;
    }

    public Double get_totalAmount() {
        return _totalAmount;
    }

    public void set_totalAmount(Double _totalAmount) {
        this._totalAmount = _totalAmount;
    }
}
