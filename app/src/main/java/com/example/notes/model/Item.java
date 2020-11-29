package com.example.notes.model;

public class Item {
    private int id;
    private String noteTitle;
    private String noteText;
    private String dateNoteAdded;

    public Item() {
    }

    public Item(String noteTitle, String noteText, String dateNoteAdded) {
        this.noteTitle = noteTitle;
        this.noteText = noteText;
        this.dateNoteAdded = dateNoteAdded;
    }

    public Item(int id, String noteTitle, String noteText, String dateNoteAdded) {
        this.id = id;
        this.noteTitle = noteTitle;
        this.noteText = noteText;
        this.dateNoteAdded = dateNoteAdded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public String getDateNoteAdded() {
        return dateNoteAdded;
    }

    public void setDateNoteAdded(String dateNoteAdded) {
        this.dateNoteAdded = dateNoteAdded;
    }
}
