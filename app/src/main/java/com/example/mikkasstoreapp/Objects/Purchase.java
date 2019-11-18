package com.example.mikkasstoreapp.Objects;

import java.util.Map;

public class Purchase {
    Employee employee;
    Items items;
    int purch_id, purch_qty, purch_total;

    Map<String, Object> employee_purchases;

    public Purchase() {
    }

    public Purchase(Employee employee, Items items, int purch_id, int purch_qty, int purch_total) {
        this.employee = employee;
        this.items = items;
        this.purch_id = purch_id;
        this.purch_qty = purch_qty;
        this.purch_total = purch_total;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
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

    public int getPurch_qty() {
        return purch_qty;
    }

    public void setPurch_qty(int purch_qty) {
        this.purch_qty = purch_qty;
    }

    public int getPurch_total() {
        return purch_total;
    }

    public void setPurch_total(int purch_total) {
        this.purch_total = purch_total;
    }

    public Map<String, Object> getEmployee_purchases() {
        return employee_purchases;
    }

    public void setEmployee_purchases(Map<String, Object> employee_purchases) {
        this.employee_purchases = employee_purchases;
    }
}
