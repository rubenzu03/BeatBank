package com.rubenzu03.beatbank.domain;

import com.rubenzu03.beatbank.SongDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private String duration; // Duration in format HH:MM:SS

    @ManyToOne(cascade = {CascadeType.REMOVE})
    @JoinColumn
    private Album album;

    @ManyToMany
    private List<Artist> artists;

    public Song(SongDto songDto){
        this.name = songDto.name();
        this.duration = songDto.duration();
        this.album = songDto.album();
        this.artists = songDto.artists();
    }
}
