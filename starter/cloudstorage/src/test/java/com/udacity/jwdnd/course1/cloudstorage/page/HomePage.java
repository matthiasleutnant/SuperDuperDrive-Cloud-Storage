package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.File;

public class HomePage {

    @FindBy(id="logoutButton")
    WebElement logoutButton;

    @FindBy(id="fileUpload")
    WebElement fileUpload;

    @FindBy(id="uploadButton")
    WebElement uploadButton;

    public HomePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void logout(){
        logoutButton.click();
    }

    public void upload(File file){
        fileUpload.sendKeys(file.getAbsolutePath());
        uploadButton.click();
    }
}
