package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper extends IMapper<NoteModel>{

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{title}, #{description}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(NoteForm noteForm);

    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    List<NoteModel> getByUserId(int userId);

    @Delete("DELETE FROM NOTES WHERE id = #{id} AND userid=#{userid}")
    int deleteByUserIdAndId(int userid, int id);

    @Update("UPDATE NOTES SET notetitle = #{title}, notedescription= #{description} WHERE id = #{id}")
    int update(NoteForm noteForm);

    @Delete("DELETE FROM NOTES")
    int deleteALL();
}
