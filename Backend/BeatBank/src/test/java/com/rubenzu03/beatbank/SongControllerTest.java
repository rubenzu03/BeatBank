package com.rubenzu03.beatbank;

import com.rubenzu03.beatbank.application.dto.*;
import com.rubenzu03.beatbank.application.exception.GlobalExceptionHandler;
import com.rubenzu03.beatbank.application.exception.ResourceNotFoundException;
import com.rubenzu03.beatbank.application.port.inbound.SongUseCase;
import com.rubenzu03.beatbank.adapter.inbound.rest.SongController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
class SongControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SongUseCase songUseCase;

    @BeforeEach
    void setUp() {
        SongController controller = new SongController(songUseCase);
        mockMvc = standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void getAllSongs_ShouldReturnPagedResponse() throws Exception {
        Page<SongDto> page = new PageImpl<>(List.of(
                new SongDto(1L, "Song A", "3:00", 10L, null, null)
        ));
        when(songUseCase.getAllSongs(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/songs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].name").value("Song A"))
                .andExpect(jsonPath("$.totalItems").value(1));
    }

    @Test
    void searchSongs_ShouldReturnPagedResponse() throws Exception {
        Page<SongDto> page = new PageImpl<>(List.of(
                new SongDto(1L, "Result", "3:00", 5L, null, null)
        ));
        when(songUseCase.searchSongs(eq("rock"), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/songs/search").param("q", "rock"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].name").value("Result"));
    }

    @Test
    void getSongById_WhenExists_ShouldReturnSong() throws Exception {
        when(songUseCase.getSongById(1L)).thenReturn(
                new SongDto(1L, "Test Song", "3:45", 100L, null, null)
        );

        mockMvc.perform(get("/api/songs/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Song"));
    }

    @Test
    void getSongById_WhenNotExists_ShouldReturn404() throws Exception {
        when(songUseCase.getSongById(99L)).thenThrow(new ResourceNotFoundException("Song", 99L));

        mockMvc.perform(get("/api/songs/99"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Song with id 99 not found"));
    }

    @Test
    void createSong_ShouldReturn201() throws Exception {
        when(songUseCase.createSong(any(SongDto.class))).thenReturn(
                new SongDto(1L, "New Song", "3:00", 0L, null, null)
        );

        mockMvc.perform(post("/api/songs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New Song\",\"duration\":\"3:00\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("New Song"));
    }

    @Test
    void createSong_WithBlankName_ShouldReturn400() throws Exception {
        mockMvc.perform(post("/api/songs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\",\"duration\":\"3:00\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateSong_WhenExists_ShouldReturn200() throws Exception {
        when(songUseCase.updateSong(eq(1L), any(SongDto.class))).thenReturn(
                new SongDto(1L, "Updated", "4:00", 50L, null, null)
        );

        mockMvc.perform(put("/api/songs/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated\",\"duration\":\"4:00\",\"plays\":50}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated"));
    }

    @Test
    void patchSong_ShouldReturn200() throws Exception {
        when(songUseCase.patchSong(eq(1L), any(SongPatchDto.class))).thenReturn(
                new SongDto(1L, "Patched", "3:00", 100L, null, null)
        );

        mockMvc.perform(patch("/api/songs/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Patched\",\"plays\":100}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Patched"));
    }

    @Test
    void incrementPlays_ShouldReturn200() throws Exception {
        when(songUseCase.incrementPlays(1L)).thenReturn(
                new SongDto(1L, "Test", "3:00", 11L, null, null)
        );

        mockMvc.perform(post("/api/songs/1/play"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.plays").value(11));
    }

    @Test
    void deleteSongById_WhenExists_ShouldReturn200() throws Exception {
        doNothing().when(songUseCase).deleteSongById(1L);

        mockMvc.perform(delete("/api/songs/1"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteSongById_WhenNotExists_ShouldReturn404() throws Exception {
        doThrow(new ResourceNotFoundException("Song", 99L)).when(songUseCase).deleteSongById(99L);

        mockMvc.perform(delete("/api/songs/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void addArtistToSong_ShouldReturn200() throws Exception {
        when(songUseCase.addArtistToSong(eq(1L), any(ArtistDto.class))).thenReturn(
                new SongDto(1L, "Song", "3:00", 0L, null, null)
        );

        mockMvc.perform(post("/api/songs/1/artists")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New Artist\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteArtistFromSong_ShouldReturn200() throws Exception {
        doNothing().when(songUseCase).deleteArtistFromSong(1L, 2L);

        mockMvc.perform(delete("/api/songs/1/artists/2"))
                .andExpect(status().isOk());
    }
}
