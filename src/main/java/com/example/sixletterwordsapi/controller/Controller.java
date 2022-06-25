package com.example.sixletterwordsapi.controller;

import com.example.sixletterwordsapi.model.FileInfo;
import com.example.sixletterwordsapi.repository.SixLetterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

    @Autowired
    private SixLetterRepository sixLetterRepository;

    @PostConstruct
    public void fillDb(){
        if (sixLetterRepository.count()==0){
            ArrayList<String> list1 = new ArrayList<String>();
            list1.add("na");
            list1.add("vi");
            list1.add("de");
            ArrayList<String> list2= new ArrayList<String>();
            list2.add("lu");
            list2.add("ci");
            list2.add("ch");
            ArrayList<String> list3= new ArrayList<String>();
            list3.add("sa");
            list3.add("ma");
            list3.add("ne");
            sixLetterRepository.save(new FileInfo("file1", list1));
            sixLetterRepository.save(new FileInfo("file2", list2));
            sixLetterRepository.save(new FileInfo("file3", list3));
        }
    }

    static ArrayList<String> noDuplicatedWord(ArrayList<String> listOfLines){
        // Create a new list without duplicated and words with more character than 6
        ArrayList<String> listOfLinesNoDuplicated = new ArrayList<String>();
        for (String element : listOfLines) {
            // If this element is not present in newList
            // If this element is smaller than 6 character
            // then add it
            if (!listOfLinesNoDuplicated.contains(element) && element.length() < 6) {
                listOfLinesNoDuplicated.add(element);
            }
        }
        return listOfLinesNoDuplicated;
    }

    static boolean isBiggerThan6(String word){
        if (6-word.length()<0){
            return true;
        }else{
            return false;
        }
    }
    static Boolean isWordComplete(String word1, String word2){
        if (word1.length() + word2.length() == 6){
            return true;
        }else {
            return false;
        }
    }

    static ArrayList<String> sixLetterCombination(ArrayList<String> listOfLinesNoDuplicated){
        // Make combination based on newlist
        ArrayList<String> newArrayList = new ArrayList<>();
        // loop over list
        for (int i = 0; i < listOfLinesNoDuplicated.size(); i++) {
            String e = listOfLinesNoDuplicated.get(i);
            // loop over list to find match
            for(int y = i+1; y < listOfLinesNoDuplicated.size(); y++){
                String n = listOfLinesNoDuplicated.get(y);
                if (isWordComplete(e,n)){
                    newArrayList.add(e+n);
                }
                // check is the word has number of character that we need
                if (!isWordComplete(e,n) && !isBiggerThan6(e+n)){
                    e += n;
                }
            }
        }
        return newArrayList;
    }

    @GetMapping("/api/output")
    public List<String> generatedfile () throws IOException {
        BufferedReader bufReader = new BufferedReader(new FileReader("src/main/resources/input.txt"));
        ArrayList<String> listOfLines = new ArrayList<>();
        String line = bufReader.readLine();
        while (line != null) {
            listOfLines.add(line);
            line = bufReader.readLine();
        }
        bufReader.close();

        return sixLetterCombination(noDuplicatedWord(listOfLines));
    }

    @GetMapping("/api/histories")
    public List<FileInfo> fileInfos (){
        return sixLetterRepository.findAll();
    }
    @GetMapping("/api/filedetail/{id}")
    public FileInfo fileInfo (@PathVariable String id){
        return sixLetterRepository.findById(id).get();
    }
}