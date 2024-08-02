package org.rubenzu03.beatbank.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity(name = "Genres")
@NoArgsConstructor
@Getter
@Setter
public class Genre {
    @Id
    private String id = UUID.randomUUID().toString();
    private String name;

    @OneToMany
    private List<Album> albums;

    public Genre(GenreDTO genreDTO) {
        this.name = genreDTO.getName();
    }

    public void addAlbum(Album album) {
        this.albums.add(album);
    }

    public void removeAlbum(String albumId) {
        albums.removeIf(album -> album.getId().equals(albumId));
    }

    public void updateGenre(GenreDTO genreDTO) {
        this.name = genreDTO.getName();
    }
}
