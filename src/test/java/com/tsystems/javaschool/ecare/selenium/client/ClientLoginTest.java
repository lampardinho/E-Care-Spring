package com.tsystems.javaschool.ecare.selenium.client;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ClientLoginTest
{
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception
    {
        driver = new FirefoxDriver();
        baseUrl = "http://localhost:8085/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testLogin() throws Exception
    {
        driver.get(baseUrl + "/login.jsp");
        /*try
        {
            assertEquals("Please sign in", driver.findElement(By.cssSelector("h2.form-signin-heading")).getText());
        } catch (Error e)
        {
            verificationErrors.append(e.toString());
        }*/
        driver.findElement(By.name("email")).clear();
        driver.findElement(By.name("email")).sendKeys("mashasyrkina@mail.ru");
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("qwerty");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        /*try
        {
            assertEquals("mashasyrkina@mail.ru", driver.findElement(By.cssSelector("#user")).getText());
        } catch (Error e)
        {
            verificationErrors.append(e.toString());
        }*/
    }

    @After
    public void tearDown() throws Exception
    {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString))
        {
            fail(verificationErrorString);
        }
    }

    private boolean isElementPresent(By by)
    {
        try
        {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e)
        {
            return false;
        }
    }

    private boolean isAlertPresent()
    {
        try
        {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e)
        {
            return false;
        }
    }

    private String closeAlertAndGetItsText()
    {
        try
        {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert)
            {
                alert.accept();
            } else
            {
                alert.dismiss();
            }
            return alertText;
        } finally
        {
            acceptNextAlert = true;
        }
    }
}
