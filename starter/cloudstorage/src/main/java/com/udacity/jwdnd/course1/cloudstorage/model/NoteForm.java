package com.udacity.jwdnd.course1.cloudstorage.model;

public class NoteForm {
    private String username;
    private String title;
    private String description;

    public NoteForm(String username, String title, String description) {
        this.username = username;
        this.title = title;
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
