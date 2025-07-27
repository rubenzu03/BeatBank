package com.rubenzu03.beatbank.application;

import com.rubenzu03.beatbank.application.dto.AlbumDto;
import com.rubenzu03.beatbank.application.dto.SongDto;
import com.rubenzu03.beatbank.persistence.AlbumRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlbumService {

    private final AlbumRepository albumRepository;

    public AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    public List<AlbumDto> getAllAlbums(){
        return albumRepository.findAll().stream().map(AlbumDto::new).collect(Collectors.toList());
    }


}
