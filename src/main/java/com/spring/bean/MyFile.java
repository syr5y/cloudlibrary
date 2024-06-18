package com.spring.bean;

public class MyFile {
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MyFile(String name) {
        this.name = name;
    }

    public MyFile() {
    }

    @Override
    public String toString() {
        return "MyFile{" +
                "name='" + name + '\'' +
                '}';
    }
}
