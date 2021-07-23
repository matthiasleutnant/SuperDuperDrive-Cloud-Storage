package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialModel;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES(#{url}, #{username}, #{key}, #{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    int insert(CredentialModel credentialModel);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
    List<CredentialModel> getCredentialByUserId(int userId);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    CredentialModel getCredentialByCredentialId(int credentialid);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialid} AND userid = #{userid}")
    int deleteCredentialByCredentialId(int credentialid, int userid);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username= #{username}, password= #{password} WHERE credentialid = #{credentialid}")
    void updateCredential(CredentialModel credentialModel);

    @Delete("DELETE FROM CREDENTIALS")
    int deleteALL();
}
