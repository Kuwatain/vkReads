package vkReads;

import org.openqa.selenium.WebDriver;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class Data {

    String vkNews = "https://vk.com/feed";
    String vkMessagesUnread = "https://vk.com/im?tab=unread";
    String vkAllMessages = "https://vk.com/im";
    List<String> dela = Arrays.asList("дел","дела");
    List<String> wroteStarWars;
    String lastMsgSended = "";
    String baseURI = "http://swapi.co/api";

}
