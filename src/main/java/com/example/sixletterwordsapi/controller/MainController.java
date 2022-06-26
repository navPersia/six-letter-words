package com.example.sixletterwordsapi.controller;

import com.example.sixletterwordsapi.model.FileInfo;
import com.example.sixletterwordsapi.repository.SixLetterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

@Controller
public class MainController {
    @Autowired
    private SixLetterRepository sixLetterRepository;

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

            ArrayList<String> newArrayList;
            newArrayList = sixLetterCombination(noDuplicatedWord(listOfLines));
            model.addAttribute("arraytext", newArrayList);
        }catch (Exception e) {
            System.out.println("something goes wrong");
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

        ArrayList<String> newArrayList;
        newArrayList = sixLetterCombination(noDuplicatedWord(listOfLines));
        model.addAttribute("arraytext", newArrayList);
        sixLetterRepository.save(new FileInfo(file.getName(), listOfLines));
        return "result";
    }

    @RequestMapping("/newfile")
    public String newfile(){
        return "newfile";
    }

    @RequestMapping("/history")
    public String history(Model model){
        model.addAttribute("files", sixLetterRepository.findAll());
        return "history";
    }

    @RequestMapping("/filedetail")
    public String fileDetail(HttpServletRequest request, Model model){
        String id = request.getParameter("id");
        ArrayList<String> newArrayList;
        newArrayList = sixLetterCombination(noDuplicatedWord(sixLetterRepository.findById(id).get().getFileList()));
        model.addAttribute("file", sixLetterRepository.findById(id).get());
        model.addAttribute("fileResult", newArrayList);
        return "filedetail";
    }

    @RequestMapping("/filedelete")
    public String fileDelete(HttpServletRequest request, Model model){
        String id = request.getParameter("id");
        FileInfo file = sixLetterRepository.findById(id).get();
        sixLetterRepository.delete(file);
        model.addAttribute("files", sixLetterRepository.findAll());
        return "history";
    }

}
