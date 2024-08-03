package org.rubenzu03.beatbank.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.rubenzu03.beatbank.domain.Artist;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class ArtistDTO {
    private String name;
    private String country;
    private String description;
    private LocalDate birthDate;

    public ArtistDTO(Artist artist){
        this.name = artist.getName();
        this.country = artist.getCountry();
        this.description = artist.getDescription();
        this.birthDate = artist.getBirthDate();
    }



}
