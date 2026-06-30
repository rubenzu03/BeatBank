package com.rubenzu03.beatbank.application.service;

import com.rubenzu03.beatbank.application.dto.ArtistDto;
import com.rubenzu03.beatbank.application.exception.ResourceNotFoundException;
import com.rubenzu03.beatbank.application.port.inbound.ArtistUseCase;
import com.rubenzu03.beatbank.application.port.outbound.DtoMapper;
import com.rubenzu03.beatbank.domain.Artist;
import com.rubenzu03.beatbank.domain.port.outbound.ArtistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistUseCaseImpl implements ArtistUseCase {

    private final ArtistRepository artistRepository;
    private final DtoMapper mapper;

    public ArtistUseCaseImpl(ArtistRepository artistRepository, DtoMapper mapper) {
        this.artistRepository = artistRepository;
        this.mapper = mapper;
    }

    @Override
    public List<ArtistDto> getAllArtists() {
        return mapper.toArtistDtoList(artistRepository.findAll());
    }

    @Override
    public ArtistDto getArtistById(Long id) {
        return artistRepository.findById(id)
                .map(mapper::toArtistDto)
                .orElseThrow(() -> new ResourceNotFoundException("Artist", id));
    }

    @Override
    public ArtistDto createArtist(ArtistDto artistDto){
        Artist artist = new Artist(artistDto.name(), artistDto.description());
        artistRepository.save(artist);
        return mapper.toArtistDto(artist);
    }

    @Override
    public void deleteArtistById(Long id) {
        if (!artistRepository.existsById(id)) {
            throw new ResourceNotFoundException("Artist", id);
        }
        artistRepository.deleteById(id);
    }
}
