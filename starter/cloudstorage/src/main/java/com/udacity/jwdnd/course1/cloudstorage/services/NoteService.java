package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.IMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteModel;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService extends AbstractService<NoteModel> implements IService<NoteModel>{


    public NoteService(IMapper<NoteModel> mapper, UserService userService) {
        super(mapper, userService);
    }

    public int store(String username, NoteForm noteForm) {
        User user = userService.getUser(username);
        noteForm.setUserid(user.getUserId());
        return mapper.insert(noteForm);
    }

    public List<NoteModel> getNotes(String username) {
        User user = userService.getUser(username);
        return mapper.getByUserId(user.getUserId());
    }

    public int editNote(NoteForm noteForm) {
        return mapper.update(noteForm);
    }

    public int deleteNote(String username, int noteid) {
        User user = userService.getUser(username);
        return mapper.deleteByUserIdAndId(user.getUserId(), noteid);
    }
}
