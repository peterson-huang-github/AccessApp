package com.example.accessapp;

public class UserModel {

    public final static String PHOTO = "photo";
    public final static String NAME = "name";

    private String photo;
    private String name;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
