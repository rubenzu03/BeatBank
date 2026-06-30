package com.rubenzu03.beatbank.adapter.inbound.rest;

import com.rubenzu03.beatbank.application.dto.ArtistDto;
import com.rubenzu03.beatbank.application.port.inbound.ArtistUseCase;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/artists")
public class ArtistController {

    private final ArtistUseCase artistUseCase;

    public ArtistController(ArtistUseCase artistUseCase) {
        this.artistUseCase = artistUseCase;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ArtistDto> getAllArtists() {
        return artistUseCase.getAllArtists();
    }

    @Transactional
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ArtistDto createArtist(@RequestBody ArtistDto artistDto) {
        return artistUseCase.createArtist(artistDto);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteArtistById(@RequestParam Long id) {
        artistUseCase.deleteArtistById(id);
    }
}
