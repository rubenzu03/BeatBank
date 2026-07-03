package com.rubenzu03.beatbank;

import com.rubenzu03.beatbank.application.dto.*;
import com.rubenzu03.beatbank.application.exception.GlobalExceptionHandler;
import com.rubenzu03.beatbank.application.exception.ResourceNotFoundException;
import com.rubenzu03.beatbank.application.port.inbound.GenreUseCase;
import com.rubenzu03.beatbank.adapter.inbound.rest.GenreController;
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
class GenreControllerTest {

    private MockMvc mockMvc;

    @Mock
    private GenreUseCase genreUseCase;

    @BeforeEach
    void setUp() {
        GenreController controller = new GenreController(genreUseCase);
        mockMvc = standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void getAllGenres_ShouldReturnPagedResponse() throws Exception {
        Page<GenreDto> page = new PageImpl<>(List.of(
                new GenreDto(1L, "Rock", "Rock music", null)
        ));
        when(genreUseCase.getAllGenres(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/genres"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].name").value("Rock"))
                .andExpect(jsonPath("$.totalItems").value(1));
    }

    @Test
    void getGenreById_WhenExists_ShouldReturnGenre() throws Exception {
        when(genreUseCase.getGenreById(1L)).thenReturn(
                new GenreDto(1L, "Rock", "Rock music", null)
        );

        mockMvc.perform(get("/api/genres/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Rock"));
    }

    @Test
    void getGenreById_WhenNotExists_ShouldReturn404() throws Exception {
        when(genreUseCase.getGenreById(99L)).thenThrow(new ResourceNotFoundException("Genre", 99L));

        mockMvc.perform(get("/api/genres/99"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Genre with id 99 not found"));
    }

    @Test
    void createGenre_ShouldReturn201() throws Exception {
        when(genreUseCase.createGenre(any(GenreDto.class))).thenReturn(
                new GenreDto(1L, "Rock", "Rock music", null)
        );

        mockMvc.perform(post("/api/genres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Rock\",\"description\":\"Rock music\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Rock"));
    }

    @Test
    void createGenre_WithBlankName_ShouldReturn400() throws Exception {
        mockMvc.perform(post("/api/genres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void patchGenre_ShouldReturn200() throws Exception {
        when(genreUseCase.patchGenre(eq(1L), any(GenrePatchDto.class))).thenReturn(
                new GenreDto(1L, "Updated Genre", "New desc", null)
        );

        mockMvc.perform(patch("/api/genres/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Genre\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Genre"));
    }

    @Test
    void deleteGenreById_WhenExists_ShouldReturn200() throws Exception {
        doNothing().when(genreUseCase).deleteGenreById(1L);

        mockMvc.perform(delete("/api/genres/1"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteGenreById_WhenNotExists_ShouldReturn404() throws Exception {
        doThrow(new ResourceNotFoundException("Genre", 99L)).when(genreUseCase).deleteGenreById(99L);

        mockMvc.perform(delete("/api/genres/99"))
                .andExpect(status().isNotFound());
    }
}
