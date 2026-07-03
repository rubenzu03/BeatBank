package com.rubenzu03.beatbank;

import com.rubenzu03.beatbank.application.dto.ArtistDto;
import com.rubenzu03.beatbank.application.dto.SongDto;
import com.rubenzu03.beatbank.application.dto.SongPatchDto;
import com.rubenzu03.beatbank.application.exception.ResourceNotFoundException;
import com.rubenzu03.beatbank.application.port.outbound.DtoMapper;
import com.rubenzu03.beatbank.application.service.SongUseCaseImpl;
import com.rubenzu03.beatbank.domain.Artist;
import com.rubenzu03.beatbank.domain.Song;
import com.rubenzu03.beatbank.domain.port.outbound.AlbumRepository;
import com.rubenzu03.beatbank.domain.port.outbound.ArtistRepository;
import com.rubenzu03.beatbank.domain.port.outbound.SongRepository;
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
class SongUseCaseImplTest {

    @Mock private SongRepository songRepository;
    @Mock private ArtistRepository artistRepository;
    @Mock private AlbumRepository albumRepository;
    @Mock private DtoMapper mapper;

    @InjectMocks private SongUseCaseImpl songUseCase;

    @Captor private ArgumentCaptor<Song> songCaptor;

    private Song createSong(Long id, String name, String duration, Long plays) {
        Song s = new Song();
        s.setId(id);
        s.setName(name);
        s.setDuration(duration);
        s.setPlays(plays);
        return s;
    }

    private SongDto createSongDto(Long id, String name, String duration, Long plays) {
        return new SongDto(id, name, duration, plays, null, null);
    }

    @Test
    void getAllSongs_ShouldReturnPageOfSongDtos() {
        Song song = createSong(1L, "Test Song", "3:45", 100L);
        Page<Song> songPage = new PageImpl<>(List.of(song));
        when(songRepository.findAll(any(Pageable.class))).thenReturn(songPage);
        when(mapper.toSongDto(song)).thenReturn(createSongDto(1L, "Test Song", "3:45", 100L));

        Page<SongDto> result = songUseCase.getAllSongs(Pageable.unpaged());

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().getFirst().name()).isEqualTo("Test Song");
    }

    @Test
    void getSongById_WhenExists_ShouldReturnSongDto() {
        Song song = createSong(1L, "Test Song", null, null);
        when(songRepository.findSongById(1L)).thenReturn(song);
        when(mapper.toSongDto(song)).thenReturn(createSongDto(1L, "Test Song", null, null));

        SongDto result = songUseCase.getSongById(1L);

        assertThat(result).isNotNull();
        assertThat(result.name()).isEqualTo("Test Song");
    }

    @Test
    void getSongById_WhenNotExists_ShouldThrow() {
        when(songRepository.findSongById(1L)).thenReturn(null);

        assertThatThrownBy(() -> songUseCase.getSongById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Song");
    }

    @Test
    void createSong_ShouldSaveAndReturnDto() {
        SongDto input = createSongDto(null, "New Song", "3:45", 0L);
        Song savedSong = createSong(1L, "New Song", "3:45", 0L);
        when(songRepository.save(any(Song.class))).thenReturn(savedSong);
        when(mapper.toSongDto(any(Song.class))).thenReturn(createSongDto(1L, "New Song", "3:45", 0L));

        SongDto result = songUseCase.createSong(input);

        assertThat(result.name()).isEqualTo("New Song");
        verify(songRepository).save(songCaptor.capture());
        assertThat(songCaptor.getValue().getName()).isEqualTo("New Song");
    }

    @Test
    void updateSong_WhenExists_ShouldUpdateAndReturnDto() {
        Song existing = createSong(1L, "Old", "3:00", 10L);
        SongDto update = createSongDto(1L, "Updated", "4:00", 20L);
        when(songRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(songRepository.save(existing)).thenReturn(existing);
        when(mapper.toSongDto(existing)).thenReturn(update);

        SongDto result = songUseCase.updateSong(1L, update);

        assertThat(result.name()).isEqualTo("Updated");
        assertThat(result.duration()).isEqualTo("4:00");
        assertThat(result.plays()).isEqualTo(20L);
    }

    @Test
    void updateSong_WhenNotExists_ShouldThrow() {
        when(songRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> songUseCase.updateSong(1L, createSongDto(1L, "Any", null, null)))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Song");
    }

    @Test
    void patchSong_WhenExists_ShouldPatchFields() {
        Song existing = createSong(1L, "Original", "3:00", 10L);
        SongPatchDto patch = new SongPatchDto("Patched", null, 99L);
        when(songRepository.findSongById(1L)).thenReturn(existing);
        when(songRepository.save(existing)).thenReturn(existing);
        when(mapper.toSongDto(existing)).thenReturn(createSongDto(1L, "Patched", "3:00", 99L));

        SongDto result = songUseCase.patchSong(1L, patch);

        assertThat(result.name()).isEqualTo("Patched");
        assertThat(result.plays()).isEqualTo(99L);
        assertThat(result.duration()).isEqualTo("3:00");
    }

    @Test
    void patchSong_WhenNotExists_ShouldThrow() {
        when(songRepository.findSongById(1L)).thenReturn(null);

        assertThatThrownBy(() -> songUseCase.patchSong(1L, new SongPatchDto("X", null, null)))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Song");
    }

    @Test
    void addArtistToSong_ShouldCreateArtistAndAssociate() {
        Song song = createSong(1L, "Test", "3:00", 0L);
        song.setArtists(new ArrayList<>());
        ArtistDto artistDto = new ArtistDto(null, "New Artist", null, "desc");
        when(songRepository.findSongById(1L)).thenReturn(song);
        when(songRepository.save(song)).thenReturn(song);
        when(mapper.toSongDto(song)).thenReturn(createSongDto(1L, "Test", "3:00", 0L));

        SongDto result = songUseCase.addArtistToSong(1L, artistDto);

        assertThat(result).isNotNull();
        assertThat(song.getArtists()).hasSize(1);
        assertThat(song.getArtists().getFirst().getName()).isEqualTo("New Artist");
        verify(songRepository).save(song);
    }

    @Test
    void deleteArtistFromSong_WhenAssociated_ShouldRemove() {
        Song song = createSong(1L, "Test", "3:00", 0L);
        Artist artist = new Artist("Artist", "desc");
        artist.setId(2L);
        artist.setSongs(new ArrayList<>(List.of(song)));
        song.setArtists(new ArrayList<>(List.of(artist)));

        when(songRepository.findSongById(1L)).thenReturn(song);
        when(artistRepository.findById(2L)).thenReturn(Optional.of(artist));

        songUseCase.deleteArtistFromSong(1L, 2L);

        assertThat(song.getArtists()).isEmpty();
        assertThat(artist.getSongs()).isEmpty();
        verify(songRepository).save(song);
        verify(artistRepository).save(artist);
    }

    @Test
    void deleteArtistFromSong_WhenNotAssociated_ShouldThrow() {
        Song song = createSong(1L, "Test", "3:00", 0L);
        song.setArtists(new ArrayList<>());
        Artist artist = new Artist("Artist", "desc");
        artist.setId(2L);
        artist.setSongs(new ArrayList<>());

        when(songRepository.findSongById(1L)).thenReturn(song);
        when(artistRepository.findById(2L)).thenReturn(Optional.of(artist));

        assertThatThrownBy(() -> songUseCase.deleteArtistFromSong(1L, 2L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void deleteSongById_WhenExists_ShouldDelete() {
        when(songRepository.existsById(1L)).thenReturn(true);

        songUseCase.deleteSongById(1L);

        verify(songRepository).deleteById(1L);
    }

    @Test
    void deleteSongById_WhenNotExists_ShouldThrow() {
        when(songRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> songUseCase.deleteSongById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Song");
    }

    @Test
    void searchSongs_ShouldReturnPageOfResults() {
        Song song = createSong(1L, "Searchable", "3:00", 0L);
        Page<Song> songPage = new PageImpl<>(List.of(song));
        when(songRepository.searchSongs("search", Pageable.unpaged())).thenReturn(songPage);
        when(mapper.toSongDto(song)).thenReturn(createSongDto(1L, "Searchable", "3:00", 0L));

        Page<SongDto> result = songUseCase.searchSongs("search", Pageable.unpaged());

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().getFirst().name()).isEqualTo("Searchable");
    }

    @Test
    void incrementPlays_WhenExists_ShouldIncrement() {
        Song song = createSong(1L, "Test", "3:00", 5L);
        when(songRepository.findSongById(1L)).thenReturn(song);
        when(songRepository.save(song)).thenReturn(song);
        when(mapper.toSongDto(song)).thenReturn(createSongDto(1L, "Test", "3:00", 6L));

        SongDto result = songUseCase.incrementPlays(1L);

        assertThat(result.plays()).isEqualTo(6L);
        assertThat(song.getPlays()).isEqualTo(6L);
    }

    @Test
    void incrementPlays_WhenNotExists_ShouldThrow() {
        when(songRepository.findSongById(1L)).thenReturn(null);

        assertThatThrownBy(() -> songUseCase.incrementPlays(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Song");
    }
}
