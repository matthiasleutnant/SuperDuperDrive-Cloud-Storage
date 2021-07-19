package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.page.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.page.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.page.SignupPage;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;
	private String url;

	private WebDriver driver;
	@Autowired
	private UserService userService;

	private String firstname = "Matthias";
	private String lastname = "Leutnant";
	private String username = "Matze";
	private String password = "superSecurePassword123";

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.firefoxdriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new FirefoxDriver();
		url = "http://localhost:" + this.port;
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
	void testSignup(){
		driver.get(url+"/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(firstname,lastname,username,password);
		User createdUser = userService.getUser(username);

		assertThat(createdUser).isNotNull();
		assertThat(createdUser.getFirstName()).isEqualTo(firstname);
		assertThat(createdUser.getLastName()).isEqualTo(lastname);
		assertThat(createdUser.getUsername()).isEqualTo(username);
		assertThat(createdUser.getPassword()).isNotEqualTo(password);
	}

	@Test
	void invalidLogin(){
		driver.get(url+"/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username,password);
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	void directHomeAccess(){
		driver.get(url+"/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	void testLogin(){
		driver.get(url+"/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(firstname,lastname,username,password);
		User createdUser = userService.getUser(username);

		assertThat(createdUser).isNotNull();
		assertThat(createdUser.getFirstName()).isEqualTo(firstname);
		assertThat(createdUser.getLastName()).isEqualTo(lastname);
		assertThat(createdUser.getUsername()).isEqualTo(username);
		assertThat(createdUser.getPassword()).isNotEqualTo(password);

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username,password);
		Assertions.assertEquals("Home", driver.getTitle());
	}

	@Test
	void testlogout(){
		driver.get(url+"/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(firstname,lastname,username,password);
		User createdUser = userService.getUser(username);

		assertThat(createdUser).isNotNull();
		assertThat(createdUser.getFirstName()).isEqualTo(firstname);
		assertThat(createdUser.getLastName()).isEqualTo(lastname);
		assertThat(createdUser.getUsername()).isEqualTo(username);
		assertThat(createdUser.getPassword()).isNotEqualTo(password);

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username,password);
		Assertions.assertEquals("Home", driver.getTitle());

		HomePage homePage = new HomePage(driver);
		homePage.logout();

		Assertions.assertEquals("Login", driver.getTitle());
	}
}
