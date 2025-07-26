package com.rubenzu03.beatbank.application.api;

import com.rubenzu03.beatbank.SongDto;
import com.rubenzu03.beatbank.application.SongService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SongRestController {

    private final SongService songService;

    public SongRestController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping("/songs")
    @ResponseStatus(HttpStatus.OK)
    public List<SongDto> getAllSongs(){
        return songService.getAllSongs();
    }

    @PostMapping("/songs")
    @ResponseStatus(HttpStatus.CREATED)
    public SongDto createSong(@RequestBody SongDto songDto){
        return songService.createSong(songDto);
    }


}
