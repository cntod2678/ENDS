package com.cdj.ends.data;

import java.util.List;

/**
 * Created by Dongjin on 2017. 8. 8..
 */

public class NewsSource {
    String id;
    String name;
    String description;
    String url;
    String category;
    String language;
    String country;
    Object urlsToLogos;
    List<SortedBysAvailable> sortedBysAvailables;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Object getUrlsToLogos() {
        return urlsToLogos;
    }

    public void setUrlsToLogos(Object urlsToLogos) {
        this.urlsToLogos = urlsToLogos;
    }

    public List<SortedBysAvailable> getSortedBysAvailables() {
        return sortedBysAvailables;
    }

    public void setSortedBysAvailables(List<SortedBysAvailable> sortedBysAvailables) {
        this.sortedBysAvailables = sortedBysAvailables;
    }

    @Override
    public String toString() {
        return "NewsSource{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", category='" + category + '\'' +
                ", language='" + language + '\'' +
                ", country='" + country + '\'' +
                ", urlsToLogos=" + urlsToLogos +
                ", sortedBysAvailables=" + sortedBysAvailables +
                '}';
    }

    /***
     *
     "status":"ok",
     "sources":[
     {
         "id":"abc-news-au",
         "name":"ABC News (AU)",
         "description":"Australia's most trusted source of local, national and world news. Comprehensive, independent, in-depth analysis, the latest business, sport, weather and more.",
         "url":"http://www.abc.net.au/news",
         "category":"general",
         "language":"en",
         "country":"au",
         "urlsToLogos":{
         "small":"",
         "medium":"",
         "large":""
         },
         "sortBysAvailable":[
         "top"
         ]
         },
     {
         "id":"al-jazeera-english",
         "name":"Al Jazeera English",
         "description":"News, analysis from the Middle East and worldwide, multimedia and interactives, opinions, documentaries, podcasts, long reads and broadcast schedule.",
         "url":"http://www.aljazeera.com",
         "category":"general",
         "language":"en",
         "country":"us",
         "urlsToLogos":{
         "small":"",
         "medium":"",
         "large":""
         },
         "sortBysAvailable":[
         "top",
         "latest"
         ]
     },
     */

}
