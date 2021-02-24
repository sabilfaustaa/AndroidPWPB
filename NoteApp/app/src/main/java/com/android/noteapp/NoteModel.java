package com.android.noteapp;

public class NoteModel {
    int id;
    String header;
    String text;

    public NoteModel(int id, String header, String text) {
        this.id = id;
        this.header = header;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public String getHeader() {
        return header;
    }

    public String getText() {
        return text;
    }
}
