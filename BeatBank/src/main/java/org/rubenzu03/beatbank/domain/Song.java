package org.rubenzu03.beatbank.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.rubenzu03.beatbank.dto.SongDTO;

import java.util.List;
import java.util.UUID;

@Entity(name = "Songs")
@NoArgsConstructor
@Getter
@Setter
public class Song {
    @Id
    private String id = UUID.randomUUID().toString();
    private String name;
    private String duration;
    private String lyrics;

    @ManyToOne
    private Album album;

    @OneToMany
    private List<ArtistsInSong> artistsInSong;

    public Song(SongDTO songDTO) {
        this.name = songDTO.getName();
        this.duration = songDTO.getDuration();
        this.lyrics = songDTO.getLyrics();
    }

    public void addArtistsInSong(ArtistsInSong artistsInSong) {
        this.artistsInSong.add(artistsInSong);
    }

    public void removeArtistsInSong(String artistsInSongId) {
        artistsInSong.removeIf(artistsInSong -> artistsInSong.getId().equals(artistsInSongId));
    }

    public void updateSong(SongDTO songDTO) {
        this.name = songDTO.getName();
        this.duration = songDTO.getDuration();
        this.lyrics = songDTO.getLyrics();
    }

    public void addAlbum(Album album) {
        this.album = album;
    }

    public void removeAlbum() {
        this.album = null;
    }

}
