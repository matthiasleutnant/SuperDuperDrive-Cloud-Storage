package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    public String getHomepage(Authentication authentication, Model model) {
        return "home";
    }

    @PostMapping
    public String postHomepage(Authentication authentication, Model model) {
        return "home";
    }

    @PostMapping("/upload")
    public String postUpload(Authentication authentication, @RequestParam("fileUpload")MultipartFile fileUpload, Model model) throws IOException {
        int number = fileService.createFile(authentication.getName(),fileUpload);
        return "home";
    }

}
