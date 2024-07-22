package com.pallow.pallow.domain.meets.service;

import com.pallow.pallow.domain.meets.dto.MeetsRequestDto;
import com.pallow.pallow.domain.meets.dto.MeetsResponseDto;
import com.pallow.pallow.domain.meets.entity.Meets;
import com.pallow.pallow.domain.meets.repository.MeetsRepository;
import com.pallow.pallow.domain.user.repository.UserRepository;
import com.pallow.pallow.global.enums.CommonStatus;
import com.pallow.pallow.global.enums.ErrorType;
import com.pallow.pallow.global.exception.CustomException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MeetsService {

    private final MeetsRepository meetsRepository;
    private final UserRepository userRepository;

    public MeetsResponseDto create(Long userId, MeetsRequestDto requestDto) {
//        //userId가 존재하는지 확인
//        User user = userRepository.findById(userId).orElseThrow(
//                () -> new RuntimeException("유저가 존재하지 않습니다.")
//        );

        // requestDto -> entity
        Meets meets = Meets.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
//                .user(user)
                .status(CommonStatus.ACTIVE)
                .build();

        meetsRepository.save(meets);

        // entity -> responseDto
        return new MeetsResponseDto(meets);
    }

    public MeetsResponseDto getMeets(Long meetsId) {
        Meets meets = findByMeetsIdAndStatus(meetsId);

        return new MeetsResponseDto(meets);
    }

    public List<MeetsResponseDto> getAllMeets() {
        List<Meets> meetsList = meetsRepository.findAllByStatus(CommonStatus.ACTIVE);
        return meetsList.stream()
                .map(MeetsResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public MeetsResponseDto update(Long meetsId, MeetsRequestDto requestDto) {
        Meets meets = findByMeetsIdAndStatus(meetsId);

        Meets updatedMeets = meets.update(requestDto);

        return new MeetsResponseDto(updatedMeets);
    }

    @Transactional
    public void delete(Long meetsId) {
        Meets meets = findByMeetsIdAndStatus(meetsId);
        meets.delete();
    }

    public Meets findByMeetsIdAndStatus(Long meetsId) {
        return meetsRepository.findByIdAndStatus(meetsId, CommonStatus.ACTIVE).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_MEET_ID)
        );
    }

}
