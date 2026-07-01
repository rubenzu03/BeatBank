package com.rubenzu03.beatbank.application.service;

import com.rubenzu03.beatbank.application.dto.GenreDto;
import com.rubenzu03.beatbank.application.dto.GenrePatchDto;
import com.rubenzu03.beatbank.application.exception.ResourceNotFoundException;
import com.rubenzu03.beatbank.application.port.inbound.GenreUseCase;
import com.rubenzu03.beatbank.application.port.outbound.DtoMapper;
import com.rubenzu03.beatbank.domain.Genre;
import com.rubenzu03.beatbank.domain.port.outbound.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreUseCaseImpl implements GenreUseCase {

    private final GenreRepository genreRepository;
    private final DtoMapper mapper;

    public GenreUseCaseImpl(GenreRepository genreRepository, DtoMapper mapper) {
        this.genreRepository = genreRepository;
        this.mapper = mapper;
    }

    @Override
    public List<GenreDto> getAllGenres() {
        return mapper.toGenreDtoList(genreRepository.findAll());
    }

    @Override
    public GenreDto getGenreById(Long id) {
        return genreRepository.findById(id)
                .map(mapper::toGenreDto)
                .orElseThrow(() -> new ResourceNotFoundException("Genre", id));
    }

    @Override
    public GenreDto createGenre(GenreDto genreDto) {
        Genre genre = new Genre(genreDto.name(), genreDto.description());
        genreRepository.save(genre);
        return mapper.toGenreDto(genre);
    }

    @Override
    public GenreDto patchGenre(Long id, GenrePatchDto patch) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre", id));
        if (patch.name() != null) genre.setName(patch.name());
        if (patch.description() != null) genre.setDescription(patch.description());
        genreRepository.save(genre);
        return mapper.toGenreDto(genre);
    }

    @Override
    public void deleteGenreById(Long id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre", id));
        genre.getSongs().forEach(song -> song.setGenre(null));
        genre.getSongs().clear();
        genreRepository.deleteById(id);
    }
}
