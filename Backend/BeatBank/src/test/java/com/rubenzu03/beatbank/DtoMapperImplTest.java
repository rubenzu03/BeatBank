package com.rubenzu03.beatbank;

import com.rubenzu03.beatbank.adapter.inbound.rest.mapper.DtoMapperImpl;
import com.rubenzu03.beatbank.application.dto.*;
import com.rubenzu03.beatbank.domain.Album;
import com.rubenzu03.beatbank.domain.Artist;
import com.rubenzu03.beatbank.domain.Genre;
import com.rubenzu03.beatbank.domain.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DtoMapperImplTest {

    private DtoMapperImpl mapper;
    private Genre genre;
    private Album album;
    private Artist artist;
    private Song song;

    @BeforeEach
    void setUp() {
        mapper = new DtoMapperImpl();

        genre = new Genre();
        genre.setId(10L);
        genre.setName("Rock");
        genre.setDescription("Rock music");

        album = new Album();
        album.setId(20L);
        album.setName("Test Album");
        album.setReleaseDate("2024-01-01");
        album.setCoverImageUrl("http://cover.jpg");
        album.setDescription("Album desc");
        album.setGenre(genre);

        artist = new Artist();
        artist.setId(30L);
        artist.setName("Queen");
        artist.setDescription("Rock band");

        song = new Song();
        song.setId(1L);
        song.setName("Test Song");
        song.setDuration("3:45");
        song.setPlays(100L);
        song.setAlbum(album);
        song.setArtists(List.of(artist));
        song.setGenre(genre);
    }

    @Test
    void toSongDto_ShouldMapAllFields() {
        SongDto dto = mapper.toSongDto(song);

        assertThat(dto.id()).isEqualTo(1L);
        assertThat(dto.name()).isEqualTo("Test Song");
        assertThat(dto.duration()).isEqualTo("3:45");
        assertThat(dto.plays()).isEqualTo(100L);
        assertThat(dto.album()).isNotNull();
        assertThat(dto.album().id()).isEqualTo(20L);
        assertThat(dto.artists()).hasSize(1);
        assertThat(dto.artists().getFirst().name()).isEqualTo("Queen");
    }

    @Test
    void toSongDto_WithNullRelations_ShouldHandleGracefully() {
        song.setAlbum(null);
        song.setArtists(null);

        SongDto dto = mapper.toSongDto(song);

        assertThat(dto.id()).isEqualTo(1L);
        assertThat(dto.album()).isNull();
        assertThat(dto.artists()).isNull();
    }

    @Test
    void toSongDtoList_ShouldMapList() {
        List<SongDto> dtos = mapper.toSongDtoList(List.of(song));

        assertThat(dtos).hasSize(1);
        assertThat(dtos.getFirst().name()).isEqualTo("Test Song");
    }

    @Test
    void toAlbumDto_ShouldMapAllFields() {
        AlbumDto dto = mapper.toAlbumDto(album);

        assertThat(dto.id()).isEqualTo(20L);
        assertThat(dto.name()).isEqualTo("Test Album");
        assertThat(dto.releaseDate()).isEqualTo("2024-01-01");
        assertThat(dto.coverImageUrl()).isEqualTo("http://cover.jpg");
        assertThat(dto.description()).isEqualTo("Album desc");
        assertThat(dto.genre()).isNotNull();
        assertThat(dto.genre().id()).isEqualTo(10L);
        assertThat(dto.genre().name()).isEqualTo("Rock");
        assertThat(dto.genre().songs()).isNull();
    }

    @Test
    void toAlbumDto_WithNullGenre_ShouldHandleGracefully() {
        album.setGenre(null);

        AlbumDto dto = mapper.toAlbumDto(album);

        assertThat(dto.genre()).isNull();
    }

    @Test
    void toAlbumDto_WithSongs_ShouldIncludeSongs() {
        album.setSongs(List.of(song));

        AlbumDto dto = mapper.toAlbumDto(album);

        assertThat(dto.songs()).hasSize(1);
        assertThat(dto.songs().getFirst().name()).isEqualTo("Test Song");
    }

    @Test
    void toAlbumDtoList_ShouldMapList() {
        List<AlbumDto> dtos = mapper.toAlbumDtoList(List.of(album));

        assertThat(dtos).hasSize(1);
        assertThat(dtos.getFirst().name()).isEqualTo("Test Album");
    }

    @Test
    void toArtistDto_ShouldMapAllFields() {
        ArtistDto dto = mapper.toArtistDto(artist);

        assertThat(dto.id()).isEqualTo(30L);
        assertThat(dto.name()).isEqualTo("Queen");
        assertThat(dto.description()).isEqualTo("Rock band");
    }

    @Test
    void toArtistDto_WithSongs_ShouldIncludeSimpleSongs() {
        artist.setSongs(List.of(song));

        ArtistDto dto = mapper.toArtistDto(artist);

        assertThat(dto.songs()).hasSize(1);
        assertThat(dto.songs().getFirst().name()).isEqualTo("Test Song");
        assertThat(dto.songs().getFirst()).isInstanceOf(SongDtoSimple.class);
    }

    @Test
    void toArtistDto_WithNullSongs_ShouldHandleGracefully() {
        artist.setSongs(null);

        ArtistDto dto = mapper.toArtistDto(artist);

        assertThat(dto.songs()).isNull();
        assertThat(dto.name()).isEqualTo("Queen");
    }

    @Test
    void toArtistDtoList_ShouldMapList() {
        List<ArtistDto> dtos = mapper.toArtistDtoList(List.of(artist));

        assertThat(dtos).hasSize(1);
        assertThat(dtos.getFirst().name()).isEqualTo("Queen");
    }

    @Test
    void toGenreDto_ShouldMapAllFields() {
        GenreDto dto = mapper.toGenreDto(genre);

        assertThat(dto.id()).isEqualTo(10L);
        assertThat(dto.name()).isEqualTo("Rock");
        assertThat(dto.description()).isEqualTo("Rock music");
    }

    @Test
    void toGenreDto_WithSongs_ShouldIncludeSongs() {
        genre.setSongs(List.of(song));

        GenreDto dto = mapper.toGenreDto(genre);

        assertThat(dto.songs()).hasSize(1);
        assertThat(dto.songs().getFirst().name()).isEqualTo("Test Song");
    }

    @Test
    void toGenreDtoList_ShouldMapList() {
        List<GenreDto> dtos = mapper.toGenreDtoList(List.of(genre));

        assertThat(dtos).hasSize(1);
        assertThat(dtos.getFirst().name()).isEqualTo("Rock");
    }
}
