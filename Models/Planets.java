package vkReads.Models;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.openqa.selenium.Keys;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Planets extends Model{


    private  String rotation_period;
    private  String orbital_period;
    private  String population;
    private  String diameter;
    private  String climate;
    private  String gravity;
    private  String terrain;
    private  String name;

    private List<String> residents;
    private List<String> films;

    public String getFullInfo() throws URISyntaxException {
        StringBuilder s = new StringBuilder(getPlanetInfo());

        s.append("Фильмы: ");
        if(getFilms().size() != 0) {
            List<String> titles = getFilms();
            for (String title : titles) {
                s.append(title).append(titles.indexOf(title) == titles.size() - 1 ? ".\n" : ", ");
            }
        }
        else{
            s.append("n/a\n");
        }

        s.append("Жители: ");
        if(getResident().size() != 0) {
            List<String> names = getResident();
            for (String name : names) {
                s.append(name).append(names.indexOf(name) == names.size() - 1 ? ".\n" : ", ");
            }
        }
        else{
            s.append("n/a\n");
        }
        s = new StringBuilder(s.toString().replace("\n", Keys.chord(Keys.CONTROL, Keys.ENTER)));

        return s.toString();
    }
    public static Planets getPlanet(Response response){
        //this.driver = driver;
        Gson gson = new Gson();
        Object requested = response.getBody().jsonPath().get("results[0]");
        return gson.fromJson(gson.toJson(requested), Planets.class);
    }
    public  String getPlanetInfo(){
        String s = "";
        s += "Название: " + getName() +"\n";
        s += "Диаметр: " + getDiameter() +"\n";
        s += "Гравитация: " + getGravity() +"\n";
        s += "Орбитальный период: " + getOrbital_period() +"\n";
        s += "Сутки: " + getRotation_period()+ "часа" +"\n";
        s += "Климат: " + getClimate() +"\n";
        s += "Территория: " + getTerrain() +"\n";
        s += "Популяция: " + getPopulation() +"\n";

        s = s.replace("\n", Keys.chord(Keys.CONTROL,Keys.ENTER));
        return s;
    }

    private List<String> getResident() throws URISyntaxException {
        List<String> names = new ArrayList<>();
        for (String resident : residents) {
            URI uri = new URI(resident);
            Response response = RestAssured.given()
                    .baseUri(uri.getScheme() + "://" + uri.getAuthority())
                    .basePath(uri.getPath())
                    .when()
                    .get();
            names.add(response.getBody().jsonPath().getString("name"));
        }
        return names;
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

    private  String getRotation_period() {
        return rotation_period;
    }
    private  String getOrbital_period() {
        return orbital_period;
    }
    private  String getPopulation() {
        return population;
    }
    private  String getDiameter() {
        return diameter;
    }
    private  String getClimate() {
        return climate;
    }
    private  String getGravity() {
        return gravity;
    }
    private  String getTerrain() {
        return terrain;
    }
    public  String getName() {
        return name;
    }
}
