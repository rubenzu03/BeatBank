package com.rubenzu03.beatbank.application.dto;

import java.io.Serializable;

public record SongPatchDto(String name, String duration, Long plays) implements Serializable {
}
