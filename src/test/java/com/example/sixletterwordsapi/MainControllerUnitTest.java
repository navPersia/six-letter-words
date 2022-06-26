package com.example.sixletterwordsapi;

import com.example.sixletterwordsapi.model.FileInfo;
import com.example.sixletterwordsapi.repository.SixLetterRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SixLetterRepository sixLetterRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testHandledfile() throws Exception {

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

        FileInfo fileinfo1 = new FileInfo("file1", list1);
        fileinfo1.setId("1");
        FileInfo fileinfo2 = new FileInfo("file2", list2);
        fileinfo1.setId("2");
        FileInfo fileinfo3 = new FileInfo("file3", list3);
        fileinfo1.setId("3");

        given(sixLetterRepository.findById("1")).willReturn(Optional.of(fileinfo1));
        given(sixLetterRepository.findById("2")).willReturn(Optional.of(fileinfo2));
        given(sixLetterRepository.findById("3")).willReturn(Optional.of(fileinfo3));

        mockMvc.perform(get("/api/filedetail/{id}", 1))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("file1")))
                .andExpect(jsonPath("$.fileList", is(list1)));

        mockMvc.perform(get("/api/filedetail/{id}", 2))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("file2")))
                .andExpect(jsonPath("$.fileList", is(list2)));

        mockMvc.perform(get("/api/filedetail/{id}", 3))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("file3")))
                .andExpect(jsonPath("$.fileList", is(list3)));
    }

}
