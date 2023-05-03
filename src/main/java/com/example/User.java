package com.example;

public class User {

    private String name;

    private int age;

    public User() {
        name = "quan";
    }

    public User(String name) {
        this.name = name;
    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // 静态方法
    public static void play(String game) {
        System.out.println("playing: " + game);
    }

    static String sleep() {
        return "sleep good";
    }

    public void eat(String food) {
        System.out.println("eating: " + food);
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "com.example.User{" +
                "name='" + name + '\'' +
                '}';
    }
}
