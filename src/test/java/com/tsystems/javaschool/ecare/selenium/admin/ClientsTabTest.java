package com.tsystems.javaschool.ecare.selenium.admin;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;


public class ClientsTabTest {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://localhost:8085/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testClientsTab() throws Exception {
		driver.get(baseUrl + "/login.jsp");
		driver.findElement(By.id("inputEmail")).clear();
		driver.findElement(By.id("inputEmail")).sendKeys("1lampard@mail.ru");
		driver.findElement(By.id("inputPassword")).clear();
		driver.findElement(By.id("inputPassword")).sendKeys("qwerty");
		driver.findElement(By.xpath("//button")).click();
		driver.findElement(By.xpath("//div[2]/button")).click();
		driver.findElement(By.id("user_firstName")).clear();
		driver.findElement(By.id("user_firstName")).sendKeys("qwr");
		driver.findElement(By.id("user_lastName")).clear();
		driver.findElement(By.id("user_lastName")).sendKeys("wqerr");
		driver.findElement(By.id("user_birthDate")).clear();
		driver.findElement(By.id("user_birthDate")).sendKeys("June 1, 1900");
		driver.findElement(By.id("user_passportData")).clear();
		driver.findElement(By.id("user_passportData")).sendKeys("dfbg");
		driver.findElement(By.id("user_address")).clear();
		driver.findElement(By.id("user_address")).sendKeys("dfgnhf");
		driver.findElement(By.id("user_email")).clear();
		driver.findElement(By.id("user_email")).sendKeys("dsbgfd@mail.ru");
		driver.findElement(By.id("user_password")).clear();
		driver.findElement(By.id("user_password")).sendKeys("qwerty");
		driver.findElement(By.id("user_password2")).clear();
		driver.findElement(By.id("user_password2")).sendKeys("qwerty");
		driver.findElement(By.id("user_isAdmin")).click();
		driver.findElement(By.id("createUser")).click();
		/*try {
			assertEquals("dfbg", driver.findElement(By.xpath("//tr[4]/td[4]")).getText());
		} catch (Error e) {
			verificationErrors.append(e.toString());
		}*/
		driver.findElement(By.xpath("//tr[4]/td[8]/button")).click();
		/*try {
			assertEquals("Unlock", driver.findElement(By.xpath("//tr[4]/td[8]/button")).getText());
		} catch (Error e) {
			verificationErrors.append(e.toString());
		}*/
		//driver.findElement(By.xpath("//tr[4]/td[8]/button")).click();
		//driver.findElement(By.id("logout")).click();
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
