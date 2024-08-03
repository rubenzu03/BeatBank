package org.rubenzu03.beatbank.dto;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.rubenzu03.beatbank.domain.Song;

@NoArgsConstructor
@Getter
@Setter
public class SongDTO {
    private String name;
    private String duration;
    private String lyrics;

    public SongDTO(Song song){
        this.name = song.getName();
        this.duration = song.getDuration();
        this.lyrics = song.getLyrics();
    }
}
