package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.FileModel;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
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
    private final NoteService noteService;

    public HomeController(FileService fileService, NoteService noteService) {
        this.fileService = fileService;
        this.noteService = noteService;
    }

    @RequestMapping
    public String getHomepage(Authentication authentication, Model model, NoteForm noteForm) {
        model.addAttribute("filemodel", fileService.getFiles(authentication.getName()));
        model.addAttribute("notes",noteService.getNotes(authentication.getName()));
        return "home";
    }

    @PostMapping
    public String postUpload(Authentication authentication, @RequestParam("fileUpload") MultipartFile fileUpload, Model model) throws IOException {
        String error = null;
        if (fileUpload.getOriginalFilename().equals("")) {
            error = "Empty filenames are illegal.";
        }
        if(fileService.getFile(authentication.getName(),fileUpload.getOriginalFilename())!=null){
            error = "You have already uploaded a file with the same name.";
        }

        if (error == null) {
            int rowsAdded = fileService.storeFile(authentication.getName(), fileUpload);
            if (rowsAdded < 0) {
                error = "No new File added.";
                model.addAttribute("error",true);
                model.addAttribute("errortext",error);
            }

        } else {
            model.addAttribute("error",true);
            model.addAttribute("errortext",error);
        }
        model.addAttribute("filemodel", fileService.getFiles(authentication.getName()));
        return "redirect:/home";
    }

    @PostMapping("/note")
    public String postNote(Authentication authentication, NoteForm noteForm){
        if(noteForm.getId()==null) {
            int success = noteService.storeNote(authentication.getName(), noteForm);
        }
        else{
            noteService.editNote(noteForm);
        }
        return "redirect:/home";
    }

    @PostMapping("/notes/{notetitle}/delete")
    public String postDeleteNote(Authentication authentication, @PathVariable("notetitle") String notetitle){
        noteService.deleteNote(authentication.getName(),notetitle);
        return "redirect:/home";
    }

    @GetMapping("/file/{filename}/view")
    public void getFileView(Authentication authentication, @PathVariable("filename") String filename,
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
