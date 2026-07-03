package com.rubenzu03.beatbank.application.port.outbound;

import com.rubenzu03.beatbank.application.dto.AlbumDto;
import com.rubenzu03.beatbank.application.dto.ArtistDto;
import com.rubenzu03.beatbank.application.dto.GenreDto;
import com.rubenzu03.beatbank.application.dto.SongDto;
import com.rubenzu03.beatbank.domain.Album;
import com.rubenzu03.beatbank.domain.Artist;
import com.rubenzu03.beatbank.domain.Genre;
import com.rubenzu03.beatbank.domain.Song;

import java.util.List;

public interface DtoMapper {
    SongDto toSongDto(Song song);
    List<SongDto> toSongDtoList(List<Song> songs);
    AlbumDto toAlbumDto(Album album);
    List<AlbumDto> toAlbumDtoList(List<Album> albums);
    ArtistDto toArtistDto(Artist artist);
    List<ArtistDto> toArtistDtoList(List<Artist> artists);
    GenreDto toGenreDto(Genre genre);
    List<GenreDto> toGenreDtoList(List<Genre> genres);
}
