package com.example.sixletterwordsapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class Controller {

    @GetMapping("/output")
    public List<String> generatedfile () throws IOException {
        BufferedReader bufReader = new BufferedReader(new FileReader("src/main/resources/input.txt"));
        ArrayList<String> listOfLines = new ArrayList<>();
        String line = bufReader.readLine();
        while (line != null) {
            listOfLines.add(line);
            line = bufReader.readLine();
        }
        bufReader.close();

        // Create a new list without duplicated and word with more character than 6
        ArrayList<String> listOfLinesNoDuplicated = new ArrayList<String>();
        for (String element : listOfLines) {
            // If this element is not present in newList
            // If this element is smaller than 6 character
            // then add it
            if (!listOfLinesNoDuplicated.contains(element) && element.length() < 6) {
                listOfLinesNoDuplicated.add(element);
            }
        }

        // Make combination based on newlist
        ArrayList<String> newArrayList = new ArrayList<>();
        // loop over list
        for (String e : listOfLinesNoDuplicated) {
            // check how many character do we need for a word with 6 character
            int characterNeeded = 6 - e.length();
            // loop over list to find match
            for(String n : listOfLinesNoDuplicated){
                // check is the word has number of character that we need
                if(n.length() == characterNeeded){
                    newArrayList.add(e + n);
                }
            }
        }
        return newArrayList;
    }
}
