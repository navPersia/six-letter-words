package com.example.sixletterwordsapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

@Controller
public class MainController {

    @RequestMapping("/file-txt")
    public String handledfile(Model model) throws Exception{
        try {
            ArrayList<String> listOfLines = new ArrayList<>();
            BufferedReader bufReader = new BufferedReader(new FileReader("src/main/resources/input.txt"));
            String line = bufReader.readLine();
            while (line != null) {
                listOfLines.add(line);
                line = bufReader.readLine();
            }
            bufReader.close();
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
            model.addAttribute("arraytext", newArrayList);
        }catch (Exception e) {
            System.out.println("somthing goes wrong");
            System.out.println(e);
        }
        return "result";
    }


    @RequestMapping(value="/upload", method= RequestMethod.POST)
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) throws Exception{

        File convFile = new File(System.getProperty("java.io.tmpdir")+"/"+file.getName());
        file.transferTo(convFile);

        ArrayList<String> listOfLines = new ArrayList<>();
        BufferedReader bufReader = new BufferedReader(new FileReader(convFile));
        String line = bufReader.readLine();
        while (line != null) {
            listOfLines.add(line);
            line = bufReader.readLine();
        }
        bufReader.close();

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
        model.addAttribute("arraytext", newArrayList);

        return "result";
    }

    @RequestMapping("/newfile")
    public String newfile(){
        return "newfile";
    }

    @RequestMapping("/history")
    public String history(){
        return "history";
    }
}
