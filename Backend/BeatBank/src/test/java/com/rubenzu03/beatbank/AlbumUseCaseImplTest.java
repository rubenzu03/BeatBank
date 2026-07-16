package com.rubenzu03.beatbank;

import com.rubenzu03.beatbank.application.dto.AlbumDto;
import com.rubenzu03.beatbank.application.dto.AlbumPatchDto;
import com.rubenzu03.beatbank.application.dto.GenreDto;
import com.rubenzu03.beatbank.application.dto.SongDto;
import com.rubenzu03.beatbank.application.exception.ResourceNotFoundException;
import com.rubenzu03.beatbank.application.port.outbound.DtoMapper;
import com.rubenzu03.beatbank.application.service.AlbumUseCaseImpl;
import com.rubenzu03.beatbank.domain.Album;
import com.rubenzu03.beatbank.domain.Genre;
import com.rubenzu03.beatbank.domain.Song;
import com.rubenzu03.beatbank.domain.port.outbound.AlbumRepository;
import com.rubenzu03.beatbank.domain.port.outbound.GenreRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlbumUseCaseImplTest {

    @Mock private AlbumRepository albumRepository;
    @Mock private GenreRepository genreRepository;
    @Mock private DtoMapper mapper;

    @InjectMocks private AlbumUseCaseImpl albumUseCase;

    @Captor private ArgumentCaptor<Album> albumCaptor;

    private Genre createGenre(Long id, String name) {
        Genre g = new Genre();
        g.setId(id);
        g.setName(name);
        return g;
    }

    private Album createAlbum(Long id, String name, Genre genre) {
        Album a = new Album();
        a.setId(id);
        a.setName(name);
        a.setGenre(genre);
        return a;
    }

    private AlbumDto createAlbumDto(Long id, String name, GenreDto genreDto) {
        return new AlbumDto(id, name, null, null, null, genreDto, null);
    }

    @Test
    void getAllAlbums_ShouldReturnPageOfAlbumDtos() {
        Album album = createAlbum(1L, "Test Album", null);
        Page<Album> albumPage = new PageImpl<>(List.of(album));
        when(albumRepository.findAll(any(Pageable.class))).thenReturn(albumPage);
        when(mapper.toAlbumDto(album)).thenReturn(createAlbumDto(1L, "Test Album", null));

        Page<AlbumDto> result = albumUseCase.getAllAlbums(Pageable.unpaged());

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().getFirst().name()).isEqualTo("Test Album");
    }

    @Test
    void getAlbumById_WhenExists_ShouldReturnAlbumDto() {
        Album album = createAlbum(1L, "Test Album", null);
        when(albumRepository.findById(1L)).thenReturn(Optional.of(album));
        when(mapper.toAlbumDto(album)).thenReturn(createAlbumDto(1L, "Test Album", null));

        AlbumDto result = albumUseCase.getAlbumById(1L);

        assertThat(result.name()).isEqualTo("Test Album");
    }

    @Test
    void getAlbumById_WhenNotExists_ShouldThrow() {
        when(albumRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> albumUseCase.getAlbumById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Album");
    }

    @Test
    void createAlbum_WithoutGenre_ShouldSave() {
        AlbumDto input = createAlbumDto(null, "New Album", null);
        Album saved = createAlbum(1L, "New Album", null);
        when(albumRepository.save(any(Album.class))).thenReturn(saved);
        when(mapper.toAlbumDto(any(Album.class))).thenReturn(createAlbumDto(1L, "New Album", null));

        AlbumDto result = albumUseCase.createAlbum(input);

        assertThat(result.name()).isEqualTo("New Album");
        verify(albumRepository).save(albumCaptor.capture());
        assertThat(albumCaptor.getValue().getName()).isEqualTo("New Album");
        assertThat(albumCaptor.getValue().getGenre()).isNull();
    }

    @Test
    void createAlbum_WithGenre_ShouldResolveAndSave() {
        Genre genre = createGenre(10L, "Rock");
        GenreDto genreDto = new GenreDto(10L, "Rock", null, null);
        AlbumDto input = createAlbumDto(null, "New Album", genreDto);
        Album saved = createAlbum(1L, "New Album", genre);
        when(genreRepository.findById(10L)).thenReturn(Optional.of(genre));
        when(albumRepository.save(any(Album.class))).thenReturn(saved);
        when(mapper.toAlbumDto(any(Album.class))).thenReturn(createAlbumDto(1L, "New Album", genreDto));

        AlbumDto result = albumUseCase.createAlbum(input);

        assertThat(result.name()).isEqualTo("New Album");
        verify(albumRepository).save(albumCaptor.capture());
        assertThat(albumCaptor.getValue().getGenre()).isNotNull();
        assertThat(albumCaptor.getValue().getGenre().getName()).isEqualTo("Rock");
    }

    @Test
    void createAlbum_WithGenre_WhenGenreNotFound_ShouldThrow() {
        GenreDto genreDto = new GenreDto(99L, "Unknown", null, null);
        AlbumDto input = createAlbumDto(null, "Album", genreDto);
        when(genreRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> albumUseCase.createAlbum(input))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Genre");
    }

    @Test
    void updateAlbum_WhenExists_ShouldUpdate() {
        Genre genre = createGenre(10L, "Rock");
        GenreDto genreDto = new GenreDto(10L, "Rock", null, null);
        Album existing = createAlbum(1L, "Old", null);
        AlbumDto update = createAlbumDto(1L, "Updated", genreDto);
        when(albumRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(genreRepository.findById(10L)).thenReturn(Optional.of(genre));
        when(albumRepository.save(existing)).thenReturn(existing);
        when(mapper.toAlbumDto(existing)).thenReturn(update);

        AlbumDto result = albumUseCase.updateAlbum(1L, update);

        assertThat(result.name()).isEqualTo("Updated");
        assertThat(existing.getGenre()).isNotNull();
        assertThat(existing.getGenre().getName()).isEqualTo("Rock");
    }

    @Test
    void updateAlbum_WhenNotExists_ShouldThrow() {
        when(albumRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> albumUseCase.updateAlbum(1L, createAlbumDto(null, "Any", null)))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Album");
    }

    @Test
    void patchAlbum_WhenExists_ShouldPatchFields() {
        Album existing = createAlbum(1L, "Original", null);
        AlbumPatchDto patch = new AlbumPatchDto("Patched", "2024-01-01", null, null);
        when(albumRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(albumRepository.save(existing)).thenReturn(existing);
        when(mapper.toAlbumDto(existing)).thenReturn(createAlbumDto(1L, "Patched", null));

        AlbumDto result = albumUseCase.patchAlbum(1L, patch);

        assertThat(result.name()).isEqualTo("Patched");
        assertThat(existing.getReleaseDate()).isEqualTo("2024-01-01");
    }

    @Test
    void patchAlbum_WhenNotExists_ShouldThrow() {
        when(albumRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> albumUseCase.patchAlbum(1L, new AlbumPatchDto("X", null, null, null)))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Album");
    }

    @Test
    void addSongToAlbum_WhenExists_ShouldAddSong() {
        Album album = createAlbum(1L, "Album", null);
        SongDto songDto = new SongDto(null, "New Song", "3:45", 0L, null, null);
        when(albumRepository.findById(1L)).thenReturn(Optional.of(album));
        when(albumRepository.save(album)).thenReturn(album);
        when(mapper.toAlbumDto(album)).thenReturn(createAlbumDto(1L, "Album", null));

        AlbumDto result = albumUseCase.addSongToAlbum(1L, songDto);

        assertThat(result).isNotNull();
        assertThat(album.getSongs()).isNotNull();
        assertThat(album.getSongs()).hasSize(1);
        assertThat(album.getSongs().getFirst().getName()).isEqualTo("New Song");
    }

    @Test
    void addSongToAlbum_WhenNotExists_ShouldThrow() {
        when(albumRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> albumUseCase.addSongToAlbum(1L, new SongDto(null, "S", null, null, null, null)))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Album");
    }

    @Test
    void updateAlbumCover_WhenExists_ShouldUpdate() {
        Album existing = createAlbum(1L, "Album", null);
        when(albumRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(albumRepository.save(existing)).thenReturn(existing);
        when(mapper.toAlbumDto(existing)).thenReturn(createAlbumDto(1L, "Album", null));

        AlbumDto result = albumUseCase.updateAlbumCover(1L, "http://new-cover.jpg");

        assertThat(result).isNotNull();
        assertThat(existing.getCoverImageUrl()).isEqualTo("http://new-cover.jpg");
    }

    @Test
    void updateAlbumCover_WhenNotExists_ShouldThrow() {
        when(albumRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> albumUseCase.updateAlbumCover(1L, "http://img.jpg"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Album");
    }

    @Test
    void deleteAlbumById_WhenExists_ShouldDelete() {
        when(albumRepository.existsById(1L)).thenReturn(true);

        albumUseCase.deleteAlbumById(1L);

        verify(albumRepository).deleteById(1L);
    }

    @Test
    void deleteAlbumById_WhenNotExists_ShouldThrow() {
        when(albumRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> albumUseCase.deleteAlbumById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Album");
    }
}
