package com.example.mikkasstoreapp.Objects;

import java.util.Map;

public class Purchase {

    Items items;
    int purch_id, purch_tot_qty;
    String purch_payment_date, purch_status, purchase_emp_name, purchase_key;
    double purch_total_due;

    Map<String, Object> employee_cart;

    public Purchase() {
    }

    public Purchase(Items items, int purch_id, int purch_tot_qty, String purch_payment_date, String purch_status, String purchase_emp_name, double purch_total_due, Map<String, Object> employee_cart) {
        this.items = items;
        this.purch_id = purch_id;
        this.purch_tot_qty = purch_tot_qty;
        this.purch_payment_date = purch_payment_date;
        this.purch_status = purch_status;
        this.purchase_emp_name = purchase_emp_name;
        this.purch_total_due = purch_total_due;
        this.employee_cart = employee_cart;
    }

    public String getPurchase_emp_name() {
        return purchase_emp_name;
    }

    public void setPurchase_emp_name(String purchase_emp_name) {
        this.purchase_emp_name = purchase_emp_name;
    }

    public Items getItems() {
        return items;
    }

    public void setItems(Items items) {
        this.items = items;
    }

    public int getPurch_id() {
        return purch_id;
    }

    public void setPurch_id(int purch_id) {
        this.purch_id = purch_id;
    }

    public int getPurch_tot_qty() {
        return purch_tot_qty;
    }

    public void setPurch_tot_qty(int purch_tot_qty) {
        this.purch_tot_qty = purch_tot_qty;
    }

    public String getPurch_payment_date() {
        return purch_payment_date;
    }

    public void setPurch_payment_date(String purch_date) {
        this.purch_payment_date = purch_date;
    }

    public String getPurch_status() {
        return purch_status;
    }

    public void setPurch_status(String purch_status) {
        this.purch_status = purch_status;
    }

    public double getPurch_total_due() {
        return purch_total_due;
    }

    public void setPurch_total_due(double purch_total_due) {
        this.purch_total_due = purch_total_due;
    }

    public Map<String, Object> getEmployee_cart() {
        return employee_cart;
    }

    public void setEmployee_cart(Map<String, Object> employee_cart) {
        this.employee_cart = employee_cart;
    }

    public String getPurchase_key() {
        return purchase_key;
    }

    public void setPurchase_key(String purchase_key) {
        this.purchase_key = purchase_key;
    }

    //
//    public Map<String, Object> getEmployee_purchases() {
//        return employee_purchases;
//    }
//
//    public void setEmployee_purchases(Map<String, Object> employee_purchases) {
//        this.employee_purchases = employee_purchases;
//    }
}
