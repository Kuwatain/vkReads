package vkReads;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import vkReads.Pages.AuthPage;
import vkReads.Pages.BasePage;
import vkReads.Pages.DialogPage;
import vkReads.Pages.MessagesPage;
import com.google.gson.*;
import java.io.*;


import java.util.concurrent.TimeUnit;

class TestBase {
    WebDriver driver;
    WebDriverWait wait;

    Data data = new Data();

    private AuthPage authPage;
    MessagesPage messagesPage;
    DialogPage dialogPage;
    BasePage basePage;
    User user = getUser();

    int quest = 0;
    int img = 0;
    boolean warningChecked = false;






    private User getUser() {
        Gson gson = new Gson();
        Reader reader = null;
        try {
            reader = new FileReader("src/test/java/vkReads/User.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert reader != null;
        return  gson.fromJson(reader,User.class);
    }
    void initPages() {
        authPage = new AuthPage(driver);
        messagesPage = new MessagesPage(driver);
        dialogPage = new DialogPage(driver);
        basePage = new BasePage(driver);
    }
    void initDrivers(String browser) {
        switch (browser) {
            case "Chrome":
                driver = new ChromeDriver();
                break;
            case "Firefox":
                driver = new FirefoxDriver();
                break;
            default: driver = new ChromeDriver();
        }
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);
    }
    void authorization(String login, String password) {
        goTo(data.vkNews, true);
        sendTextTo(authPage.LogInput,login, true);
        sendTextTo(authPage.PassInput,password, true);
        click(authPage.Submit, true);
    }
    void goTo(String url){
        driver.get(url);
        if(warningChecked == false) {
            if (isElementPresent(basePage.safeWarning)) {
                click(basePage.closeWarningButton);
                warningChecked = true;
            }
        }
    }
    void goTo(String url, boolean warningChecked){
        driver.get(url);
        if(warningChecked == false) {
            if (isElementPresent(basePage.safeWarning)) {
                click(basePage.closeWarningButton);
                warningChecked = true;
            }
        }
    }

    void click(WebElement element){
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        if(!warningChecked) {
            if (isElementPresent(basePage.safeWarning)) {
                click(basePage.closeWarningButton);
                warningChecked = true;
            }
        }
    }
    private void click(WebElement element, boolean warningChecked){
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        if(!warningChecked) {
            if (isElementPresent(basePage.safeWarning)) {
                click(basePage.closeWarningButton);
                warningChecked = true;
            }
        }
    }

    void sendTextTo(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.sendKeys(text);
        if(warningChecked == false) {
            if (isElementPresent(basePage.safeWarning)) {
                click(basePage.closeWarningButton);
                warningChecked = true;
            }
        }
    }
    void sendTextTo(WebElement element, String text, boolean warningChecked) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.sendKeys(text);
        if(warningChecked == false) {
            if (isElementPresent(basePage.safeWarning)) {
                click(basePage.closeWarningButton);
                warningChecked = true;
            }
        }
    }
    boolean isElementPresent(WebElement element){
        try{
            element.isDisplayed();
            return true;
        }
        catch(NoSuchElementException ex){
            return false;
        }
    }
}
