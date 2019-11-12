package vkReads;

import io.restassured.RestAssured;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.Keys;
import org.openqa.selenium.By;

import org.testng.annotations.Test;

import vkReads.Models.*;

import java.util.InputMismatchException;
import java.net.URISyntaxException;


public class StarWarsApi extends TestBase{

    @Test
    public void starWarsBot() throws InterruptedException {

        authorization(user.getLogin(),user.getPassword());
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='submit_post_box']")));

        goTo(data.vkAllMessages);
        messagesPage.searchInput.sendKeys(data.wroteStarWars.get(0), Keys.ENTER);
        wait.until((ExpectedConditions.visibilityOf(dialogPage.messageInput)));

        daBot:
        while (true) {
            try {
                intro();
                switch (dialogPage.lastMessage().getText()) {
                    case "1": {
                        response = RestAssured.given()
                                .baseUri(data.URI)
                                .basePath(endpoints.filmsPath)
                                .when()
                                .get();

                        filmsLoop:
                        while (true) {
                            sendTextTo(dialogPage.messageInput,"Выбери цифру нужного фильма");
                            getAttributes(response, "title");
                            try {
                                switch (dialogPage.lastMessage().getText()) {
                                    case "n":
                                        nextResponse("next");
                                        break;
                                    case "b":
                                        nextResponse("previous");
                                        break;
                                    case "q":
                                        break filmsLoop;
                                    default:
                                        if (hashMap.containsKey(dialogPage.lastMessage().getText())) {
                                            response = RestAssured.given()
                                                    .baseUri(data.URI)
                                                    .basePath(endpoints.filmsPath)
                                                    .param("search",hashMap.get(dialogPage.lastMessage().getText()))
                                                    .when()
                                                    .get();
                                            films = Films.getFilms(response);
                                            sendTextTo(dialogPage.messageInput, films.getFilmInfo());
                                            sendTextTo(dialogPage.messageInput,getFullInfo(films));
                                            break filmsLoop;
                                        } else {
                                            throw new InputMismatchException("Неверный ввод");
                                        }
                                }
                            } catch (InputMismatchException ex) {
                                dialogPage.messageInput.sendKeys(ex.getMessage(),Keys.ENTER);
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    }
                    case "2": {
                        response = RestAssured.given()
                                .baseUri(data.URI)
                                .basePath(endpoints.peoplePath)
                                .when()
                                .get();

                        planetLoop:
                        while (true) {
                            sendTextTo(dialogPage.messageInput,"Выбери цифру нужного персонажа");
                            getAttributes(response, "name");
                            try {
                                switch (dialogPage.lastMessage().getText()) {
                                    case "n":
                                        nextResponse("next");
                                        break;
                                    case "b":
                                        nextResponse("previous");
                                        break;
                                    case "q":
                                        break planetLoop;
                                    default:
                                        if (hashMap.containsKey(dialogPage.lastMessage().getText())) {
                                            response = RestAssured.given()
                                                    .baseUri(data.URI)
                                                    .basePath(endpoints.peoplePath)
                                                    .param("search",hashMap.get(dialogPage.lastMessage().getText()))
                                                    .when()
                                                    .get();
                                            people = People.getPeople(response);
                                            sendTextTo(dialogPage.messageInput, people.getPeopleInfo());
                                            break planetLoop;
                                        } else {
                                            throw new InputMismatchException("Неверный ввод");
                                        }
                                }
                            } catch (InputMismatchException ex) {
                                dialogPage.messageInput.sendKeys(ex.getMessage(),Keys.ENTER);
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    }
                    case "3": {
                        response = RestAssured.given()
                                .baseUri(data.URI)
                                .basePath(endpoints.planetPath)
                                .when()
                                .get();

                        planetLoop:
                        while (true) {
                            sendTextTo(dialogPage.messageInput,"Выбери цифру нужной планеты");
                            getAttributes(response, "name");
                            try {
                                switch (dialogPage.lastMessage().getText()) {
                                    case "n":
                                        nextResponse("next");
                                        break;
                                    case "b":
                                        nextResponse("previous");
                                        break;
                                    case "q":
                                        break planetLoop;
                                    default:
                                        if (hashMap.containsKey(dialogPage.lastMessage().getText())) {
                                            response = RestAssured.given()
                                                    .baseUri(data.URI)
                                                    .basePath(endpoints.planetPath)
                                                    .param("search",hashMap.get(dialogPage.lastMessage().getText()))
                                                    .when()
                                                    .get();
                                            planets = Planets.getPlanet(response);
                                            sendTextTo(dialogPage.messageInput, planets.getPlanetInfo());
                                            sendTextTo(dialogPage.messageInput,getFullInfo(planets));
                                            break planetLoop;
                                        } else {
                                            throw new InputMismatchException("Неверный ввод");
                                        }
                                }
                            } catch (InputMismatchException ex) {
                                dialogPage.messageInput.sendKeys(ex.getMessage(),Keys.ENTER);
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    }
                    case "4": {
                        response = RestAssured.given()
                                .baseUri(data.URI)
                                .basePath(endpoints.speciesPath)
                                .when()
                                .get();

                        speciesLoop:
                        while (true) {
                            sendTextTo(dialogPage.messageInput,"Выбери цифру нужной расы");
                            getAttributes(response, "name");
                            try {
                                switch (dialogPage.lastMessage().getText()) {
                                    case "n":
                                        nextResponse("next");
                                        break;
                                    case "b":
                                        nextResponse("previous");
                                        break;
                                    case "q":
                                        break speciesLoop;
                                    default:
                                        if (hashMap.containsKey(dialogPage.lastMessage().getText())) {
                                            response = RestAssured.given()
                                                    .baseUri(data.URI)
                                                    .basePath(endpoints.speciesPath)
                                                    .param("search",hashMap.get(dialogPage.lastMessage().getText()))
                                                    .when()
                                                    .get();
                                            species = Species.getSpecie(response);
                                            sendTextTo(dialogPage.messageInput, species.getSpeciesInfo());
                                            break speciesLoop;
                                        } else {
                                            throw new InputMismatchException("Неверный ввод");
                                        }
                                }
                            } catch (InputMismatchException ex) {
                                dialogPage.messageInput.sendKeys(ex.getMessage(),Keys.ENTER);
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    }
                    case "5": {
                        response = RestAssured.given()
                                .baseUri(data.URI)
                                .basePath(endpoints.starshipsPath)
                                .when()
                                .get();

                        starshipsLoop:
                        while (true) {
                            sendTextTo(dialogPage.messageInput,"Выбери цифру нужного корабля");
                            getAttributes(response, "name");
                            try {
                                switch (dialogPage.lastMessage().getText()) {
                                    case "n":
                                        nextResponse("next");
                                        break;
                                    case "b":
                                        nextResponse("previous");
                                        break;
                                    case "q":
                                        break starshipsLoop;
                                    default:
                                        if (hashMap.containsKey(dialogPage.lastMessage().getText())) {
                                            response = RestAssured.given()
                                                    .baseUri(data.URI)
                                                    .basePath(endpoints.starshipsPath)
                                                    .param("search",hashMap.get(dialogPage.lastMessage().getText()))
                                                    .when()
                                                    .get();
                                            starships = Starships.getStarship(response);
                                            sendTextTo(dialogPage.messageInput, starships.getStarshipInfo());
                                            break starshipsLoop;
                                        } else {
                                            throw new InputMismatchException("Неверный ввод");
                                        }
                                }
                            } catch (InputMismatchException ex) {
                                dialogPage.messageInput.sendKeys(ex.getMessage(),Keys.ENTER);
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    }
                    case "6": {
                        response = RestAssured.given()
                                .baseUri(data.URI)
                                .basePath(endpoints.vehiclesPath)
                                .when()
                                .get();

                        starshipsLoop:
                        while (true) {
                            sendTextTo(dialogPage.messageInput,"Выбери цифру нужного транспорта");
                            getAttributes(response, "name");
                            try {
                                switch (dialogPage.lastMessage().getText()) {
                                    case "n":
                                        nextResponse("next");
                                        break;
                                    case "b":
                                        nextResponse("previous");
                                        break;
                                    case "q":
                                        break starshipsLoop;
                                    default:
                                        if (hashMap.containsKey(dialogPage.lastMessage().getText())) {
                                            response = RestAssured.given()
                                                    .baseUri(data.URI)
                                                    .basePath(endpoints.vehiclesPath)
                                                    .param("search",hashMap.get(dialogPage.lastMessage().getText()))
                                                    .when()
                                                    .get();
                                            vehicles = Vehicles.getVehicle(response);
                                            sendTextTo(dialogPage.messageInput, vehicles.getVehicleInfo());
                                            break starshipsLoop;
                                        } else {
                                            throw new InputMismatchException("Неверный ввод");
                                        }
                                }
                            } catch (InputMismatchException ex) {
                                dialogPage.messageInput.sendKeys(ex.getMessage(),Keys.ENTER);
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    }
                    case "quit": {
                        dialogPage.messageInput.sendKeys("ya powel, brat",Keys.ENTER);
                        break daBot;
                    }
                    default: {
                        throw new InputMismatchException("Неверный ввод: " + dialogPage.lastMessage().getText());
                    }
                }
            }
            catch (InputMismatchException ex){
                dialogPage.messageInput.sendKeys(ex.getMessage(),Keys.ENTER);
            }
        }

    }
}
