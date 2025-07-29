package com.rubenzu03.beatbank.application.api;

import com.rubenzu03.beatbank.application.AlbumService;
import com.rubenzu03.beatbank.application.dto.AlbumDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/albums")
public class AlbumRestController {

    public final AlbumService albumService;

    public AlbumRestController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AlbumDto> getAllAlbums() {
        return albumService.getAllAlbums();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AlbumDto createAlbum(@RequestBody AlbumDto albumDto) {
        return albumService.createAlbum(albumDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public AlbumDto updateAlbum(@RequestParam Long id, @RequestBody AlbumDto albumDto) {
        return albumService.updateAlbum(id, albumDto);
    }
}
