package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialModel;
import com.udacity.jwdnd.course1.cloudstorage.model.FileModel;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteModel;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.page.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.page.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.page.SignupPage;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

    @LocalServerPort
    private int port;
    private String url;
    private String downloadPath = "src/test/resources/downloads";

    private WebDriver driver;
    @Autowired
    private UserService userService;
    @Autowired
    FileMapper fileMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    NoteMapper noteMapper;
    @Autowired
    CredentialMapper credentialMapper;

    private String firstname = "Matthias";
    private String lastname = "Leutnant";
    private String username = "Matze";
    private String password = "superSecurePassword123";
    private File downloadPackage;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.firefoxdriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        downloadPackage = new File(downloadPath);
        FirefoxProfile fp = new FirefoxProfile();
        fp.setPreference("browser.download.folderList", 2);
        fp.setPreference("browser.download.manager.showWhenStarting", false);
        fp.setPreference("browser.download.dir", downloadPackage.getAbsolutePath());
        fp.setPreference("browser.helperApps.neverAsk.saveToDisk", "text/plain");
        FirefoxOptions fo = new FirefoxOptions();
        fo.setProfile(fp);
        this.driver = new FirefoxDriver(fo);

        url = "http://localhost:" + this.port;
        cleanUp();
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    public void getLoginPage() {
        driver.get(url + "/login");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    void testSignup() {
        User createdUser = createUser();

        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getFirstName()).isEqualTo(firstname);
        assertThat(createdUser.getLastName()).isEqualTo(lastname);
        assertThat(createdUser.getUsername()).isEqualTo(username);
        assertThat(createdUser.getPassword()).isNotEqualTo(password);
    }

    private User createUser() {
        driver.get(url + "/signup");
        SignupPage signupPage = new SignupPage(driver);
        signupPage.signup(firstname, lastname, username, password);
        User createdUser = userService.getUser(username);
        return createdUser;
    }

    @Test
    void invalidLogin() {
        driver.get(url + "/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);
        assertThat(driver.getTitle()).isEqualTo("Login");
    }

    @Test
    void directHomeAccess() {
        driver.get(url + "/home");
        assertThat(driver.getTitle()).isEqualTo("Login");
    }

    @Test
    void testLogin() {
        loginUser();
        assertThat(driver.getTitle()).isEqualTo("Home");
    }

    private void loginUser() {
        createUser();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);
    }

    @Test
    void testLogout() {
        loginUser();

        HomePage homePage = new HomePage(driver);
        homePage.logout();

        assertThat(driver.getTitle()).isEqualTo("Login");
    }

    @Test
    void testUpload() {
        String filename = "test.txt";
        uploadFile("test.txt");

        FileModel fileModel = fileMapper.getFileByFileNameAndUserId(userService.getUser(username).getUserId(), "test.txt");
        assertThat(fileModel.getFilename()).isEqualTo("test.txt");
        WebElement webElement = driver.findElement(By.name("filename_" + "test.txt"));
        assertThat(webElement.getText()).isEqualTo("test.txt");
    }

    @Test
    void testCreateNote() {
        String noteTitle = "testNote";
        String noteDescription = "This is an awesome note!!!";
        HomePage homePage = createNote(noteTitle, noteDescription);

        homePage.changeToNoteTab();
        WebElement title = driver.findElement(By.id("id_note_title" + noteTitle));
        assertThat(title.getText()).isEqualTo(noteTitle);
        WebElement description = driver.findElement(By.id("id_note_description" + noteTitle));
        assertThat(description.getText()).isEqualTo(noteDescription);

    }

    private HomePage createNote(String noteTitle, String noteDescription) {
        loginUser();
        HomePage homePage = new HomePage(driver);
        homePage.createNote(noteTitle, noteDescription);
        return homePage;
    }

    @Test
    void testEditNote() {
        String noteTitle = "testNote";
        String noteDescription = "This is an awesome note!!!";
        String newTitle = "The same old note";
        String newDescription = "with an other content";
        HomePage homePage = createNote(noteTitle, noteDescription);
        homePage.editNote(noteTitle, newTitle, newDescription);


        homePage.changeToNoteTab();
        WebElement title = driver.findElement(By.id("id_note_title" + newTitle));
        assertThat(title.getText()).isEqualTo(newTitle);
        WebElement description = driver.findElement(By.id("id_note_description" + newTitle));
        assertThat(description.getText()).isEqualTo(newDescription);
    }

    @Test
    void testDeleteNote() {
        String noteTitle = "testNote";
        String noteDescription = "This is an awesome note!!!";
        HomePage homePage = createNote(noteTitle, noteDescription);
        homePage.deleteNote(noteTitle);

        homePage.changeToNoteTab();
        assertThatThrownBy(() -> {
            driver.findElement(By.id("id_note_title" + noteTitle));
        }).isInstanceOf(NoSuchElementException.class);
        assertThatThrownBy(() -> {
            driver.findElement(By.id("id_note_description" + noteTitle));
        }).isInstanceOf(NoSuchElementException.class);
        List<NoteModel> notes = noteMapper.getNoteByUserId(userService.getUser(username).getUserId());
        assertThat(notes.isEmpty()).isTrue();
    }

    private HomePage uploadFile(String filename) {
        String path = "src/test/resources/" + filename;
        loginUser();
        HomePage homePage = new HomePage(driver);
        File file = new File(path);
        homePage.upload(file);
        return homePage;
    }

    @Test
    void testDownload() throws InterruptedException {
        String filename = "test.txt";
        File downloadedFile = downloadFile(filename);
        assertThat(downloadedFile.isFile()).isTrue();
    }

    @Test
    void deleteFile() {
        String filename = "test.txt";
        HomePage homePage = uploadFile(filename);
        homePage.deleteFile(filename);

        List<FileModel> files = fileMapper.getFileByUserId(userService.getUser(username).getUserId());
        assertThat(files.isEmpty()).isTrue();
        assertThatThrownBy(() -> {
            driver.findElement(By.name("filename_" + filename));
        }).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void testCredentialCreation() {
        String url = "192.168.0.1";
        String user = "user";
        String password = "Geheimespassword";
        HomePage homePage = createCredential(url, user, password);

        List<CredentialModel> creadential = credentialMapper.getCredentialByUserId(userService.getUser(username).getUserId());
        assertThat(creadential.isEmpty()).isFalse();
        assertThat(creadential.size()).isEqualTo(1);
        assertThat(creadential.get(0).getUrl()).isEqualTo(url);
        assertThat(creadential.get(0).getUsername()).isEqualTo(user);
        assertThat(creadential.get(0).getPassword()).isNotEqualTo(password);
        assertThat(creadential.get(0).getTruePassword()).isEqualTo(password);
        assertThat(homePage.getUnencryptedPassword(url)).isEqualTo(password);
        assertThat(creadential.get(0).getUserid()).isEqualTo(userService.getUser(username).getUserId());
        WebElement urltext = driver.findElement(By.name("credential_url_" + url));
        assertThat(urltext.getText()).isEqualTo(url);
        WebElement usertext = driver.findElement(By.name("credential_user_" + url));
        assertThat(usertext.getText()).isEqualTo(user);
        WebElement passwordtext = driver.findElement(By.name("credential_password_" + url));
        assertThat(passwordtext.getText()).isNotEqualTo(password);
    }

    @Test
    void testCredentialEditing() {
        String url = "192.168.0.1";
        String user = "user";
        String password = "Geheimespasswort";
        HomePage homePage = createCredential(url, user, password);
        String newUrl = url+":8080";
        String newUser = "user2";
        String newpw = "superGeheimespasswort123";
        homePage.editCredential(url,newUrl,newUser,newpw);

        List<CredentialModel> creadential = credentialMapper.getCredentialByUserId(userService.getUser(username).getUserId());
        assertThat(creadential.isEmpty()).isFalse();
        assertThat(creadential.size()).isEqualTo(1);
        assertThat(creadential.get(0).getUrl()).isEqualTo(newUrl);
        assertThat(creadential.get(0).getUsername()).isEqualTo(newUser);
        assertThat(creadential.get(0).getPassword()).isNotEqualTo(newpw);
        assertThat(creadential.get(0).getTruePassword()).isEqualTo(newpw);
        assertThat(homePage.getUnencryptedPassword(newUrl)).isEqualTo(newpw);
        assertThat(creadential.get(0).getUserid()).isEqualTo(userService.getUser(username).getUserId());
        WebElement urltext = driver.findElement(By.name("credential_url_" + newUrl));
        assertThat(urltext.getText()).isEqualTo(newUrl);
        WebElement usertext = driver.findElement(By.name("credential_user_" + newUrl));
        assertThat(usertext.getText()).isEqualTo(newUser);
        WebElement passwordtext = driver.findElement(By.name("credential_password_" + newUrl));
        assertThat(passwordtext.getText()).isNotEqualTo(newpw);
    }

    @Test
    void testCredentialDeletion() {
        String url = "192.168.0.1";
        String user = "user";
        String password = "Geheimespassword";
        HomePage homePage = createCredential(url, user, password);
        homePage.deleteCredential(url);

        List<CredentialModel> creadential = credentialMapper.getCredentialByUserId(userService.getUser(username).getUserId());
        assertThat(creadential.isEmpty()).isTrue();
        assertThatThrownBy(() -> {
            driver.findElement(By.id("credential_url_" + url));
        }).isInstanceOf(NoSuchElementException.class);
        assertThatThrownBy(() -> {
            driver.findElement(By.id("credential_user_" + url));
        }).isInstanceOf(NoSuchElementException.class);
        assertThatThrownBy(() -> {
            driver.findElement(By.id("credential_password_" + url));
        }).isInstanceOf(NoSuchElementException.class);
    }


    private HomePage createCredential(String url, String username, String password) {
        loginUser();
        HomePage homePage = new HomePage(driver);
        homePage.createCredential(url, username, password);
        return homePage;
    }

    private File downloadFile(String filename) throws InterruptedException {
        uploadFile(filename);
        WebElement webElement = driver.findElement(By.id("viewButton_" + filename));
        webElement.click();
        File downloadedFile = new File(downloadPackage.getAbsolutePath() + "/" + filename);
        Thread.sleep(500);
        return downloadedFile;
    }


    void cleanUp() {
        credentialMapper.deleteALL();
        noteMapper.deleteALL();
        fileMapper.deleteALL();
        userMapper.deleteALL();
        for (File f : downloadPackage.listFiles()) {
            f.delete();
        }
    }
}
