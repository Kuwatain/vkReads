package vkReads;

import org.openqa.selenium.Keys;

import java.util.Arrays;
import java.util.List;

class Data {

    String vkMessagesUnread = "https://vk.com/im?tab=unread";
    List<String> dela = Arrays.asList("дел","дела");
    String vkAllMessages = "https://vk.com/im";
    String vkNews = "https://vk.com/feed";
    String URI = "http://swapi.co/";
    List<String> wroteStarWars;
    String lastMsgSended = "";

    String introScheme = ("Добро пожаловать\n" +
                         "1) Фильмы\n" +
                         "2) Человеки\n" +
                         "3) Планеты\n" +
                         "4) Расы\n" +
                         "5) Корабли\n" +
                         "6) Транспорт\n" +
                         "quit) Закрыть бота").replace("\n", Keys.chord(Keys.CONTROL,Keys.ENTER));

}
