package com.example.studentteachercollaborations;

public class ThesisInfo {
    private String id;
    private String title;
    private String authors;
    private String url;
    private String storageFileName;

    public ThesisInfo() {
    }

    public ThesisInfo(String id, String title, String authors, String url, String storageFileName) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.url = url;
        this.storageFileName = storageFileName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStorageFileName() {
        return storageFileName;
    }

    public void setStorageFileName(String storageFileName) {
        this.storageFileName = storageFileName;
    }
}
