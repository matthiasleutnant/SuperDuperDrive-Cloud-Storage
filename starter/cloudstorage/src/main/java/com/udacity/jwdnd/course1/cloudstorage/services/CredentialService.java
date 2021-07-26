package com.udacity.jwdnd.course1.cloudstorage.services;

import com.google.gson.Gson;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.*;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.*;

@Service
public class CredentialService {

    private final UserService userService;
    private final CredentialMapper credentialMapper;
    private static final Gson gson = new Gson();

    public CredentialService(UserService userService, CredentialMapper credentialMapper) {
        this.userService = userService;
        this.credentialMapper = credentialMapper;
    }

    public int storeCredential(String username, CredentialForm credentialForm) throws Exception {
        NoteModel noteModel;
        User user = userService.getUser(username);
        CredentialModel credential = new CredentialModel(credentialForm.getUrl(),credentialForm.getUsername(),"",
                credentialForm.getPassword(),user.getUserId());
        encryptPassword(credential);
        return credentialMapper.insert(credential);
    }

    public List<CredentialModel> getCredentials(String username) {
        User user = userService.getUser(username);
        return credentialMapper.getCredentialByUserId(user.getUserId());
    }

    public int editCredential(String username,CredentialForm credentialForm) throws Exception {
        CredentialModel credential = credentialMapper.getCredentialByCredentialId(credentialForm.getCredentialid());
        User user = userService.getUser(username);
        if(user.getUserId()==credential.getUserid()){
            credential.setUrl(credentialForm.getUrl());
            credential.setUsername(credentialForm.getUsername());
            credential.setPassword(credentialForm.getPassword());
            credential = encryptPassword(credential);
            return credentialMapper.updateCredential(credential);
        }
        return -1;
    }

    public int deleteCredential(String username, int credentialid) {
        User user = userService.getUser(username);
        return credentialMapper.deleteCredentialByCredentialId(credentialid, user.getUserId());
    }

    private CredentialModel encryptPassword(CredentialModel credentialModel) throws Exception {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String key;
        if(credentialModel.getKey()=="") {
            key = Base64.getEncoder().encodeToString(salt);
        }
        else{
            key = credentialModel.getKey();
        }
        Map<String, String> encodedPass = AesCtrArgon2HmacExample.aes256CtrArgon2HMacEncrypt(credentialModel.getPassword(), key);
        credentialModel.setPassword(gson.toJson(encodedPass));
        credentialModel.setEncryptedPassword(encodedPass.get("cipherText"));
        credentialModel.setKey(key);
        return credentialModel;
    }

    public static String decryptPassword(String encryptedMsg, String key){
        Map<String,String> map = gson.fromJson(encryptedMsg, Map.class);
        String result="";
        try {
            result = AesCtrArgon2HmacExample.aes256CtrArgon2HMacDecrypt(map,key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
