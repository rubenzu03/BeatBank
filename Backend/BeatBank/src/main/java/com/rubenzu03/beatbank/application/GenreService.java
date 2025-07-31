package com.rubenzu03.beatbank.application;

import com.rubenzu03.beatbank.application.dto.GenreDto;
import com.rubenzu03.beatbank.domain.Genre;
import com.rubenzu03.beatbank.domain.Song;
import com.rubenzu03.beatbank.persistence.GenreRepository;
import com.rubenzu03.beatbank.persistence.SongRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {

    private final GenreRepository genreRepository;
    private final SongRepository songRepository;

    public GenreService(GenreRepository genreRepository, SongRepository songRepository) {
        this.genreRepository = genreRepository;
        this.songRepository = songRepository;
    }

    public List<GenreDto> getAllGenres() {
        return genreRepository.findAll().stream()
                .map(GenreDto::new)
                .toList();
    }

    public GenreDto getGenreById(Long id) {
        return genreRepository.findById(id)
                .map(GenreDto::new)
                .orElse(null);
    }

    public GenreDto createGenre(GenreDto genreDto) {
        Genre genre = new Genre(genreDto);
        genreRepository.save(genre);
        return new GenreDto(genre);
    }


    public void deleteGenreById(Long id) {
        Song song = songRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Song not found"));
        song.setGenre(null);
        songRepository.save(song);
        genreRepository.deleteById(id);
    }
}
