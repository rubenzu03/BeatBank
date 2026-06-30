package com.rubenzu03.beatbank.application.service;

import com.rubenzu03.beatbank.application.dto.GenreDto;
import com.rubenzu03.beatbank.application.port.inbound.GenreUseCase;
import com.rubenzu03.beatbank.domain.Genre;
import com.rubenzu03.beatbank.domain.Song;
import com.rubenzu03.beatbank.domain.port.outbound.GenreRepository;
import com.rubenzu03.beatbank.domain.port.outbound.SongRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreUseCaseImpl implements GenreUseCase {

    private final GenreRepository genreRepository;
    private final SongRepository songRepository;

    public GenreUseCaseImpl(GenreRepository genreRepository, SongRepository songRepository) {
        this.genreRepository = genreRepository;
        this.songRepository = songRepository;
    }

    @Override
    public List<GenreDto> getAllGenres() {
        return genreRepository.findAll().stream()
                .map(GenreDto::new)
                .toList();
    }

    @Override
    public GenreDto getGenreById(Long id) {
        return genreRepository.findById(id)
                .map(GenreDto::new)
                .orElse(null);
    }

    @Override
    public GenreDto createGenre(GenreDto genreDto) {
        Genre genre = new Genre(genreDto);
        genreRepository.save(genre);
        return new GenreDto(genre);
    }

    @Override
    public void deleteGenreById(Long id) {
        Song song = songRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Song not found"));
        song.setGenre(null);
        songRepository.save(song);
        genreRepository.deleteById(id);
    }
}
