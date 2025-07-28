package com.rubenzu03.beatbank.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.rubenzu03.beatbank.application.dto.ArtistDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity(name="artists")
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    private String name;
    private String description;

    @JsonManagedReference
    @ManyToMany(mappedBy = "artists")
    private List<Song> songs;

    public Artist (ArtistDto artistDto){
        this.id = artistDto.id();
        this.name = artistDto.name();
        this.description = artistDto.description();
        this.songs = null;
    }

    public void addSong(Song song) {
        if (this.songs == null) {
            this.songs = List.of(song);
        } else {
            this.songs.add(song);
        }
    }
}
