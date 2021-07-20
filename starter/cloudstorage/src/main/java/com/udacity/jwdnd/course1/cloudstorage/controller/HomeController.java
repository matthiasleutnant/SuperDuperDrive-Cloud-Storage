package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.FileModel;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final FileService fileService;

    public HomeController(FileService fileService) {
        this.fileService = fileService;
    }

    @RequestMapping
    public String getHomepage(Authentication authentication, Model model) {
        model.addAttribute("filemodel", fileService.getFiles(authentication.getName()));
        return "home";
    }

    @PostMapping
    public String postUpload(Authentication authentication, @RequestParam("fileUpload") MultipartFile fileUpload, Model model) throws IOException {
        String error = null;
        if (!fileUpload.getOriginalFilename().equals("")) {
            int rowsAdded = fileService.storeFile(authentication.getName(), fileUpload);
            if (rowsAdded < 0) {
                error = "No new File added";
            }
        }
        else{
            error = "empty filenames are illegal";
        }

        if (error == null) {

        } else {

        }
        model.addAttribute("filemodel", fileService.getFiles(authentication.getName()));
        return "home";
    }

    @GetMapping("/file/{filename}/view")
    public void getFileView(Authentication authentication, Model model, @PathVariable("filename") String filename,
                              HttpServletResponse response) throws IOException {
        FileModel fileModel = fileService.getFile(authentication.getName(), filename);
        File tempFile = new File("src/main/resources/temp/"+filename);
        tempFile.createNewFile();
        new FileOutputStream(tempFile).write(fileModel.getFiledata());
        response.addHeader("Content-Disposition", "attachment; filename="+filename);
        response.setContentType(fileModel.getContenttype());
        Files.copy(tempFile.getAbsoluteFile().toPath(), response.getOutputStream());
        tempFile.delete();
    }

}
