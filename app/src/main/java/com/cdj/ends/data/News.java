package com.cdj.ends.data;

import org.parceler.Parcel;

/**
 * Created by Dongjin on 2017. 8. 8..
 */

@Parcel
public class News {
    private String id;
    private int number;
    private String owner;
    private String source;

    private String author;
    private String description;
    private String title;
    private String url;
    private String urlToImage;
    private String publishedAt;

    public News(){}

    public News(String id, int number, String owner, String source, String author, String description, String title, String url, String urlToImage, String publishedAt) {
        this.id = id;
        this.number = number;
        this.owner = owner;
        this.source = source;
        this.author = author;
        this.description = description;
        this.title = title;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", number=" + number +
                ", owner='" + owner + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", urlToImage='" + urlToImage + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                '}';
    }
}
