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

    @FindBy(id = "nav-credentials-tab")
    WebElement credentialsTab;

    @FindBy(id = "newNoteButton")
    WebElement newNoteButton;

    @FindBy(id = "editNoteTitle")
    WebElement editNoteTitle;

    @FindBy(id = "editNoteDescription")
    WebElement editNoteDescription;

    @FindBy(id = "newCredentialButton")
    WebElement newCredentialButton;

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

    public void deleteFile(String filename){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement fileDelete = wait.until(webDriver -> webDriver.findElement(By.name("delete_file_button_"+filename)));
        fileDelete.click();
    }

    public void changeToNoteTab(){
        notesTab.click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement noteSubmit = wait.until(webDriver -> webDriver.findElement(By.id("noteTitleLabel")));
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

    public void editNote(String notetitle, CharSequence newTitle, CharSequence newDescription){
        changeToNoteTab();
        WebElement editButton = driver.findElement(By.name("editNoteButton_"+notetitle));
        editButton.click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement noteSubmit = wait.until(webDriver -> webDriver.findElement(By.id("saveNoteChanges")));
        editNoteTitle.clear();
        editNoteTitle.sendKeys(newTitle);
        editNoteDescription.clear();
        editNoteDescription.sendKeys(newDescription);
        noteSubmit.click();
    }

    public void deleteNote(String notetitle){
        changeToNoteTab();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement deleteButton = wait.until(webDriver -> webDriver.findElement(By.id("delete_note_button_"+notetitle)));
        deleteButton.click();
    }


    public void changeToCredentialsTab(){
        credentialsTab.click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(webDriver -> webDriver.findElement(By.id("newCredentialButton")));
    }

    public void createCredential(String url, String username, String password){
        changeToCredentialsTab();
        newCredentialButton.click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement urlText = wait.until(webDriver -> webDriver.findElement(By.id("credential-url")));
        WebElement userText = wait.until(webDriver -> webDriver.findElement(By.id("credential-username")));
        WebElement passText = wait.until(webDriver -> webDriver.findElement(By.id("credential-password")));

        urlText.sendKeys(url);
        passText.sendKeys(password);
        userText.sendKeys(username);
        WebElement credentialSubmit = wait.until(webDriver -> webDriver.findElement(By.id("credentialSubmitButton")));
        credentialSubmit.click();
    }

    public void editCredential(String oldUrl,String url, String username, String password){
        changeToCredentialsTab();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement editButton = wait.until(webDriver -> webDriver.findElement(By.name("editCredentialButton_"+oldUrl)));
        editButton.click();
        WebElement urlText = wait.until(webDriver -> webDriver.findElement(By.id("credential-url")));
        WebElement userText = wait.until(webDriver -> webDriver.findElement(By.id("credential-username")));
        WebElement passText = wait.until(webDriver -> webDriver.findElement(By.id("credential-password")));
        urlText.clear();
        passText.clear();
        userText.clear();
        urlText.sendKeys(url);
        passText.sendKeys(password);
        userText.sendKeys(username);
        WebElement credentialSubmit = wait.until(webDriver -> webDriver.findElement(By.id("credentialSubmitButton")));
        credentialSubmit.click();

    }

    public void deleteCredential(String url){
        changeToCredentialsTab();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement urlDelete = wait.until(webDriver -> webDriver.findElement(By.name("credential_delete_"+url)));
        urlDelete.click();
    }
}
