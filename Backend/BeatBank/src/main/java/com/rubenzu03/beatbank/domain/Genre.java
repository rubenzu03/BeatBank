package com.rubenzu03.beatbank.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "genres")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @OneToMany(mappedBy = "genre")
    private List<Song> songs;

    public Genre(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void updateGenre(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void addSongToGenre(Song newSong) {
        if (this.songs == null) {
            this.songs = new ArrayList<>();
        }
        this.songs.add(newSong);
        newSong.setGenre(this);
    }
}
