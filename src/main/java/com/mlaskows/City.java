package com.mlaskows;

public class City {
    private Integer id;
    private Double y;
    private Double x;

    public City(Integer id, Double y, Double x) {
        this.id = id;
        this.y = y;
        this.x = x;
    }

    public Integer getId() {
        return id;
    }

    public Double getY() {
        return y;
    }

    public Double getX() {
        return x;
    }
}
