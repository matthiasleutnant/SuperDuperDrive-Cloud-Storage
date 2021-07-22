package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;

public class HomePage {

    @FindBy(id = "logoutButton")
    WebElement logoutButton;

    @FindBy(id = "fileUpload")
    WebElement fileUpload;

    @FindBy(id = "uploadButton")
    WebElement uploadButton;

    @FindBy(id = "nav-notes-tab")
    WebElement notesTab;

    @FindBy(id = "newNoteButton")
    WebElement newNoteButton;

    @FindBy(id = "editNoteTitle")
    WebElement editNoteTitle;

    @FindBy(id = "editNoteDescription")
    WebElement editNoteDescription;
    WebDriver driver;

    public HomePage(WebDriver webDriver) {

        driver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void logout() {
        logoutButton.click();
    }

    public void upload(File file) {
        fileUpload.sendKeys(file.getAbsolutePath());
        uploadButton.click();
    }

    public void changeToNoteTab(){
        notesTab.click();
    }

    public void createNote(String title, String description) {
        changeToNoteTab();
        newNoteButton.click();
        editNoteTitle.sendKeys(title);
        editNoteDescription.sendKeys(description);

        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement noteSubmit = wait.until(webDriver -> webDriver.findElement(By.id("saveNoteChanges")));
        noteSubmit.click();
    }
}
