package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.FileModel;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {
    private final FileMapper fileMapper;
    private final UserService userService;

    public FileService(FileMapper fileMapper, UserService userService) {
        this.fileMapper = fileMapper;
        this.userService = userService;
    }

    public int storeFile(String username, MultipartFile file) throws IOException {
        User user = userService.getUser(username);
        return fileMapper.insert(new FileModel(file.getOriginalFilename(), file.getContentType(), file.getSize() + " Bytes", user.getUserId(), file.getBytes()));
    }

    public List<FileModel> getFiles(String username) {
        User user = userService.getUser(username);
        return fileMapper.getFileByUserId(user.getUserId());
    }

    public FileModel getFile(String username, String filename){
        User user = userService.getUser(username);
        FileModel file = fileMapper.getFileByFileNameAndUserId(user.getUserId(),filename);
        return file;
    }

    public int deleteFile(String username, int fileid) {
        User user = userService.getUser(username);
        return fileMapper.deleteFileByUserIdAndFileid(user.getUserId(), fileid);
    }
}
