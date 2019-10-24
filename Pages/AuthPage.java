package vkReads.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AuthPage {
    private WebDriver driver;
    public AuthPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    @FindBy(xpath = "//input[@id='email']" )
    public WebElement LogInput;

    @FindBy(xpath = "//input[@id='pass']")
    public WebElement PassInput;

    @FindBy(xpath = "//button[@id='login_button']")
    public WebElement Submit;

}
