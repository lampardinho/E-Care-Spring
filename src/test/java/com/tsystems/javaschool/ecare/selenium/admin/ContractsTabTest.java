package com.tsystems.javaschool.ecare.selenium.admin;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;


public class ContractsTabTest {
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
  public void testContractsTab() throws Exception {
		driver.get(baseUrl + "/login.jsp");
		driver.findElement(By.id("inputEmail")).clear();
		driver.findElement(By.id("inputEmail")).sendKeys("1lampard@mail.ru");
		driver.findElement(By.id("inputPassword")).clear();
		driver.findElement(By.id("inputPassword")).sendKeys("qwerty");
		driver.findElement(By.xpath("//button")).click();
		driver.findElement(By.id("contracts_tab")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Clients')]")).click();
		driver.findElement(By.id("searchPhoneNumber")).clear();
		driver.findElement(By.id("searchPhoneNumber")).sendKeys("3966788");
		driver.findElement(By.id("searchUser")).click();
		/*try {
			assertEquals("1lampard@mail.ru", driver.findElement(By.id("foundUserEmail")).getText());
		} catch (Error e) {
			verificationErrors.append(e.toString());
		}*/
		driver.findElement(By.xpath("//button[2]")).click();
		driver.findElement(By.id("contracts_tab")).click();
		driver.findElement(By.xpath("//div[2]/div/div[2]/button")).click();
		new Select(driver.findElement(By.id("owner"))).selectByVisibleText("Sergey Vasiliev");
		driver.findElement(By.id("phoneNumber")).clear();
		driver.findElement(By.id("phoneNumber")).sendKeys("4575476");
		driver.findElement(By.id("balance")).clear();
		driver.findElement(By.id("balance")).sendKeys("566");
		new Select(driver.findElement(By.id("tariff"))).selectByVisibleText("Smart mini");
		driver.findElement(By.id("createContract")).click();
		driver.findElement(By.xpath("//tr[7]/td[6]/button[2]")).click();
		new Select(driver.findElement(By.id("avail_tariffs"))).selectByVisibleText("Endless talk");
		driver.findElement(By.id("saveChangeTariff")).click();
		driver.findElement(By.id("logout")).click();
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
