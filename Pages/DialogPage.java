package vkReads.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class DialogPage {
    WebDriver driver;
    public DialogPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    @FindBy(xpath = "//span[@class='im-to-end__button']")
    public WebElement toEnd;

    @FindBy(xpath = "//div[@class='im-mess--text wall_module _im_log_body']")
    public List<WebElement> allMessages;

    @FindBy(xpath = "//div[@class='im_editable im-chat-input--text _im_text']")
    public WebElement messageInput;

    @FindBy(xpath = "//a[@class='im-page--title-main-inner _im_page_peer_name']")
    public WebElement name;

    public WebElement lastMessage(){
       return allMessages.get(allMessages.size()-1);
    }



}
