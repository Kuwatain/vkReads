package vkReads.Models;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.openqa.selenium.Keys;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public  class Films extends Model {

    private String opening_crawl;
    private String release_date;
    private String episode_id;
    private String director;
    private String producer;
    private String title;

    private List<String> characters;
    private List<String> starships;
    private List<String> vehicles;
    private List<String> planets;
    private List<String> species;


    public String getFullInfo() throws URISyntaxException {
        StringBuilder s = new StringBuilder(getFilmInfo());

        s.append("\nВступительные титры: \n" + getOpening_crawl() + "\n");

        s.append("Планеты: ");
        List<String> planets = getPlanets();
        for (String planet : planets){
            s.append(planet).append(planets.indexOf(planet) == planets.size() - 1 ? ".\n" : ", ");
        }

        s.append("Виды: ");
        List<String> species = getSpecies();
        for (String specie : species){
            s.append(specie).append(species.indexOf(specie) == species.size() - 1 ? ".\n" : ", ");
        }

        s.append("Космич. Корабли: ");
        List<String> starships = getStarships();
        if(getStarships().size() != 0) {
            for (String starship : starships){
                s.append(starship).append(starships.indexOf(starship) == starships.size() - 1 ? ".\n" : ", ");
            }
        }else{
            System.out.println("n/a");
        }

        s.append("Траспорт: ");
        List<String> vehicles = getVehicles();
        if (getVehicles().size() != 0) {
            for (String vehicle : vehicles){
                s.append(vehicle).append(vehicles.indexOf(vehicle) == vehicles.size() - 1 ? ".\n" : ", ");
            }
        }else{
            System.out.println("n/a");
        }

        s.append("Персонажи: ");
        List<String> characters = getCharacters();
        for (String character : characters){
            s.append(character).append(characters.indexOf(character) == characters.size() - 1 ? ".\n" : ", ");        }

        s = new StringBuilder(s.toString().replace("\n", Keys.chord(Keys.CONTROL, Keys.ENTER)));

        return s.toString();

    }
    public static Films getFilms(Response response){
        Gson gson = new Gson();
        Object requested = response.getBody().jsonPath().get("results[0]");
        return gson.fromJson(gson.toJson(requested), Films.class);
    }
    public String getFilmInfo(){
        String s = "";
        s += "Название фильма: " + getTitle() + "\n";
        s += "Эпизод: " + getEpisode_id() + "\n";
        s += "Дата выхода: " + getRelease_date() + "\n";
        s += "Продюссер: " + getProducer() + "\n";
        s += "Режиссер: " + getDirector() + "\n";

        s = s.replace("\n", Keys.chord(Keys.CONTROL,Keys.ENTER));
        return s;
    }

    private List<String> getCharacters() throws URISyntaxException {
        List<String> character = new ArrayList<>();
        for (String s : characters) {
            URI uri = new URI(s);
            Response response = RestAssured.given()
                    .baseUri(uri.getScheme() + "://" + uri.getAuthority())
                    .basePath(uri.getPath())
                    .when()
                    .get();
            character.add(response.getBody().jsonPath().getString("name"));
        }
        return character;
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
    private List<String> getPlanets() throws URISyntaxException {
        List<String> planet = new ArrayList<>();
        for (String s : planets) {
            URI uri = new URI(s);
            Response response = RestAssured.given()
                    .baseUri(uri.getScheme() + "://" + uri.getAuthority())
                    .basePath(uri.getPath())
                    .when()
                    .get();
            planet.add(response.getBody().jsonPath().getString("name"));
        }
        return planet;
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

    public String getOpening_crawl() {
        return opening_crawl;
    }
    private String getRelease_date() {
        return release_date;
    }
    private String getEpisode_id() {
        return episode_id;
    }
    private String getDirector() {
        return director;
    }
    private String getProducer() {
        return producer;
    }
    private String getTitle() {
        return title;
    }
}
