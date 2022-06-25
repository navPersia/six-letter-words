package com.example.sixletterwordsapi.model;

import java.util.ArrayList;

public class FileInfo {
    private String name;
    private ArrayList<String> file;
    public FileInfo(String name, ArrayList<String> file) {
        this.name = name;
        this.file = file;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getFile() {
        return file;
    }

    public void setFile(ArrayList<String> file) {
        this.file = file;
    }
}
