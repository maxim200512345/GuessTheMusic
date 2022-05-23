package com.codecademy.app.models;

public class SongsItem {
    String name;
    int ref;
    String author;

    public SongsItem(String name, int ref, String author) {
        this.name = name;
        this.ref = ref;
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public int getRef() {
        return ref;
    }
    public String getAuthor(){return author;}
}
