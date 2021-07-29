package com.udacity.jwdnd.course1.cloudstorage.model;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class CredentialModel extends AbstractModel{
    private static Gson gson = new Gson();
    private String url;
    private String username;
    private String key;
    private String password;
    
    public CredentialModel(Integer id, String url, String username, String key, String password, Integer userid) {
        this.id = id;
        this.url = url;
        this.username = username;
        this.key = key;
        this.password = password;
        this.userid = userid;
    }

    public CredentialModel(String url, String username, String key, String password, Integer userid) {
        this.url = url;
        this.username = username;
        this.key = key;
        this.password = password;
        this.userid = userid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPassword() {
        return password;
    }

    public String getEncryptedPassword() {
        return password;
    }

    public String getTruePassword(){
        return EncryptionService.decryptValue(password,key);
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
