package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialModel;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper extends IMapper<CredentialModel> {

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES(#{url}, #{username}, #{key}, #{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(CredentialForm credentialForm);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid}")
    List<CredentialModel> getByUserId(int userId);

    @Delete("DELETE FROM CREDENTIALS WHERE id = #{id} AND userid=#{userid}")
    int deleteByUserIdAndId(int id, int userid);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username= #{username}, key= #{key}, password= #{password} WHERE id = #{id}")
    int update(CredentialForm credentialForm);

    @Delete("DELETE FROM CREDENTIALS")
    int deleteALL();
}
