package com.udacity.jwdnd.course1.cloudstorage.model;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.udacity.jwdnd.course1.cloudstorage.services.AesCtrArgon2HmacExample;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;

import java.util.Map;

public class CredentialModel {
    private static Gson gson = new Gson();
    private Integer credentialid;
    private String url;
    private String username;
    private String key;
    private String password;
    private Integer userid;
    private String encryptedPassword;

    public CredentialModel(Integer credentialid, String url, String username, String key, String password, Integer userid) {
        this.credentialid = credentialid;
        this.url = url;
        this.username = username;
        this.key = key;
        this.password = password;
        this.userid = userid;

        try {
            Map<String, String> map = gson.fromJson(password, Map.class);
            setEncryptedPassword(map.get("cipherText"));
        }
        catch (JsonSyntaxException e){

        }
    }

    public CredentialModel(String url, String username, String key, String password, Integer userid) {
        this.url = url;
        this.username = username;
        this.key = key;
        this.password = password;
        this.userid = userid;
    }

    public Integer getCredentialid() {
        return credentialid;
    }

    public void setCredentialid(Integer credentialid) {
        this.credentialid = credentialid;
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

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public String getTruePassword(){
        return CredentialService.decryptPassword(getPassword(),key);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}
