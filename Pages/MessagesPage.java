package vkReads.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MessagesPage {
    private WebDriver driver;
    public MessagesPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//li[contains(@class, 'nim-dialog_unread')]")
    public WebElement unreadedMessages;

    @FindBy(xpath = "//span[@class='nim-dialog--preview _dialog_body']")
    public WebElement previewDialogText;

    @FindBy(xpath = "//input[@id='im_dialogs_search']")
    public WebElement searchInput;
}
