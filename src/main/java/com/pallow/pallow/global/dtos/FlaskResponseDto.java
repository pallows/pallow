package com.pallow.pallow.global.dtos;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FlaskResponseDto {

    private int status;
    private String message;
    private dataObject data;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public class dataObject {

        private List<Long> sortedIdList;
    }

}
