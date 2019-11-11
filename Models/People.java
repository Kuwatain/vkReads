package vkReads.Models;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.openqa.selenium.Keys;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class People {

    private String hair_color;
    private String skin_color;
    private String birth_year;
    private String eye_color;
    private String height;
    private String gender;
    private String name;
    private String mass;

    private List<String> starships;
    private List<String> vehicles;
    private List<String> species;
    private List<String> films;


    public String getPeopleInfo() throws URISyntaxException {

        StringBuilder s = new StringBuilder();

        s.append("Имя: ").append(getName()).append("\n");
        s.append("Дата рождения: ").append(getBirth_year()).append("\n");
        s.append("Цвет глаз: ").append(getEye_color()).append("\n");
        s.append("Пол: ").append(getGender()).append("\n");
        s.append("Цвет волос: ").append(getHair_color()).append("\n");
        s.append("Рост: ").append(getHeight()).append("\n");
        s.append("Вес: ").append(getMass()).append("\n");
        s.append("Цвет кожи: ").append(getSkin_color()).append("\n");

        s.append("Фильмы: ");
        List<String> films = getFilms();
        for (String film : films){
            s.append(film).append(films.indexOf(film) == films.size() - 1 ? ".\n" : ", ");
        }
        s.append("Вид: ").append(getSpecies().get(0)).append("\n");

        s.append("Космич. Корабли: ");
        if(getStarships().size() != 0) {
            List<String> starships = getStarships();
            for (String starship : starships){
                s.append(starship).append(starships.indexOf(starship) == starships.size() - 1 ? ".\n" : ", ");
            }
        }else{
            s.append("n/a\n");
        }
        s.append("Траспорт: ");
        if (getVehicles().size() != 0) {
            List<String> vehicles = getVehicles();
            for (String vehicle : films){
                s.append(vehicle).append(vehicles.indexOf(vehicle) == vehicles.size() - 1 ? ".\n" : ", ");
            }
        }else{
            s.append("n/a\n");
        }

        s = new StringBuilder(s.toString().replace("\n", Keys.chord(Keys.CONTROL, Keys.ENTER)));

        return s.toString();

    }
    public static People getPeople(Response response){
        Gson gson = new Gson();
        Object requested = response.getBody().jsonPath().get("results[0]");
        return gson.fromJson(gson.toJson(requested), People.class);
    }


    private List<String> getStarships() throws URISyntaxException {
        List<String> starship = new ArrayList<>();
        for (String s : starships) {
            URI uri = new URI(s);
            Response response = RestAssured.given()
                    .baseUri(uri.getScheme() + "://" + uri.getAuthority())
                    .basePath(uri.getPath())
                    .when()
                    .get();
            starship.add(response.getBody().jsonPath().getString("name"));
        }
        return starship;
    }
    private List<String> getVehicles() throws URISyntaxException {
        List<String> vehicle = new ArrayList<>();
        for (String s : vehicles) {
            URI uri = new URI(s);
            Response response = RestAssured.given()
                    .baseUri(uri.getScheme() + "://" + uri.getAuthority())
                    .basePath(uri.getPath())
                    .when()
                    .get();
            vehicle.add(response.getBody().jsonPath().getString("name"));
        }
        return vehicle;
    }
    private List<String> getSpecies() throws URISyntaxException {
        List<String> specie = new ArrayList<>();
        for (String s : species) {
            URI uri = new URI(s);
            Response response = RestAssured.given()
                    .baseUri(uri.getScheme() + "://" + uri.getAuthority())
                    .basePath(uri.getPath())
                    .when()
                    .get();
            specie.add(response.getBody().jsonPath().getString("name"));
        }
        return specie;
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

    private String getHair_color() {
        return hair_color;
    }
    private String getSkin_color() {
        return skin_color;
    }
    private String getBirth_year() {
        return birth_year;
    }
    private String getEye_color() {
        return eye_color;
    }
    private String getGender() {
        return gender;
    }
    private String getHeight() {
        return height;
    }
    public String getName() {
        return name;
    }
    private String getMass() {
        return mass;
    }
}


