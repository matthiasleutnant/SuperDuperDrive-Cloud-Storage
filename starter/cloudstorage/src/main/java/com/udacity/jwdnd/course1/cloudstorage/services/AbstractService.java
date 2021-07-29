package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.IMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.AbstractModel;
import com.udacity.jwdnd.course1.cloudstorage.model.IForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public abstract class AbstractService<T extends AbstractModel> implements IService<T>{

    protected final IMapper<T> mapper;
    protected final UserService userService;

    public AbstractService(IMapper<T> mapper, UserService userService) {
        this.mapper = mapper;
        this.userService = userService;
    }

    @Override
    public int store(String username, IForm<T> form) {
        User user = userService.getUser(username);
        form.setUserid(user.getUserId());
        return mapper.insert(form);
    }

    @Override
    public List<T> get(String username) {
        User user = userService.getUser(username);
        return mapper.getByUserId(user.getUserId());
    }

    @Override
    public int edit(IForm<T> form) {
        return mapper.update(form);
    }

    @Override
    public int delete(String username, int id) {
        User user = userService.getUser(username);
        return mapper.deleteByUserIdAndId(user.getUserId(), id);
    }
}
