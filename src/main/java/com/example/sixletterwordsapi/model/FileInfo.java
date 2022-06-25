package com.example.sixletterwordsapi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document(collection = "files")
public class FileInfo {
    @Id
    private String id;
    private String name;
    private ArrayList<String> fileList;

    public FileInfo(String name, ArrayList<String> fileList) {
        this.name = name;
        this.fileList = fileList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getFileList() {
        return fileList;
    }

    public void setFileList(ArrayList<String> fileList) {
        this.fileList = fileList;
    }
}
