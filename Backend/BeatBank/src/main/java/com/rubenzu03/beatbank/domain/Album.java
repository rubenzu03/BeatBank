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
@Entity(name = "albums")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String releaseDate;
    private String coverImageUrl;
    private String description;

    @ManyToOne
    private Genre genre;

    @OneToMany(mappedBy = "album", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    private List<Song> songs;

    public Album(String name, String releaseDate, String coverImageUrl, String description, Genre genre) {
        this.name = name;
        this.releaseDate = releaseDate;
        this.coverImageUrl = coverImageUrl;
        this.description = description;
        this.genre = genre;
    }

    public void updateAlbum(String name, String releaseDate, String coverImageUrl, String description, Genre genre) {
        this.name = name;
        this.releaseDate = releaseDate;
        this.coverImageUrl = coverImageUrl;
        this.description = description;
        this.genre = genre;
    }

    public void addSong(Song newSong) {
        if (this.songs == null) {
            this.songs = new ArrayList<>();
        }
        this.songs.add(newSong);
        newSong.setAlbum(this);
    }
}
