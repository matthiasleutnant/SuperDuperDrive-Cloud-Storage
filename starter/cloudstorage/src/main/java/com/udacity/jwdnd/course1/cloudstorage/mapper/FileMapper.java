package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.FileModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES(#{filename}, #{contenttype}, #{filesize}, #{userId}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(FileModel fileModel);


    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    List<FileModel> getFileByUserId(int userId);

    @Select("SELECT * FROM FILES WHERE filename = #{filename} AND userid=#{userid}")
    FileModel getFileByFileNameAndUserId(int userid, String filename);

    @Delete("DELETE FROM FILES WHERE fileid = #{fileid} AND userid=#{userid}")
    int deleteFileByUserIdAndFileid(int userid, int fileid);

    @Delete("DELETE FROM FILES")
    int deleteALL();
}
