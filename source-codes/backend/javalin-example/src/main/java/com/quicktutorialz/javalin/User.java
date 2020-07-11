package com.quicktutorialz.javalin;

public class User {
    private String title;
    private String name;
    private String surname;

    public User() {
    }

    public User(String title, String name, String surname) {
        this.title = title;
        this.name = name;
        this.surname = surname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
