package com.bot.model;

public class Student {

    private String name;
    private String surname;
    private String patronymic;
    private String nickname;
    private String password;
    private int course;
    private int id;

    public Student() {
    }

    public String getNickname() {
        return nickname;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }
}
