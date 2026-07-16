package com.rubenzu03.beatbank;

import com.rubenzu03.beatbank.application.dto.*;
import com.rubenzu03.beatbank.application.exception.GlobalExceptionHandler;
import com.rubenzu03.beatbank.application.exception.ResourceNotFoundException;
import com.rubenzu03.beatbank.application.port.inbound.AlbumUseCase;
import com.rubenzu03.beatbank.adapter.inbound.rest.AlbumController;
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
class AlbumControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AlbumUseCase albumUseCase;

    @BeforeEach
    void setUp() {
        AlbumController controller = new AlbumController(albumUseCase);
        mockMvc = standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void getAllAlbums_ShouldReturnPagedResponse() throws Exception {
        Page<AlbumDto> page = new PageImpl<>(List.of(
                new AlbumDto(1L, "Test Album", "2024-01-01", null, null, null, null)
        ));
        when(albumUseCase.getAllAlbums(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/albums"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].name").value("Test Album"))
                .andExpect(jsonPath("$.totalItems").value(1));
    }

    @Test
    void getAlbumById_WhenExists_ShouldReturnAlbum() throws Exception {
        when(albumUseCase.getAlbumById(1L)).thenReturn(
                new AlbumDto(1L, "Test Album", "2024-01-01", null, null, null, null)
        );

        mockMvc.perform(get("/api/albums/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Album"));
    }

    @Test
    void getAlbumById_WhenNotExists_ShouldReturn404() throws Exception {
        when(albumUseCase.getAlbumById(99L)).thenThrow(new ResourceNotFoundException("Album", 99L));

        mockMvc.perform(get("/api/albums/99"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Album with id 99 not found"));
    }

    @Test
    void createAlbum_ShouldReturn201() throws Exception {
        when(albumUseCase.createAlbum(any(AlbumDto.class))).thenReturn(
                new AlbumDto(1L, "New Album", "2024-01-01", null, null, null, null)
        );

        mockMvc.perform(post("/api/albums")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New Album\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("New Album"));
    }

    @Test
    void createAlbum_WithBlankName_ShouldReturn400() throws Exception {
        mockMvc.perform(post("/api/albums")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addSongToAlbum_ShouldReturn200() throws Exception {
        when(albumUseCase.addSongToAlbum(eq(1L), any(SongDto.class))).thenReturn(
                new AlbumDto(1L, "Album", null, null, null, null, null)
        );

        mockMvc.perform(post("/api/albums/1/songs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New Song\",\"duration\":\"3:00\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void updateAlbum_ShouldReturn200() throws Exception {
        when(albumUseCase.updateAlbum(eq(1L), any(AlbumDto.class))).thenReturn(
                new AlbumDto(1L, "Updated", "2024-06-01", null, null, null, null)
        );

        mockMvc.perform(put("/api/albums/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated\",\"releaseDate\":\"2024-06-01\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated"));
    }

    @Test
    void patchAlbum_ShouldReturn200() throws Exception {
        when(albumUseCase.patchAlbum(eq(1L), any(AlbumPatchDto.class))).thenReturn(
                new AlbumDto(1L, "Patched", null, "http://img.jpg", null, null, null)
        );

        mockMvc.perform(patch("/api/albums/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Patched\",\"coverImageUrl\":\"http://img.jpg\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Patched"));
    }

    @Test
    void updateAlbumCover_ShouldReturn200() throws Exception {
        when(albumUseCase.updateAlbumCover(eq(1L), any(String.class))).thenReturn(
                new AlbumDto(1L, "Album", null, "http://new-cover.jpg", null, null, null)
        );

        mockMvc.perform(patch("/api/albums/1/cover")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"coverImageUrl\":\"http://new-cover.jpg\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.coverImageUrl").value("http://new-cover.jpg"));
    }

    @Test
    void deleteAlbumById_WhenExists_ShouldReturn200() throws Exception {
        doNothing().when(albumUseCase).deleteAlbumById(1L);

        mockMvc.perform(delete("/api/albums/1"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteAlbumById_WhenNotExists_ShouldReturn404() throws Exception {
        doThrow(new ResourceNotFoundException("Album", 99L)).when(albumUseCase).deleteAlbumById(99L);

        mockMvc.perform(delete("/api/albums/99"))
                .andExpect(status().isNotFound());
    }
}
