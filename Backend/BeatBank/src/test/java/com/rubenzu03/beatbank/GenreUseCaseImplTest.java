package com.rubenzu03.beatbank;

import com.rubenzu03.beatbank.application.dto.GenreDto;
import com.rubenzu03.beatbank.application.dto.GenrePatchDto;
import com.rubenzu03.beatbank.application.exception.ResourceNotFoundException;
import com.rubenzu03.beatbank.application.port.outbound.DtoMapper;
import com.rubenzu03.beatbank.application.service.GenreUseCaseImpl;
import com.rubenzu03.beatbank.domain.Genre;
import com.rubenzu03.beatbank.domain.Song;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GenreUseCaseImplTest {

    @Mock private GenreRepository genreRepository;
    @Mock private DtoMapper mapper;

    @InjectMocks private GenreUseCaseImpl genreUseCase;

    @Captor private ArgumentCaptor<Genre> genreCaptor;

    private Genre createGenre(Long id, String name, String description) {
        Genre g = new Genre();
        g.setId(id);
        g.setName(name);
        g.setDescription(description);
        return g;
    }

    private GenreDto createGenreDto(Long id, String name, String description) {
        return new GenreDto(id, name, description, null);
    }

    @Test
    void getAllGenres_ShouldReturnPageOfGenreDtos() {
        Genre genre = createGenre(1L, "Rock", "Rock music");
        Page<Genre> genrePage = new PageImpl<>(List.of(genre));
        when(genreRepository.findAll(any(Pageable.class))).thenReturn(genrePage);
        when(mapper.toGenreDto(genre)).thenReturn(createGenreDto(1L, "Rock", "Rock music"));

        Page<GenreDto> result = genreUseCase.getAllGenres(Pageable.unpaged());

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().getFirst().name()).isEqualTo("Rock");
    }

    @Test
    void getGenreById_WhenExists_ShouldReturnGenreDto() {
        Genre genre = createGenre(1L, "Rock", "Rock music");
        when(genreRepository.findById(1L)).thenReturn(Optional.of(genre));
        when(mapper.toGenreDto(genre)).thenReturn(createGenreDto(1L, "Rock", "Rock music"));

        GenreDto result = genreUseCase.getGenreById(1L);

        assertThat(result.name()).isEqualTo("Rock");
    }

    @Test
    void getGenreById_WhenNotExists_ShouldThrow() {
        when(genreRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> genreUseCase.getGenreById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Genre");
    }

    @Test
    void createGenre_ShouldSaveAndReturnDto() {
        GenreDto input = createGenreDto(null, "Rock", "Rock music");
        Genre saved = createGenre(1L, "Rock", "Rock music");
        when(genreRepository.save(any(Genre.class))).thenReturn(saved);
        when(mapper.toGenreDto(any(Genre.class))).thenReturn(createGenreDto(1L, "Rock", "Rock music"));

        GenreDto result = genreUseCase.createGenre(input);

        assertThat(result.name()).isEqualTo("Rock");
        verify(genreRepository).save(genreCaptor.capture());
        assertThat(genreCaptor.getValue().getName()).isEqualTo("Rock");
        assertThat(genreCaptor.getValue().getDescription()).isEqualTo("Rock music");
    }

    @Test
    void patchGenre_WhenExists_ShouldPatchAllFields() {
        Genre existing = createGenre(1L, "Old", "Old desc");
        GenrePatchDto patch = new GenrePatchDto("New Genre", "New desc");
        when(genreRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(genreRepository.save(existing)).thenReturn(existing);
        when(mapper.toGenreDto(existing)).thenReturn(createGenreDto(1L, "New Genre", "New desc"));

        GenreDto result = genreUseCase.patchGenre(1L, patch);

        assertThat(result.name()).isEqualTo("New Genre");
        assertThat(result.description()).isEqualTo("New desc");
    }

    @Test
    void patchGenre_WhenExists_ShouldPatchPartial() {
        Genre existing = createGenre(1L, "Old", "Old desc");
        GenrePatchDto patch = new GenrePatchDto("New Name", null);
        when(genreRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(genreRepository.save(existing)).thenReturn(existing);
        when(mapper.toGenreDto(existing)).thenReturn(createGenreDto(1L, "New Name", "Old desc"));

        GenreDto result = genreUseCase.patchGenre(1L, patch);

        assertThat(result.name()).isEqualTo("New Name");
        assertThat(result.description()).isEqualTo("Old desc");
    }

    @Test
    void patchGenre_WhenNotExists_ShouldThrow() {
        when(genreRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> genreUseCase.patchGenre(1L, new GenrePatchDto("X", null)))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Genre");
    }

    @Test
    void deleteGenreById_WhenExists_ShouldOrphanSongsAndDelete() {
        Genre genre = createGenre(1L, "Rock", "Rock");
        Song song1 = new Song();
        song1.setId(1L);
        song1.setGenre(genre);
        Song song2 = new Song();
        song2.setId(2L);
        song2.setGenre(genre);
        List<Song> songs = new ArrayList<>(List.of(song1, song2));
        genre.setSongs(songs);

        when(genreRepository.findById(1L)).thenReturn(Optional.of(genre));

        genreUseCase.deleteGenreById(1L);

        assertThat(song1.getGenre()).isNull();
        assertThat(song2.getGenre()).isNull();
        assertThat(genre.getSongs()).isEmpty();
        verify(genreRepository).deleteById(1L);
    }

    @Test
    void deleteGenreById_WhenNotExists_ShouldThrow() {
        when(genreRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> genreUseCase.deleteGenreById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Genre");
    }
}
