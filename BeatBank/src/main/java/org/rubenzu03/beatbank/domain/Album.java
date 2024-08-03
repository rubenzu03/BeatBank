package org.rubenzu03.beatbank.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.rubenzu03.beatbank.dto.AlbumDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity(name = "Albums")
@NoArgsConstructor
@Getter
@Setter
public class Album {
    @Id
    private String id = UUID.randomUUID().toString();
    private String name;
    private LocalDate releaseDate;

    @OneToMany
    private List<Song> songs;

    @ManyToOne
    private Artist artist;

    @OneToMany
    private List<Genre> genres;

    public Album(AlbumDTO albumDTO) {
        this.name = albumDTO.getName();
        this.releaseDate = albumDTO.getReleaseDate();
    }

    public void addSong(Song song) {
        this.songs.add(song);
    }

    public void removeSong(String songId) {
        songs.removeIf(song -> song.getId().equals(songId));
    }

    public void addGenre(Genre genre) {
        this.genres.add(genre);
    }

    public void removeGenre(String genreId) {
        genres.removeIf(genre -> genre.getId().equals(genreId));
    }

    public void updateAlbum(AlbumDTO albumDTO) {
        this.name = albumDTO.getName();
        this.releaseDate = albumDTO.getReleaseDate();
    }




}
