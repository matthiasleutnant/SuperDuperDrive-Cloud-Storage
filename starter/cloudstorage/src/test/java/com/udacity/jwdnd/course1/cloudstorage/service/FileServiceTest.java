package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.model.FileModel;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FileServiceTest {
    @Autowired
    FileService fileService;
    @Autowired
    UserService userService;

    private String firstname = "Matthias";
    private String lastname = "Leutnant";
    private String username = "Matze";
    private String password = "superSecurePassword123";

    @Test
    void testFileStoring() throws IOException {
        MultipartFile file = new MockMultipartFile("file.test","testFile".getBytes());
        User user = new User(0,username,"abc",password,firstname,lastname);
        userService.storeUser(user);
        fileService.storeFile(username,file);
        List<FileModel> files = fileService.getFiles(username);
        assertThat(files.get(0).getFiledata()).isEqualTo("testFile".getBytes());
    }
}
