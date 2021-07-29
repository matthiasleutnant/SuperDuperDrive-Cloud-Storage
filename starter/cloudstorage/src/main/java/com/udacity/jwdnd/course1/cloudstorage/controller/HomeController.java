package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.FileModel;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final FileService fileService;
    private final NoteService noteService;
    private final CredentialService credentialService;
    @Autowired
    private Logger logger;

    private String error = "";
    private String success = "";

    @RequestMapping(value = "*")
    public String defaultMapping() {
        return "redirect:/home";
    }


    public HomeController(FileService fileService, NoteService noteService, CredentialService credentialService) {
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
    }

    @GetMapping
    public String getHomepage(Authentication authentication, Model model, NoteForm noteForm,
                              CredentialForm credentialForm) {
        model.addAttribute("filemodel", fileService.getFiles(authentication.getName()));
        model.addAttribute("notes", noteService.getNotes(authentication.getName()));
        model.addAttribute("credentials", credentialService.getCredentials(authentication.getName()));
        model.addAttribute("success", success != "");
        model.addAttribute("successtext", success);
        model.addAttribute("error", error != "");
        model.addAttribute("errortext", error);
        error = "";
        success = "";
        return "home";
    }

    @PostMapping
    public String postUpload(Authentication authentication, @RequestParam("fileUpload") MultipartFile fileUpload,
                             Model model) throws IOException {
        if (fileUpload.getOriginalFilename().equals("")) {
            error = "Empty filenames are illegal.";
        }
        if (fileService.getFile(authentication.getName(), fileUpload.getOriginalFilename()) != null) {
            error = "You have already uploaded a file with the same name.";
        }

        if (error == "") {
            int rowsAdded = fileService.storeFile(authentication.getName(), fileUpload);
            if (rowsAdded < 0) {
                error = "No new File added";
            } else {
                success = "You uploaded the file " + fileUpload.getOriginalFilename() + " successfully";
            }

        }
        model.addAttribute("filemodel", fileService.getFiles(authentication.getName()));
        return "redirect:/home";
    }

    /**
     * I'm catching the "DataIntegrityViolationException" error just for practice purposes. It should actually be
     * prevented by limitations in the html file, but it seems that line breaks in the html recognition are recognized
     * as a single punctuation mark, but Java needs two punctuation marks to save them
     *
     * @param authentication
     * @param noteForm
     * @return
     */
    @PostMapping("/note")
    public String postNote(Authentication authentication, NoteForm noteForm) {
        try {
            int test = noteForm.getDescription().length();
            if (noteForm.getId() == null) {
                if (noteService.store(authentication.getName(), noteForm) > 0) {
                    success = "You added the note " + noteForm.getTitle() + " successfully";
                } else {
                    error = "No new Node added";
                }
            } else {
                if (noteService.editNote(noteForm) > 0) {
                    success = "You edited the note " + noteForm.getTitle() + " successfully";
                } else {
                    error = "Note was not edited";
                }
            }
        } catch (DataIntegrityViolationException e) {
            error = "The Note could not be edited/created, due to too long title/description";
        } catch (Exception e) {
            logger.error("An unexpected Error occurred", e);
            error = "An unexpected Error occurred";
        }
        return "redirect:/home";
    }

    @PostMapping("/notes/{noteid}/delete")
    public String postNoteDelete(Authentication authentication, @PathVariable("noteid") int noteid) {
        if (noteService.deleteNote(authentication.getName(), noteid) > 0) {
            success = "You deleted the note successfully";
        } else {
            error = "Note was not deleted";
        }
        return "redirect:/home";
    }

    //I tested the download process with chrome,firefox, edge and internet explorer. It always worked.
    // The only time I had an error is when there was a space in the filename. I don't know how to enable the escape of it,
    // but I guess that was the source of the download error
    @GetMapping("/file/{filename}/view")
    public void getFileView(Authentication authentication, @PathVariable("filename") String filename,
                            HttpServletResponse response) throws IOException {
        FileModel fileModel = fileService.getFile(authentication.getName(), filename);
        File tempFile = new File("src/main/resources/temp/" + filename);
        tempFile.createNewFile();
        new FileOutputStream(tempFile).write(fileModel.getFiledata());
        response.addHeader("Content-Disposition", "attachment; filename=" + filename);
        if (fileModel.getContenttype() != null) {
            response.setContentType(fileModel.getContenttype());
        } else {
            response.setContentType("application/octet-stream");
        }

        Files.copy(tempFile.getAbsoluteFile().toPath(), response.getOutputStream());
        tempFile.delete();
    }

    @PostMapping("/credential")
    public String postCredential(Authentication authentication, CredentialForm credentialForm) throws Exception {
        try {
            if (credentialForm.getCredentialid() == null) {
                if (credentialService.storeCredential(authentication.getName(), credentialForm) > 0) {
                    success = "You created the credential for " + credentialForm.getUrl() + " successfully";
                } else {
                    error = "Credential was not created";
                }
            } else {
                if (credentialService.editCredential(authentication.getName(), credentialForm) > 0) {
                    success = "You edited the credential for " + credentialForm.getUrl() + " successfully";
                } else {
                    error = "Credential was not edited";
                }
            }
        } catch (Exception e) {
            logger.error(e.toString());
            error = "An unexpected Error occurred";
        }
        return "redirect:/home";
    }

    @PostMapping("/credentials/{credentialid}/delete")
    public String postCredentialDelete(Authentication authentication, @PathVariable("credentialid") int credentialid) {
        if (credentialService.deleteCredential(authentication.getName(), credentialid) > 0) {
            success = "You deleted the credentials successfully";
        } else {
            error = "Credentials was not deleted";
        }
        return "redirect:/home";
    }

    @PostMapping("/file/{fileid}/delete")
    public String postFileDelete(Authentication authentication, @PathVariable("fileid") int fileid) {
        if (fileService.deleteFile(authentication.getName(), fileid) > 0) {
            success = "You deleted the file successfully";
        } else {
            error = "File was not deleted";
        }
        return "redirect:/home";
    }
}
