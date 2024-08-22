package com.pallow.pallow.domain.meetsreview.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ReviewRequestDto {

    @NotNull(message = "content cannot be null.")
    private String content;

    @NotNull(message = "starRating cannot be null.")
    private int starRating;
}
