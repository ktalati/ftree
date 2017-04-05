package com.example.util;

public enum PhoneEnum {

    LANDLINE(0, "landLine"),
    MOBILE(1, "mobile");

    private int id;
    private String name;

    PhoneEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}