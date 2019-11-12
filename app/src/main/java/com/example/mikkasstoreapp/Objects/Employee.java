package com.example.mikkasstoreapp.Objects;

public class Employee {
    private String empImage, empName;

    public Employee() {
    }

    public Employee(String empImage, String empName) {
        this.empImage = empImage;
        this.empName = empName;
    }

    public String getEmpImage() {
        return empImage;
    }

    public void setEmpImage(String empImage) {
        this.empImage = empImage;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }
}
