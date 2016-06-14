package com.project.yuvraj.parsing;

import java.text.SimpleDateFormat;

/**
 * Created by Lenovo on 5/17/2016.
 */
public class Cart {

    String id;
    String url;
    String price;
    String quantity;
    String name;
    String sl;
    String date;
    String orderid;




  /*  public Cart(String id, String url, String quantity, String price, String name) {
        this.id = id;
        this.url = url;
        this.quantity = quantity;
        this.price = price;
        this.name = name;
    }*/

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getSl() {
        return sl;
    }

    public void setSl(String sl) {
        this.sl = sl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }
}
