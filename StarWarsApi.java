package vkReads;

import io.restassured.RestAssured;
import io.restassured.RestAssured.*;
import io.restassured.matcher.RestAssuredMatchers.*;
import io.restassured.response.Response;
import org.hamcrest.Matchers.*;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class StarWarsApi extends TestBase{

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
    public void starWarsBot() throws InterruptedException, IOException {
        authorization(user.getLogin(),user.getPassword());
        Thread.sleep(2000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='submit_post_box']")));
        goTo("http:/vk.com/im");
        data.wroteStarWars = reader("C:\\Users\\jopad\\IdeaProjects\\untitled\\starWars.txt");
        sendTextTo(messagesPage.searchInput,data.wroteStarWars.get(0));

        wait.until((ExpectedConditions.visibilityOf(dialogPage.messageInput)));

        sendTextTo(dialogPage.messageInput,"ДАЙ ЗАПРОС СУЧКА, Я ОБРАБОТАЮ");
        sendTextTo(dialogPage.messageInput,"НАБЕРИ ЦИФРУ,БРАТ");
        sendTextTo(dialogPage.messageInput,"1) Фильмы 2) Человеки 3) планеты 4) Расы 5) корабли 6) вехиклес");


        while(true){

            if(data.lastMsgSended.equals(dialogPage.lastMessage().getText())){
                Thread.sleep(3000);
                continue;
            }

            if(dialogPage.lastMessage().getText().equals("2")){
                sendTextTo(dialogPage.messageInput,"Кого ищешь, брат?");
                checkMsg();
                String path = "/people/";

                Response response = RestAssured.given()
                        .baseUri(data.baseURI)
                        .basePath(path)
                        .param("search",dialogPage.lastMessage().getText())
                        .when()
                        .get();
                response.prettyPrint();
                sendTextTo(dialogPage.messageInput, response.getBody().jsonPath().getString("results[0].url"));
            }

            if(dialogPage.lastMessage().getText().equals("quit")){
                sendTextTo(dialogPage.messageInput,"ya powel, brat");
                break;
            }
        }
    }
}
