package com.example.signo.my_notepad.Models;

/**
 * Created by signo on 5/28/2017.
 */

public class Note {
    private int id;
    private String title;
    private String text;

    public Note(){}

    public Note(int id, String title){
        this.id = id;
        this.title = title;
    }

    public Note(int id, String title, String text){
        this.id = id;
        this.title = title;
        this.text = text;
    }

    public Note(String title, String text){
        this.title = title;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
