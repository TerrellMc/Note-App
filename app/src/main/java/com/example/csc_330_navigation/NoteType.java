package com.example.csc_330_navigation;

public class NoteType {
    String title, content;
    int noteId;


    public NoteType(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public NoteType(String title, String content, int noteId) {
        this.title = title;
        this.content = content;
        this.noteId = noteId;
    }
}
