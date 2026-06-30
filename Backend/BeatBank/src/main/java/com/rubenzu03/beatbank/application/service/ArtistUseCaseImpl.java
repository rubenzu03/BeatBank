package com.rubenzu03.beatbank.application.service;

import com.rubenzu03.beatbank.application.dto.ArtistDto;
import com.rubenzu03.beatbank.application.port.inbound.ArtistUseCase;
import com.rubenzu03.beatbank.domain.Artist;
import com.rubenzu03.beatbank.domain.port.outbound.ArtistRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArtistUseCaseImpl implements ArtistUseCase {

    private final ArtistRepository artistRepository;

    public ArtistUseCaseImpl(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Override
    public List<ArtistDto> getAllArtists() {
        return artistRepository.findAll().stream()
                .map(ArtistDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public ArtistDto getArtistById(Long id) {
        return artistRepository.findById(id)
                .map(ArtistDto::new)
                .orElse(null);
    }

    @Override
    public ArtistDto createArtist(ArtistDto artistDto){
        Artist artist = new Artist(artistDto);
        artistRepository.save(artist);
        return new ArtistDto(artist);
    }

    @Override
    public void deleteArtistById(Long id) {
        if (artistRepository.existsById(id)) {
            artistRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Artist with id " + id + " does not exist.");
        }
    }
}
