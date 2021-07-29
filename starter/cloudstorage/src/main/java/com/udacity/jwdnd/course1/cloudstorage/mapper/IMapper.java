package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.AbstractModel;
import com.udacity.jwdnd.course1.cloudstorage.model.IForm;

import java.util.List;

public interface IMapper<T extends AbstractModel>{
    int insert(IForm<T> form);

    List<T> getByUserId(int userId);

    int deleteByUserIdAndId(int userid, int id);

    int deleteALL();

    int update(IForm<T> form);
}
