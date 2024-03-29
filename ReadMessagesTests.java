package vkReads;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.By;

import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.io.IOException;


public class ReadMessagesTests extends TestBase {

    @Test
    public void readMessages() throws InterruptedException, IOException {

        authorization(user.getLogin(),user.getPassword());
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='submit_post_box']")));
        goTo(data.vkMessagesUnread);

        while(isElementPresent(messagesPage.unreadedMessages)) {
            click(messagesPage.unreadedMessages);
            try{
                new WebDriverWait(driver, 2)
                        .until(ExpectedConditions.visibilityOf(dialogPage.toEnd)).click();
                        Thread.sleep(1500);
            }catch (TimeoutException ex){ }

            for (String del : data.dela) {
                if (dialogPage.lastMessage().getText().toLowerCase().contains(del)) {
                    sendTextTo(dialogPage.messageInput,"normalno");
                    break;
                }
            }

            if(dialogPage.lastMessage().findElement(By.xpath("//a")).isDisplayed()){
                if(dialogPage.lastMessage()
                        .findElement(By.xpath("//a"))
                        .getAttribute("style")
                        .contains(".jpg")){

                    sendTextTo(dialogPage.messageInput,"lol");
                }
            }

            if(dialogPage.lastMessage().getText().contains("star wars")){
                data.wroteStarWars.add(dialogPage.name.getText());
            }
            goTo(data.vkMessagesUnread);
        }
        writeText(data.wroteStarWars);
        Assert.assertFalse(isElementPresent(messagesPage.unreadedMessages));
    }

    @AfterTest
    public void tearDown(){
        driver.close();
    }


}
