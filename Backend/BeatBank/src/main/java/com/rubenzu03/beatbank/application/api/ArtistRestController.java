package com.rubenzu03.beatbank.application.api;

import com.rubenzu03.beatbank.application.ArtistService;
import com.rubenzu03.beatbank.application.dto.ArtistDto;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artists")
public class ArtistRestController {

    private final ArtistService artistService;

    public ArtistRestController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ArtistDto> getAllArtists() {
        return artistService.getAllArtists();
    }

    @Transactional
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ArtistDto createArtist(@RequestBody ArtistDto artistDto) {
        return artistService.createArtist(artistDto);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteArtistById(@RequestParam Long id) {
        artistService.deleteArtistById(id);
    }


}
