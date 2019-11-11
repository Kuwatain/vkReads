package vkReads.Models;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.openqa.selenium.Keys;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Species {

    private String average_lifespan;
    private String average_height;
    private String classification;
    private String designation;
    private String hair_colors;
    private String skin_colors;
    private String eye_colors;
    private String homeworld;
    private String language;
    private String name;

    private List<String> people;
    private List<String> films;



    public  String getSpeciesInfo() throws URISyntaxException {
        StringBuilder s = new StringBuilder();
        s.append("Название: ").append(getName()).append("\n");
        s.append("Средний рост: ").append(getAverage_height()).append("\n");
        s.append("Средний срок жизни: ").append(getAverage_lifespan()).append("\n");
        s.append("Классификация: ").append(getClassification()).append("\n");
        s.append("Обозначение: ").append(getDesignation()).append("\n");
        s.append("Цвет глаз: ").append(getEye_colors()).append("\n");
        s.append("Цвет волос: ").append(getHair_colors()).append("\n");
        s.append("Цвет кожи: ").append(getSkin_colors()).append("\n");
        s.append("Родная планета: ").append(getHomeworld()).append("\n");
        s.append("Язык: ").append(getLanguage()).append("\n");

        s.append("Персонажи: ");
        List<String> characters = getPeople();
        for (String character : characters){
            s.append(character).append(characters.indexOf(character) == characters.size() - 1 ? ".\n" : ", ");
        }

        s.append("Фильмы: ");
        List<String> titles = getFilms();
        for (String title : titles){
            s.append(title).append(titles.indexOf(title) == titles.size() - 1 ? ".\n" : ", ");
        }

        s = new StringBuilder(s.toString().replace("\n", Keys.chord(Keys.CONTROL, Keys.ENTER)));

        return s.toString();

    }
    public static Species getSpecie(Response response){
        Gson gson = new Gson();
        Object requested = response.getBody().jsonPath().get("results[0]");
        return gson.fromJson(gson.toJson(requested), Species.class);
    }

    private List<String> getPeople() throws URISyntaxException {
        List<String> peopl = new ArrayList<>();
        for (String s : people) {
            URI uri = new URI(s);
            Response response = RestAssured.given()
                    .baseUri(uri.getScheme() + "://" + uri.getAuthority())
                    .basePath(uri.getPath())
                    .when()
                    .get();
            peopl.add(response.getBody().jsonPath().getString("name"));
        }
        return peopl;
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

    private String getAverage_lifespan() {
        return average_lifespan;
    }
    private String getHomeworld() throws URISyntaxException {
        URI uri = new URI(homeworld);
        Response response = RestAssured.given()
                .baseUri(uri.getScheme()+"://" + uri.getAuthority())
                .basePath(uri.getPath())
                .when()
                .get();
        return response.getBody().jsonPath().getString("name");
    }
    private String getAverage_height() {
        return average_height;
    }
    private String getClassification() {
        return classification;
    }
    private String getDesignation() {
        return designation;
    }
    private String getHair_colors() {
        return hair_colors;
    }
    private String getSkin_colors() {
        return skin_colors;
    }
    private String getEye_colors() {
        return eye_colors;
    }
    private String getLanguage() {
        return language;
    }
    public String getName() {
        return name;
    }
}
