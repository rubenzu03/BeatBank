package com.rubenzu03.beatbank.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.rubenzu03.beatbank.application.dto.AlbumDto;
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
    @Column(nullable = false)
    private Long id;

    private String name;

    private String releaseDate; // Formato: DD-MM-YYYY
    private String coverImageUrl;
    private String description;

    @ManyToOne
    private Genre genre;

    @OneToMany (mappedBy = "album", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    private List<Song> songs;

    public Album(AlbumDto albumDto) {
        this.name = albumDto.name();
        this.releaseDate = albumDto.releaseDate();
        this.coverImageUrl = albumDto.coverImageUrl();
        this.genre = albumDto.genre();
        this.description = albumDto.description();
        this.songs = albumDto.songs() == null ? null : albumDto.songs().stream().map(Song::new).toList();
    }

    public void updateAlbum(AlbumDto albumDto) {
        this.name = albumDto.name();
        this.releaseDate = albumDto.releaseDate();
        this.coverImageUrl = albumDto.coverImageUrl();
        this.genre = albumDto.genre();
        this.description = albumDto.description();
        this.songs = albumDto.songs() == null ? null : albumDto.songs().stream().map(Song::new).toList();
    }

    public void addSong(Song newSong) {
        if (this.songs == null) {
            this.songs = new ArrayList<>();
        }
        this.songs.add(newSong);
        newSong.setAlbum(this);
    }
}
