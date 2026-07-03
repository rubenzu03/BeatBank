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
@Entity(name = "artists")
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @ManyToMany(mappedBy = "artists")
    private List<Song> songs;

    public Artist(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void addSong(Song song) {
        if (this.songs == null) {
            this.songs = new ArrayList<>();
        }
        this.songs.add(song);
    }
}
