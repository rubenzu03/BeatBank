package com.rubenzu03.beatbank.application.service;

import com.rubenzu03.beatbank.application.dto.*;
import com.rubenzu03.beatbank.domain.Album;
import com.rubenzu03.beatbank.domain.Artist;
import com.rubenzu03.beatbank.domain.Genre;
import com.rubenzu03.beatbank.domain.Song;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DtoMapper {

    public SongDto toSongDto(Song song) {
        return new SongDto(
                song.getId(),
                song.getName(),
                song.getDuration(),
                song.getPlays(),
                song.getAlbum() == null ? null : toAlbumDtoSimple(song.getAlbum()),
                song.getArtists() == null ? null : song.getArtists().stream()
                        .map(this::toArtistDtoSimple)
                        .toList()
        );
    }

    public List<SongDto> toSongDtoList(List<Song> songs) {
        return songs.stream().map(this::toSongDto).collect(Collectors.toList());
    }

    private AlbumDto toAlbumDtoSimple(Album album) {
        return new AlbumDto(
                album.getId(),
                album.getName(),
                album.getReleaseDate(),
                album.getCoverImageUrl(),
                album.getDescription(),
                album.getGenre(),
                null
        );
    }

    public AlbumDto toAlbumDto(Album album) {
        return new AlbumDto(
                album.getId(),
                album.getName(),
                album.getReleaseDate(),
                album.getCoverImageUrl(),
                album.getDescription(),
                album.getGenre(),
                album.getSongs() == null ? null : album.getSongs().stream()
                        .map(this::toSongDto)
                        .toList()
        );
    }

    public List<AlbumDto> toAlbumDtoList(List<Album> albums) {
        return albums.stream().map(this::toAlbumDto).collect(Collectors.toList());
    }

    private SongDtoSimple toSongDtoSimple(Song song) {
        return new SongDtoSimple(
                song.getId(),
                song.getName(),
                song.getDuration(),
                song.getPlays()
        );
    }

    public ArtistDto toArtistDto(Artist artist) {
        return new ArtistDto(
                artist.getId(),
                artist.getName(),
                artist.getSongs() == null ? null : artist.getSongs().stream()
                        .map(this::toSongDtoSimple)
                        .toList(),
                artist.getDescription()
        );
    }

    public List<ArtistDto> toArtistDtoList(List<Artist> artists) {
        return artists.stream().map(this::toArtistDto).collect(Collectors.toList());
    }

    private ArtistDtoSimple toArtistDtoSimple(Artist artist) {
        return new ArtistDtoSimple(artist.getId(), artist.getName());
    }

    public GenreDto toGenreDto(Genre genre) {
        return new GenreDto(
                genre.getId(),
                genre.getName(),
                genre.getDescription(),
                genre.getSongs() == null ? null : genre.getSongs().stream()
                        .map(this::toSongDto)
                        .toList()
        );
    }

    public List<GenreDto> toGenreDtoList(List<Genre> genres) {
        return genres.stream().map(this::toGenreDto).toList();
    }
}
