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
        this.id = artistDto.getId();
        this.name = artistDto.getName();
        this.description = artistDto.getDescription();
        if (artistDto.getSongs() != null) {
            this.songs = artistDto.getSongs().stream()
                .map(Song::new)
                .toList();
        } else {
            this.songs = null;
        }
    }
}
