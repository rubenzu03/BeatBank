package org.rubenzu03.beatbank.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import org.rubenzu03.beatbank.domain.Album;

@NoArgsConstructor
@Getter
@Setter
public class AlbumDTO {
    private String name;
    private LocalDate releaseDate;

    public AlbumDTO(Album album){
        this.name = album.getName();
        this.releaseDate = album.getReleaseDate();
    }


}
