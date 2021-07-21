package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    public void storeNote(String username, NoteForm noteForm) {

    }

    public List<NoteForm> getNotes(String username) {
        return null;
    }

    public void editNote(String username, String oldTitle, NoteForm noteForm) {

    }

    public void deleteNote(String username, String title) {

    }
}
