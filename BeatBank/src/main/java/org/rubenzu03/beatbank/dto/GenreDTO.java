package org.rubenzu03.beatbank.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.rubenzu03.beatbank.domain.Genre;

@Getter
@Setter
@NoArgsConstructor
public class GenreDTO {
    private String name;

    public GenreDTO(Genre genre) {
        this.name = genre.getName();
    }


}
