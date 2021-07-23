package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {

    private final UserService userService;
    private final CredentialMapper credentialMapper;

    public CredentialService(UserService userService, CredentialMapper credentialMapper) {
        this.userService = userService;
        this.credentialMapper = credentialMapper;
    }

    public int storeCredential(String username, CredentialForm credentialForm) {
        NoteModel noteModel;
        User user = userService.getUser(username);
        CredentialModel credential = new CredentialModel(credentialForm.getUrl(),credentialForm.getUsername(),"key",
                credentialForm.getPassword(),user.getUserId());
        return credentialMapper.insert(credential);
    }

    public List<CredentialModel> getCredentials(String username) {
        User user = userService.getUser(username);
        return credentialMapper.getCredentialByUserId(user.getUserId());
    }

    public void editCredential(NoteForm noteForm) {

    }

    public void deleteCredential(String username, int credentialid) {
        User user = userService.getUser(username);
        credentialMapper.deleteCredentialByCredentialId(credentialid, user.getUserId());
    }
}
