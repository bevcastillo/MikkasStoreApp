package com.example.mikkasstoreapp.Objects;

public class Itemlistdata {
    private String item_name, item_category;
    double item_price, item_subtotal;
    int item_qty, item_stock;
    String item_purch_date;

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_category() {
        return item_category;
    }

    public void setItem_category(String item_category) {
        this.item_category = item_category;
    }

    public double getItem_price() {
        return item_price;
    }

    public void setItem_price(double item_price) {
        this.item_price = item_price;
    }

    public double getItem_subtotal() {
        return item_subtotal;
    }

    public void setItem_subtotal(double item_subtotal) {
        this.item_subtotal = item_subtotal;
    }

    public int getItem_qty() {
        return item_qty;
    }

    public void setItem_qty(int item_qty) {
        this.item_qty = item_qty;
    }

    public int getItem_stock() {
        return item_stock;
    }

    public void setItem_stock(int item_stock) {
        this.item_stock = item_stock;
    }

    public String getItem_purch_date() {
        return item_purch_date;
    }

    public void setItem_purch_date(String item_purch_date) {
        this.item_purch_date = item_purch_date;
    }
}
