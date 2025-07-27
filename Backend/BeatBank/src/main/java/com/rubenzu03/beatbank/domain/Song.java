package com.rubenzu03.beatbank.domain;

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

    @ManyToOne(cascade = {CascadeType.REMOVE})
    @JoinColumn
    private Album album;

    @ManyToMany
    private List<Artist> artists;

    public Song(SongDto songDto){
        this.name = songDto.name();
        this.duration = songDto.duration();
        if (songDto.album() != null) {
            this.album = new Album(songDto.album());
        } else {
            this.album = null;
        }
        if (songDto.artists() != null) {
            this.artists = songDto.artists().stream()
                .map(Artist::new)
                .toList();
        } else {
            this.artists = null;
        }
    }

    public void updateSong(SongDto songDto) {
        this.name = songDto.name();
        this.duration = songDto.duration();
        handleAlbum(songDto);
        if (songDto.artists() != null) {
            this.artists = songDto.artists().stream()
                .map(Artist::new)
                .toList();
        } else {
            this.artists = null;
        }
    }

    private void handleAlbum(SongDto songDto) {
        if (songDto.album() != null) {
            this.album = new Album(songDto.album());
        }
        else{
            this.album = null;
        }
    }

    public void addArtist(ArtistDto artist){
        this.artists.add(new Artist(artist));
    }
}
