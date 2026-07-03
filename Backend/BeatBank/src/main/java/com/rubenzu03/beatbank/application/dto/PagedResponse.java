package com.rubenzu03.beatbank.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;

import java.util.List;

@Schema(description = "Generic paginated response wrapper")
public record PagedResponse<T>(
        @Schema(description = "Page items") List<T> items,
        @Schema(description = "Current page number (0-based)") int page,
        @Schema(description = "Page size") int size,
        @Schema(description = "Total items across all pages") long totalItems,
        @Schema(description = "Total number of pages") int totalPages
) {
    public PagedResponse(Page<T> page) {
        this(page.getContent(), page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages());
    }
}
