package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES(#{url}, #{username}, #{key}, #{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    int insert(NoteModel noteModel);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
    List<NoteModel> getNoteByUserId(int userId);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    int deleteNoteByCredentialId(int credentialid);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username= #{username}, password= #{password} WHERE noteid = #{credentialid}")
    void updateNote(int credentialid, String url, String username,String password);

    @Delete("DELETE FROM CREDENTIALS")
    int deleteALL();
}
