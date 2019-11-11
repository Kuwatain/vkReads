package vkReads.Models;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.openqa.selenium.Keys;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Starships {

    private String max_atmosphering_speed;
    private String hyperdrive_rating;
    private String cost_in_credits;
    private String cargo_capacity;
    private String starship_class;
    private String manufacturer;
    private String consumables;
    private String passengers;
    private String length;
    private String model;
    private String MGLT;
    private String crew;
    private String name;

    private List<String> pilots;
    private List<String> films;

    public String  getStarshipInfo() throws URISyntaxException {

        StringBuilder s = new StringBuilder();

        s.append("Название: ").append(getName()).append("\n");
        s.append("Модель: ").append(getModel()).append("\n");
        s.append("Производитель: ").append(getManufacturer()).append("\n");
        s.append("Цена в Галактич. кредитах: ").append(getCost_in_credits()).append("\n");
        s.append("Длина: ").append(getLength()).append("\n");
        s.append("Макс. скорость: ").append(getMax_atmosphering_speed()).append("\n");
        s.append("Человек в команде: ").append(getCrew()).append("\n");
        s.append("Пассажиры: ").append(getPassengers()).append("\n");
        s.append("Грузоподъемность: ").append(getCargo_capacity()).append("\n");
        s.append("Запас хода:").append(getConsumables()).append("\n");
        s.append("Рейтинг гиперпривода: ").append(getHyperdrive_rating()).append("\n");
        s.append("Мегасвет в час: ").append(getMGLT()).append("\n");
        s.append("Класс корабля: ").append(getStarship_class()).append("\n");

        s.append("Фильмы: ");
        List<String> titles = getFilms();
        for (String title : titles){
            s.append(title).append(titles.indexOf(title) == titles.size() - 1 ? ".\n" : ", ");
        }

        s.append("Пилоты: ");
        List<String> pilots = getPilots();
        if(pilots.size() != 0) {
            for (String pilot : pilots){
                s.append(pilot).append(pilots.indexOf(pilot) == pilots.size() - 1 ? ".\n" : ", ");
            }
        }else{
            s.append("n/a");
        }

        s = new StringBuilder(s.toString().replace("\n", Keys.chord(Keys.CONTROL, Keys.ENTER)));

        return s.toString();
    }
    public static Starships getStarship(Response response){
        Gson gson = new Gson();
        Object requested = response.getBody().jsonPath().get("results[0]");
        return gson.fromJson(gson.toJson(requested), Starships.class);
    }

    private List<String> getPilots() throws URISyntaxException {
        List<String> pilot = new ArrayList<>();
        for (String p : pilots) {
            URI uri = new URI(p);
            Response response = RestAssured.given()
                    .baseUri(uri.getScheme() + "://" + uri.getAuthority())
                    .basePath(uri.getPath())
                    .when()
                    .get();
            pilot.add(response.getBody().jsonPath().getString("name"));
        }
        return pilot;
    }
    private List<String> getFilms() throws URISyntaxException {
        List<String> titles = new ArrayList<>();
        for (String film : films) {
            URI uri = new URI(film);
            Response response = RestAssured.given()
                    .baseUri(uri.getScheme() + "://" + uri.getAuthority())
                    .basePath(uri.getPath())
                    .when()
                    .get();
            titles.add(response.getBody().jsonPath().getString("title"));
        }
        return titles;
    }

    private String getMax_atmosphering_speed() {
        return max_atmosphering_speed;
    }
    private String getHyperdrive_rating() {
        return hyperdrive_rating;
    }
    private String getCost_in_credits() {
        return cost_in_credits;
    }
    private String getCargo_capacity() {
        return cargo_capacity;
    }
    private String getStarship_class() {
        return starship_class;
    }
    private String getManufacturer() {
        return manufacturer;
    }
    private String getConsumables() {
        return consumables;
    }
    private String getPassengers() {
        return passengers;
    }
    private String getLength() {
        return length;
    }
    private String getModel() {
        return model;
    }
    private String getMGLT() {
        return MGLT;
    }
    private String getCrew() {
        return crew;
    }
    public String getName() {
        return name;
    }
}
