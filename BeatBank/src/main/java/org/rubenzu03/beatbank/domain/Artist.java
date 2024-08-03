package org.rubenzu03.beatbank.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.rubenzu03.beatbank.dto.ArtistDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity(name = "Artists")
@NoArgsConstructor
@Getter
@Setter
public class Artist {
    @Id
    private String id = UUID.randomUUID().toString();

    private String name;
    private String country;
    private String description;
    private LocalDate birthDate;

    @OneToMany
    private List<Album> albums;

    @OneToMany
    private List<ArtistsInSong> artistsInSong;

    public Artist(ArtistDTO artistDTO) {
        this.name = artistDTO.getName();
        this.country = artistDTO.getCountry();
        this.description = artistDTO.getDescription();
        this.birthDate = artistDTO.getBirthDate();
    }

    public void addAlbum(Album album) {
        this.albums.add(album);
    }

    public void removeAlbum(String albumId) {
        albums.removeIf(album -> album.getId().equals(albumId));
    }

    public void addArtistsInSong(ArtistsInSong artistsInSong) {
        this.artistsInSong.add(artistsInSong);
    }

    public void removeArtistsInSong(String artistsInSongId) {
        artistsInSong.removeIf(artistsInSong -> artistsInSong.getId().equals(artistsInSongId));
    }




}
