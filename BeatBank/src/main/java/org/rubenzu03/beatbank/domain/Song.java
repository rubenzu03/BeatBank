package org.rubenzu03.beatbank.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @OneToMany
    private Album album;


}
