package com.rubenzu03.beatbank.adapter.inbound.rest;

import com.rubenzu03.beatbank.application.dto.AlbumDto;
import com.rubenzu03.beatbank.application.dto.AlbumPatchDto;
import com.rubenzu03.beatbank.application.dto.SongDto;
import com.rubenzu03.beatbank.application.port.inbound.AlbumUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/albums")
public class AlbumController {

    private final AlbumUseCase albumUseCase;

    public AlbumController(AlbumUseCase albumUseCase) {
        this.albumUseCase = albumUseCase;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AlbumDto> getAllAlbums() {
        return albumUseCase.getAllAlbums();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AlbumDto getAlbumById(@PathVariable Long id) {
        return albumUseCase.getAlbumById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AlbumDto createAlbum(@Valid @RequestBody AlbumDto albumDto) {
        return albumUseCase.createAlbum(albumDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AlbumDto updateAlbum(@PathVariable Long id, @Valid @RequestBody AlbumDto albumDto) {
        return albumUseCase.updateAlbum(id, albumDto);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AlbumDto patchAlbum(@PathVariable Long id, @RequestBody AlbumPatchDto patch) {
        return albumUseCase.patchAlbum(id, patch);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAlbumById(@PathVariable Long id) {
        albumUseCase.deleteAlbumById(id);
    }
}
