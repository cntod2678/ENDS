package com.cdj.ends.dto;

import com.cdj.ends.data.NewsSource;

import java.util.List;

/**
 * Created by Dongjin on 2017. 8. 8..
 */

public class NewsSourceDTO {

    String category;

    String language;

    String country;

    String status;

    List<NewsSource> sources;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<NewsSource> getSources() {
        return sources;
    }

    public void setSources(List<NewsSource> sources) {
        this.sources = sources;
    }

    @Override
    public String toString() {
        return "NewsSourceDTO{" +
                "category='" + category + '\'' +
                ", language='" + language + '\'' +
                ", country='" + country + '\'' +
                ", status='" + status + '\'' +
                ", sources=" + sources +
                '}';
    }
}

