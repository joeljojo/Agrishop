package com.example.peas.Models;


public class Cart {

    private String pid, pname,  price, quantity, discount,sellerId,date,time,Pavail;

    public Cart() {
    }

    public Cart(String pid, String pname, String price, String quantity, String discount, String sellerId, String date, String time, String pavail) {
        this.pid = pid;
        this.pname = pname;
        this.price = price;
        this.quantity = quantity;
        this.discount = discount;
        this.sellerId = sellerId;
        this.date = date;
        this.time = time;
        Pavail = pavail;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
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

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPavail() {
        return Pavail;
    }

    public void setPavail(String pavail) {
        Pavail = pavail;
    }
}

