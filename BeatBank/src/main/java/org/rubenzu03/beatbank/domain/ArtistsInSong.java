package org.rubenzu03.beatbank.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity(name = "ArtistsInSong")
@NoArgsConstructor
@Getter
@Setter
public class ArtistsInSong {
    @Id
    private String id = UUID.randomUUID().toString();

    @ManyToOne
    private Artist artist;

    @ManyToOne
    private Song song;

}
