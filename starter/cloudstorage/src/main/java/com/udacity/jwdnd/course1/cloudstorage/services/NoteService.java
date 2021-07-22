package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteModel;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private final NoteMapper noteMapper;
    private final UserService userService;

    public NoteService(NoteMapper noteMapper, UserService userService) {
        this.noteMapper = noteMapper;
        this.userService = userService;
    }

    public int storeNote(String username, NoteForm noteForm) {
        NoteModel noteModel;
        User user = userService.getUser(username);
        noteModel = new NoteModel(noteForm.getTitle(), noteForm.getDescription(), user.getUserId());
        return noteMapper.insert(noteModel);
    }

    public List<NoteModel> getNotes(String username) {
        User user = userService.getUser(username);
        return noteMapper.getNoteByUserId(user.getUserId());
    }

    public NoteModel getNote(String username, String title) {
        User user = userService.getUser(username);
        return noteMapper.getNoteByUserIdAndTitle(user.getUserId(), title);
    }

    public void editNote(NoteForm noteForm) {
        noteMapper.updateNote(noteForm.getId(),noteForm.getTitle(),noteForm.getDescription());
    }

    public void deleteNote(String username, String title) {
        User user = userService.getUser(username);
        noteMapper.deleteNoteByUserIdAndNotetitle(user.getUserId(), title);
    }
}
