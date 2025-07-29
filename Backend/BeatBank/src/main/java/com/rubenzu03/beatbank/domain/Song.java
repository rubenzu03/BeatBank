package com.rubenzu03.beatbank.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.rubenzu03.beatbank.application.dto.ArtistDto;
import com.rubenzu03.beatbank.application.dto.SongDto;
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

    private Long plays;

    @ManyToOne(cascade = {CascadeType.REMOVE})
    @JoinColumn
    private Album album;

    @ManyToMany
    private List<Artist> artists;

    public Song(SongDto songDto) {
        this.name = songDto.name();
        this.duration = songDto.duration();
        this.plays = songDto.plays() != null ? songDto.plays() : 0L;
        if (songDto.album() != null) {
            this.album = new Album();
            this.album.setId(songDto.album().id());
        } else {
            this.album = null;
        }
        this.artists = null;
    }

    public void updateSong(SongDto songDto) {
        this.name = songDto.name();
        this.duration = songDto.duration();
        if (songDto.album() != null) {
            this.album = new Album();
            this.album.setId(songDto.album().id());
        } else {
            this.album = null;
        }
        this.plays = songDto.plays() != null ? songDto.plays() : 0L;
        this.artists = null;
    }

    public void addArtist(ArtistDto artist) {
        this.artists.add(new Artist(artist));
    }

    public void setGenre(Genre genre) {
        if (this.album != null) {
            this.album.setGenre(genre);
        } else {
            throw new IllegalStateException("Cannot set genre for a song without an album");
        }
    }
}
