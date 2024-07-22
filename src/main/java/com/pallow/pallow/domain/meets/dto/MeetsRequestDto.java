package com.pallow.pallow.domain.meets.dto;

import jakarta.persistence.Column;
import lombok.Getter;

@Getter
public class MeetsRequestDto {

    private String title;

    private String content;
}
