package com.example.mikkasstoreapp.Objects;

public class EmployeeCart {
    Items items;

    public EmployeeCart() {
    }

    public EmployeeCart(Items items) {
        this.items = items;
    }

    public Items getItems() {
        return items;
    }

    public void setItems(Items items) {
        this.items = items;
    }
}
