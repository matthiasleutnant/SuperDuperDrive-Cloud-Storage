package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.IForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteModel;

import java.util.List;

public interface IService<T>{
    public int store(String username, IForm<T> form);
    public List<T> get(String username);
    public int edit(IForm<T> form);
    public int delete(String username, int id);
}
