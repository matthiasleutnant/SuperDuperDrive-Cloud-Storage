package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.IMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteModel;
import org.springframework.stereotype.Service;


@Service
public class NoteService extends AbstractService<NoteModel> implements IService<NoteModel> {
    public NoteService(IMapper<NoteModel> mapper, UserService userService) {
        super(mapper, userService);
    }
}
