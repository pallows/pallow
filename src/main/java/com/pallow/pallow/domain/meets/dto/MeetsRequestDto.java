package com.pallow.pallow.domain.meets.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MeetsRequestDto {

    @NotNull(message = "title cannot be null.")
    private String title;

    @NotNull(message = "content cannot be null.")
    private String content;

    private String image;

    private String position;

    @Min(value = 2, message = "maxMemberCount should not be less than 2")
    @Max(value = 50, message = "maxMemberCount should not be more than 50")
    @NotNull(message = "maxMemberCount cannot be null.")
    private int maxMemberCount;
}
