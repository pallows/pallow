package com.pallow.pallow.global.dtos;

import com.pallow.pallow.domain.profile.entity.ProfileItem;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FlaskRequestDto {

    private Long id;
    private List<ProfileItem> profiles;
}
