package com.rubenzu03.beatbank.domain;

import com.rubenzu03.beatbank.application.dto.GenreDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity(name="genres")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @OneToMany
    private List<Song> songs;

    public Genre(GenreDto genreDto) {
        this.name = genreDto.name();
        this.description = genreDto.description();
        this.songs = genreDto.songs() == null ? null : genreDto.songs().stream().map(Song::new).toList();
    }

    public void updateGenre(GenreDto genreDto) {
        this.name = genreDto.name();
        this.description = genreDto.description();
        this.songs = genreDto.songs() == null ? null : genreDto.songs().stream().map(Song::new).toList();
    }

    public void addSongToGenre(Song newSong) {
        if (this.songs == null) {
            this.songs = List.of(newSong);
        } else {
            this.songs.add(newSong);
        }
        newSong.setGenre(this);
    }
}
