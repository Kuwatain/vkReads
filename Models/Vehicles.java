package vkReads.Models;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.openqa.selenium.Keys;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Vehicles {


    private String max_atmosphering_speed;
    private String cost_in_credits;
    private String cargo_capacity;
    private String vehicle_class;
    private String manufacturer;
    private String consumables;
    private String passengers;
    private String length;
    private String model;
    private String crew;
    private String name;

    private List<String> films;
    private List<String> pilots;


    public  String getVehicleInfo() throws URISyntaxException {

        StringBuilder s = new StringBuilder();

        s.append("Название: ").append(getName()).append("\n");
        s.append("Модель: ").append(getModel()).append("\n");
        s.append("Класс транспорта: ").append(getVehicle_class()).append("\n");
        s.append("Производитель: ").append(getManufacturer()).append("\n");
        s.append("Длина: ").append(getLength()).append("\n");
        s.append("Цена в Галактич. кредитах: ").append(getCost_in_credits()).append("\n");
        s.append("Человек в команде: ").append(getCrew()).append("\n");
        s.append("Пассажиры: ").append(getPassengers()).append("\n");
        s.append("Макс. скорость: ").append(getMax_atmosphering_speed()).append("\n");
        s.append("Грузоподъемность: ").append(getCargo_capacity()).append("\n");
        s.append("Запас хода:").append(getConsumables()).append("\n");

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
    public static Vehicles getVehicle(Response response){
        Gson gson = new Gson();
        Object requested = response.getBody().jsonPath().get("results[0]");
        return gson.fromJson(gson.toJson(requested), Vehicles.class);
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
    private String getCost_in_credits() {
        return cost_in_credits;
    }
    private String getCargo_capacity() {
        return cargo_capacity;
    }
    private String getVehicle_class() {
        return vehicle_class;
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
    private String getCrew() {
        return crew;
    }
    public String getName() {
        return name;
    }

}
