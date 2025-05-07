package com.example.lb_android.models;

import java.util.Objects;

public class Car {
    private String brand;
    private String model;
    private int year;
    private String description;
    private double cost;
    private String photoName;

    public Car() {
    }

    public Car(String brand, String model, int year, String description, double cost, String photoName) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.description = description;
        this.cost = cost;
        this.photoName = photoName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return year == car.year && Double.compare(cost, car.cost) == 0 && Objects.equals(brand, car.brand) && Objects.equals(model, car.model) && Objects.equals(description, car.description) && Objects.equals(photoName, car.photoName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brand, model, year, description, cost, photoName);
    }

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", description='" + description + '\'' +
                ", cost=" + cost +
                ", photoName='" + photoName + '\'' +
                '}';
    }
}