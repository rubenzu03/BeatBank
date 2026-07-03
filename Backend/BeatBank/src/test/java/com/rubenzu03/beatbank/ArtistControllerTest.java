package com.rubenzu03.beatbank;

import com.rubenzu03.beatbank.application.dto.*;
import com.rubenzu03.beatbank.application.exception.GlobalExceptionHandler;
import com.rubenzu03.beatbank.application.exception.ResourceNotFoundException;
import com.rubenzu03.beatbank.application.port.inbound.ArtistUseCase;
import com.rubenzu03.beatbank.adapter.inbound.rest.ArtistController;
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
class ArtistControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ArtistUseCase artistUseCase;

    @BeforeEach
    void setUp() {
        ArtistController controller = new ArtistController(artistUseCase);
        mockMvc = standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void getAllArtists_ShouldReturnPagedResponse() throws Exception {
        Page<ArtistDto> page = new PageImpl<>(List.of(
                new ArtistDto(1L, "Queen", null, "Rock band")
        ));
        when(artistUseCase.getAllArtists(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/artists"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].name").value("Queen"))
                .andExpect(jsonPath("$.totalItems").value(1));
    }

    @Test
    void getArtistById_WhenExists_ShouldReturnArtist() throws Exception {
        when(artistUseCase.getArtistById(1L)).thenReturn(
                new ArtistDto(1L, "Queen", null, "Rock band")
        );

        mockMvc.perform(get("/api/artists/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Queen"));
    }

    @Test
    void getArtistById_WhenNotExists_ShouldReturn404() throws Exception {
        when(artistUseCase.getArtistById(99L)).thenThrow(new ResourceNotFoundException("Artist", 99L));

        mockMvc.perform(get("/api/artists/99"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Artist with id 99 not found"));
    }

    @Test
    void createArtist_ShouldReturn201() throws Exception {
        when(artistUseCase.createArtist(any(ArtistDto.class))).thenReturn(
                new ArtistDto(1L, "Queen", null, "Rock band")
        );

        mockMvc.perform(post("/api/artists")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Queen\",\"description\":\"Rock band\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Queen"));
    }

    @Test
    void createArtist_WithBlankName_ShouldReturn400() throws Exception {
        mockMvc.perform(post("/api/artists")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void patchArtist_ShouldReturn200() throws Exception {
        when(artistUseCase.patchArtist(eq(1L), any(ArtistPatchDto.class))).thenReturn(
                new ArtistDto(1L, "Updated Name", null, "New desc")
        );

        mockMvc.perform(patch("/api/artists/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Name\",\"description\":\"New desc\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"));
    }

    @Test
    void deleteArtistById_WhenExists_ShouldReturn200() throws Exception {
        doNothing().when(artistUseCase).deleteArtistById(1L);

        mockMvc.perform(delete("/api/artists/1"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteArtistById_WhenNotExists_ShouldReturn404() throws Exception {
        doThrow(new ResourceNotFoundException("Artist", 99L)).when(artistUseCase).deleteArtistById(99L);

        mockMvc.perform(delete("/api/artists/99"))
                .andExpect(status().isNotFound());
    }
}
