package com.rubenzu03.beatbank.domain;

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

    @ManyToMany(mappedBy = "artists")
    private List<Song> songs;

    private String description;

    public Artist (ArtistDto artistDto){
        this.id = artistDto.id();
        this.name = artistDto.name();
        this.description = artistDto.description();
        if (artistDto.songs() != null) {
            this.songs = artistDto.songs().stream()
                .map(Song::new)
                .toList();
        } else {
            this.songs = null;
        }
    }
}
