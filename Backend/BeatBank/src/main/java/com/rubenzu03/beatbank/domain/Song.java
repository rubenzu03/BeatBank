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
@Entity(name = "songs")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String duration;

    private Long plays;

    @ManyToOne
    @JoinColumn
    private Album album;

    @ManyToOne
    @JoinColumn
    private Genre genre;

    @ManyToMany
    private List<Artist> artists;

    public Song(String name, String duration, Long plays) {
        this.name = name;
        this.duration = duration;
        this.plays = plays != null ? plays : 0L;
    }

    public void updateSong(String name, String duration, Long plays) {
        this.name = name;
        this.duration = duration;
        this.plays = plays != null ? plays : 0L;
    }

    public void addArtist(Artist artist) {
        if (this.artists == null) {
            this.artists = new ArrayList<>();
        }
        this.artists.add(artist);
    }
}
