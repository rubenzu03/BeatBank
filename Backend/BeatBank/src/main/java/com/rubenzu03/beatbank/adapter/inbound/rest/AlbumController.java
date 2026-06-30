package com.rubenzu03.beatbank.adapter.inbound.rest;

import com.rubenzu03.beatbank.application.dto.AlbumDto;
import com.rubenzu03.beatbank.application.port.inbound.AlbumUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/albums")
public class AlbumController {

    public final AlbumUseCase albumUseCase;

    public AlbumController(AlbumUseCase albumUseCase) {
        this.albumUseCase = albumUseCase;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AlbumDto> getAllAlbums() {
        return albumUseCase.getAllAlbums();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AlbumDto createAlbum(@RequestBody AlbumDto albumDto) {
        return albumUseCase.createAlbum(albumDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public AlbumDto updateAlbum(@RequestParam Long id, @RequestBody AlbumDto albumDto) {
        return albumUseCase.updateAlbum(id, albumDto);
    }
}
