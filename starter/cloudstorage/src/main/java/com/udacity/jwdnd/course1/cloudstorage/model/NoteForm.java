package com.udacity.jwdnd.course1.cloudstorage.model;

public class NoteForm {
    private String title;
    private String description;
    private Integer id;

    public NoteForm(String title, String description, Integer id) {
        this.title = title;
        this.description = description;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
