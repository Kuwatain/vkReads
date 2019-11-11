package vkReads;
import io.restassured.response.Response;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeSuite;
import vkReads.Models.*;
import vkReads.Pages.AuthPage;
import vkReads.Pages.BasePage;
import vkReads.Pages.DialogPage;
import vkReads.Pages.MessagesPage;
import com.google.gson.*;
import java.io.*;


import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class TestBase {

    //Check

    protected WebDriver driver;
    WebDriverWait wait;

    HashMap<Object, String> hashMap = new HashMap<>();
    Response response;

    Endpoints endpoints = new Endpoints();
    Starships starships;
    Vehicles vehicles;
    Planets planets;
    Species species;
    People people;
    Films films;

    Data data = new Data();
    User user = getUser();

    private boolean warningChecked = false;
    private AuthPage authPage;
    private BasePage basePage;
    MessagesPage messagesPage;
    DialogPage dialogPage;

    @BeforeSuite
    public void init(){
        System.setProperty("webdriver.chrome.driver","src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
    }

    void initDrivers() {
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);
    }
    void initPages() {
        authPage = new AuthPage(driver);
        messagesPage = new MessagesPage(driver);
        dialogPage = new DialogPage(driver);
        basePage = new BasePage(driver);
    }

    String getFullInfo(Model model) throws InterruptedException {
        String s = "";
        s += "Показать полную информацию?\n" +
                "Y) Da N) Net";
        s = s.replace("\n", Keys.chord(Keys.CONTROL,Keys.ENTER));
         while(true) {
             sendTextTo(dialogPage.messageInput,s);
             checkMsg();
            try {
                if(dialogPage.lastMessage().getText().equals("Y") || dialogPage.lastMessage().getText().equals("y")){
                    return model.getFullInfo();
                }
                if(dialogPage.lastMessage().getText().equals("N") || dialogPage.lastMessage().getText().equals("n")){
                    break;
                }
                throw new InputMismatchException("Неверный ввод");
            }
            catch (InputMismatchException | URISyntaxException ex){
                dialogPage.messageInput.sendKeys(ex.getMessage(),Keys.ENTER);
            }
        }
         return null;
    }

    void getAttributes(Response response,String attribute) throws InterruptedException {
        StringBuilder attributes = new StringBuilder();
        for(int i = 0; i < response.getBody().jsonPath().getList("results").size(); i++){
            attributes.append((i + " - "
                    + response.getBody().jsonPath().getString("results." + attribute + "[" + i + "]")
                    + "\n").replace("\n", Keys.chord(Keys.CONTROL, Keys.ENTER)));
            hashMap.put(Integer.toString(i),response.getBody().jsonPath().getString("results."+attribute+"["+i+"]"));
        }
        //attributes.append(pageNavigation());
        if (response.getBody().jsonPath().getString("next") == null &&
                response.getBody().jsonPath().getString("previous") == null){
            attributes.append("q) Categories");
        }else {
            if (response.getBody().jsonPath().getString("next") == null) {
                attributes.append("b) Back q) Categories");
            }else{
                if (response.getBody().jsonPath().getString("previous") == null) {
                    attributes.append("n) Next q) Categories");
                }else {
                        attributes.append("b) Back n) Next q) Categories");
                }
            }
        }
        sendTextTo(dialogPage.messageInput, attributes.toString());
        checkMsg();
    }
    void nextResponse(String string) throws URISyntaxException {
        if(response.getBody().jsonPath().getString(string)==null) {
            throw new InputMismatchException("Неверный ввод");
        }

        URI uri = new URI(response.getBody().jsonPath().getString(string));

        response = given()
                .baseUri(data.URI)
                .basePath(uri.getPath())
                .param(splitQuery(uri).get(0), splitQuery(uri).get(1))
                .when()
                .get();
    }
    private static List<String> splitQuery(URI uri){
        return Arrays.stream(uri.getQuery().split("=")).collect(Collectors.toList());
    }

    void authorization(String login, String password) {
        goTo(data.vkNews);
        sendTextTo(authPage.LogInput,login);
        sendTextTo(authPage.PassInput,password);
    }
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

    void writeText(List<String> list) throws IOException {
        Path file = Paths.get("starWars.txt");
        Files.write(file, list, StandardCharsets.UTF_8);
    }
    static List<String> reader() throws IOException {
        List<String> lines;
        lines = Files.readAllLines(Paths.get("C:\\Users\\jopad\\IdeaProjects\\untitled\\starWars.txt"), StandardCharsets.UTF_8);
        return lines;
    }

    void sendTextTo(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.sendKeys(text,Keys.ENTER);

        data.lastMsgSended = text.replace(Keys.chord(Keys.CONTROL,Keys.ENTER),"\n");

        if(!warningChecked) {
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
    void intro() throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOf(dialogPage.messageInput));
        dialogPage.messageInput.sendKeys(data.introScheme,Keys.ENTER);
        data.lastMsgSended = dialogPage.lastMessage().getText();
        checkMsg();
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
    void goTo(String url){
        driver.get(url);
        if(!warningChecked) {
            if (isElementPresent(basePage.safeWarning)) {
                click(basePage.closeWarningButton);
                warningChecked = true;
            }
        }
    }

    private void checkMsg() throws InterruptedException {
        try {
            while (true) {
                if (data.lastMsgSended.equals(dialogPage.lastMessage().getText())) {
                    Thread.sleep(3000);
                } else {
                    break;
                }
            }
        }
        catch (StaleElementReferenceException ex){
            checkMsg();
        }
    }
}