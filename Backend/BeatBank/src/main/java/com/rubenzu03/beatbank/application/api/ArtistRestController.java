package com.rubenzu03.beatbank.application.api;

import com.rubenzu03.beatbank.application.ArtistService;
import com.rubenzu03.beatbank.application.dto.ArtistDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/artists")
public class ArtistRestController {

    private final ArtistService artistService;

    public ArtistRestController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping
    public List<ArtistDto> getAllArtists() {
        return artistService.getAllArtists();
    }
}
