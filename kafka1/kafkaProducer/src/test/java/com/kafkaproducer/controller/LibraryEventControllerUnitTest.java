package com.kafkaproducer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafkaproducer.domain.Book;
import com.kafkaproducer.domain.LibraryEvent;
import com.kafkaproducer.producer.LibraryeventProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(LibraryEventController.class)
@AutoConfigureMockMvc
class LibraryEventControllerUnitTest {


    @Autowired
    MockMvc mockMvc;


    @MockBean
    LibraryeventProducer libraryeventProducer;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void PostLisbraryEvent() throws Exception {

        Book book = Book.builder().boodid(1).bookAuthor("test ıntegration").name("test ankara").build();

        LibraryEvent libraryEvent = LibraryEvent.builder().LibraryEventid(null).book(book).build();

        String json = objectMapper.writeValueAsString(libraryEvent);
        doNothing().when(libraryeventProducer).sendLibraryevent(isA(LibraryEvent.class));

        mockMvc.perform(post("/v1/libraryevent").content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());


    }

}