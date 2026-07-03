package com.rubenzu03.beatbank;

import com.rubenzu03.beatbank.application.dto.ArtistDto;
import com.rubenzu03.beatbank.application.dto.ArtistPatchDto;
import com.rubenzu03.beatbank.application.exception.ResourceNotFoundException;
import com.rubenzu03.beatbank.application.port.outbound.DtoMapper;
import com.rubenzu03.beatbank.application.service.ArtistUseCaseImpl;
import com.rubenzu03.beatbank.domain.Artist;
import com.rubenzu03.beatbank.domain.port.outbound.ArtistRepository;
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
class ArtistUseCaseImplTest {

    @Mock private ArtistRepository artistRepository;
    @Mock private DtoMapper mapper;

    @InjectMocks private ArtistUseCaseImpl artistUseCase;

    @Captor private ArgumentCaptor<Artist> artistCaptor;

    private Artist createArtist(Long id, String name, String description) {
        Artist a = new Artist();
        a.setId(id);
        a.setName(name);
        a.setDescription(description);
        return a;
    }

    private ArtistDto createArtistDto(Long id, String name, String description) {
        return new ArtistDto(id, name, null, description);
    }

    @Test
    void getAllArtists_ShouldReturnPageOfArtistDtos() {
        Artist artist = createArtist(1L, "Queen", "Rock band");
        Page<Artist> artistPage = new PageImpl<>(List.of(artist));
        when(artistRepository.findAll(any(Pageable.class))).thenReturn(artistPage);
        when(mapper.toArtistDto(artist)).thenReturn(createArtistDto(1L, "Queen", "Rock band"));

        Page<ArtistDto> result = artistUseCase.getAllArtists(Pageable.unpaged());

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().getFirst().name()).isEqualTo("Queen");
    }

    @Test
    void getArtistById_WhenExists_ShouldReturnArtistDto() {
        Artist artist = createArtist(1L, "Queen", "Rock band");
        when(artistRepository.findById(1L)).thenReturn(Optional.of(artist));
        when(mapper.toArtistDto(artist)).thenReturn(createArtistDto(1L, "Queen", "Rock band"));

        ArtistDto result = artistUseCase.getArtistById(1L);

        assertThat(result.name()).isEqualTo("Queen");
        assertThat(result.description()).isEqualTo("Rock band");
    }

    @Test
    void getArtistById_WhenNotExists_ShouldThrow() {
        when(artistRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> artistUseCase.getArtistById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Artist");
    }

    @Test
    void createArtist_ShouldSaveAndReturnDto() {
        ArtistDto input = createArtistDto(null, "Queen", "Rock band");
        Artist saved = createArtist(1L, "Queen", "Rock band");
        when(artistRepository.save(any(Artist.class))).thenReturn(saved);
        when(mapper.toArtistDto(any(Artist.class))).thenReturn(createArtistDto(1L, "Queen", "Rock band"));

        ArtistDto result = artistUseCase.createArtist(input);

        assertThat(result.name()).isEqualTo("Queen");
        verify(artistRepository).save(artistCaptor.capture());
        assertThat(artistCaptor.getValue().getName()).isEqualTo("Queen");
        assertThat(artistCaptor.getValue().getDescription()).isEqualTo("Rock band");
    }

    @Test
    void patchArtist_WhenExists_ShouldPatchAllFields() {
        Artist existing = createArtist(1L, "Old", "Old desc");
        ArtistPatchDto patch = new ArtistPatchDto("New Name", "New desc");
        when(artistRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(artistRepository.save(existing)).thenReturn(existing);
        when(mapper.toArtistDto(existing)).thenReturn(createArtistDto(1L, "New Name", "New desc"));

        ArtistDto result = artistUseCase.patchArtist(1L, patch);

        assertThat(result.name()).isEqualTo("New Name");
        assertThat(result.description()).isEqualTo("New desc");
    }

    @Test
    void patchArtist_WhenExists_ShouldPatchPartialFields() {
        Artist existing = createArtist(1L, "Old", "Old desc");
        ArtistPatchDto patch = new ArtistPatchDto("New Name", null);
        when(artistRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(artistRepository.save(existing)).thenReturn(existing);
        when(mapper.toArtistDto(existing)).thenReturn(createArtistDto(1L, "New Name", "Old desc"));

        ArtistDto result = artistUseCase.patchArtist(1L, patch);

        assertThat(result.name()).isEqualTo("New Name");
        assertThat(result.description()).isEqualTo("Old desc");
    }

    @Test
    void patchArtist_WhenNotExists_ShouldThrow() {
        when(artistRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> artistUseCase.patchArtist(1L, new ArtistPatchDto("X", null)))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Artist");
    }

    @Test
    void deleteArtistById_WhenExists_ShouldDelete() {
        when(artistRepository.existsById(1L)).thenReturn(true);

        artistUseCase.deleteArtistById(1L);

        verify(artistRepository).deleteById(1L);
    }

    @Test
    void deleteArtistById_WhenNotExists_ShouldThrow() {
        when(artistRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> artistUseCase.deleteArtistById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Artist");
    }
}
