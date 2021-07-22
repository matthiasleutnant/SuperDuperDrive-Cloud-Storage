package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.FileModel;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{notetitle}, #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int insert(NoteModel noteModel);

    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    List<NoteModel> getNoteByUserId(int userId);


    @Delete("DELETE FROM NOTES WHERE noteid = #{noteid} AND userid=#{userid}")
    int deleteNoteByUserIdAndNoteid(int userid, int noteid);

    @Update("UPDATE NOTES SET notetitle = #{notetitle}, notedescription= #{notedescription} WHERE noteid = #{noteid}")
    void updateNote(int noteid, String notetitle, String notedescription);

    @Delete("DELETE FROM NOTES")
    int deleteALL();
}
