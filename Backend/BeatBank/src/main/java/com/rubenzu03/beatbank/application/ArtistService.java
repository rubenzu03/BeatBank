package com.rubenzu03.beatbank.application;

import com.rubenzu03.beatbank.application.dto.ArtistDto;
import com.rubenzu03.beatbank.domain.Artist;
import com.rubenzu03.beatbank.persistence.ArtistRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;

    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public List<ArtistDto> getAllArtists() {
        return artistRepository.findAll().stream()
                .map(ArtistDto::new)
                .collect(Collectors.toList());
    }

    public ArtistDto getArtistById(Long id) {
        return artistRepository.findById(id)
                .map(ArtistDto::new)
                .orElse(null);
    }

    public void createArtist(ArtistDto artistDto){
        artistRepository.save(new Artist(artistDto));
    }

    public void deleteArtistById(Long id) {
        if (artistRepository.existsById(id)) {
            artistRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Artist with id " + id + " does not exist.");
        }
    }
}
