package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.*;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.*;

@Service
public class CredentialService extends AbstractService<CredentialModel> {

    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper mapper, UserService userService, EncryptionService encryptionService) {
        super(mapper, userService);
        this.encryptionService = encryptionService;
    }


    public int store(String username, CredentialForm credentialForm) throws Exception {
        User user = userService.getUser(username);
        credentialForm.setUserid(user.getUserId());
        return mapper.insert(encryptPassword(credentialForm));
    }

    public int edit(String username,CredentialForm credentialForm) throws Exception {
        User user = userService.getUser(username);
        credentialForm.setUserid(user.getUserId());
            return mapper.update(encryptPassword(credentialForm));
    }

    private CredentialForm encryptPassword(CredentialForm credentialForm) throws Exception {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String key = Base64.getEncoder().encodeToString(salt);
        credentialForm.setPassword(encryptionService.encryptValue(credentialForm.getPassword(),key));
        credentialForm.setKey(key);
        return credentialForm;
    }

    public int delete(String username, int id) {
        User user = userService.getUser(username);
        return mapper.deleteByUserIdAndId(user.getUserId(), id);
    }
}
