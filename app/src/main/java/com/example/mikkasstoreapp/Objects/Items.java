package com.example.mikkasstoreapp.Objects;

public class Items {
    private String item_name, item_category;
    double item_price;
    int item_qty;

    public Items() {
    }

    public Items(String item_name, String item_category, double item_price, int item_qty) {
        this.item_name = item_name;
        this.item_category = item_category;
        this.item_price = item_price;
        this.item_qty = item_qty;
    }

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

    public int getItem_qty() {
        return item_qty;
    }

    public void setItem_qty(int item_qty) {
        this.item_qty = item_qty;
    }
}
