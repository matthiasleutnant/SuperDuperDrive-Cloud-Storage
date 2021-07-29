package com.udacity.jwdnd.course1.cloudstorage.model;

public class NoteForm extends AbstractForm<NoteModel> implements IForm<NoteModel>{
    private String title;
    private String description;

    public NoteForm(String title, String description, Integer id) {
        this.title = title;
        this.description = description;
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
