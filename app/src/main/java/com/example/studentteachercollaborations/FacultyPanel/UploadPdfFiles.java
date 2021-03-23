package com.example.studentteachercollaborations.FacultyPanel;

public class UploadPdfFiles {
    private String id;
    private String name;
    private String url;
    private String storageFileName;

    public UploadPdfFiles() {
    }

    public UploadPdfFiles(String id, String name, String url, String storageFileName) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.storageFileName = storageFileName;
    }

    public String getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getStorageFileName() {
        return storageFileName;
    }
}
