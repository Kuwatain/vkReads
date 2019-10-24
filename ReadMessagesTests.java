package vkReads;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ReadMessagesTests extends TestBase {

    @BeforeClass
    public void setUp(){
        System.setProperty("webdriver.chrome.driver","src/test/resources/chromedriver.exe");
    }

    @BeforeMethod
    public void BeforeMethod(){
        initDrivers("Chrome");
        initPages();
    }

    @Test
    public void readMessages() throws InterruptedException {

        authorization(user.getLogin(),user.getPassword());

        Thread.sleep(2000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='submit_post_box']")));
        goTo(data.vkMessagesUnread);

        while(isElementPresent(messagesPage.unreadedMessages)) {
            click(messagesPage.unreadedMessages);
            try{
                new WebDriverWait(driver, 2)
                        .until(ExpectedConditions.visibilityOf(dialogPage.toEnd)).click();
                        Thread.sleep(1500);
            }catch (TimeoutException ex){

            }

            for (String del : data.dela) {
                if (dialogPage.lastMessage().getText().toLowerCase().contains(del)) {
                    quest++;
                    sendTextTo(dialogPage.messageInput,"normalno");
                    break;
                }
            }

            if(dialogPage.lastMessage().findElement(By.xpath("//a")).isDisplayed()){
                if(dialogPage.lastMessage()
                        .findElement(By.xpath("//a"))
                        .getAttribute("style")
                        .contains(".jpg")){

                    img++;
                    sendTextTo(dialogPage.messageInput,"lol");
                }
            }
            goTo(data.vkMessagesUnread);
        }
        Assert.assertFalse(isElementPresent(messagesPage.unreadedMessages));
    }

    //@AfterTest
    public void tearDown(){
        System.out.println(quest + " человека спросили как у тебя дела, " + img + " отправили картинку" );
        driver.close();
    }


}
