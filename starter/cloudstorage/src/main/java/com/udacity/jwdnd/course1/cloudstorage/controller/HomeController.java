package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    @PostMapping("/upload")
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

}
