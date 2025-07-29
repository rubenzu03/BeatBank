package com.rubenzu03.beatbank;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.number.OrderingComparison.greaterThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SongTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    void testGetAllSongs() throws Exception {
        mockMvc.perform(get("/songs")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThan(0))));
    }

    @Test
    @Order(2)
    void testGetSongById() throws Exception {
        mockMvc.perform(get("/songs/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").isString())
                .andExpect(jsonPath("$.duration").isString());
    }

    @Test
    @Order(3)
    void testGetSongByIdNotFound() throws Exception {
        mockMvc.perform(get("/songs/99999999999999")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(4)
    void testGetSongByIdInvalid() throws Exception {
        mockMvc.perform(get("/songs/invalid")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(5)
    void testCreateSong() throws Exception {
        String newSongJson = "{ \"name\": \"New Song\", \"duration\": \"3:45\" }";

        MvcResult result = mockMvc.perform(post("/songs")
        .contentType(MediaType.APPLICATION_JSON).content(newSongJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("New Song"))
                .andExpect(jsonPath("$.duration").value("3:45"))
                .andExpect(jsonPath("$.id").isNumber()).andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Long newSongId = objectMapper.readTree(responseBody).get("id").asLong();

        mockMvc.perform(get("/songs/" + newSongId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(newSongId))
                .andExpect(jsonPath("$.name").value("New Song"))
                .andExpect(jsonPath("$.duration").value("3:45"));
    }

}
