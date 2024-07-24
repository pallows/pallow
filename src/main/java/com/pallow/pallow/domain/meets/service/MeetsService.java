package com.pallow.pallow.domain.meets.service;

import com.pallow.pallow.domain.meets.dto.MeetsRequestDto;
import com.pallow.pallow.domain.meets.dto.MeetsResponseDto;
import com.pallow.pallow.domain.meets.entity.Meets;
import com.pallow.pallow.domain.meets.repository.MeetsRepository;
import com.pallow.pallow.domain.user.entity.User;
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

    /**
     * 그룹 생성
     */
    public MeetsResponseDto create(Long userId, MeetsRequestDto requestDto, User user) {
        //userId가 존재하는지 확인
        User existUser = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_USER)
        );

        // 로그인된 유저와 요청의 유저가 일치하는지 확인
        if (!existUser.getId().equals(user.getId())) {
            throw new CustomException(ErrorType.UNAPPROVED_USER);
        }

        // requestDto -> entity
        Meets meets = Meets.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .user(existUser)
                .status(CommonStatus.ACTIVE)
                .build();

        meetsRepository.save(meets);

        // entity -> responseDto
        return new MeetsResponseDto(meets);
    }

    /**
     * 그룹 선택 조회
     */
    public MeetsResponseDto getMeets(Long meetsId) {
        Meets meets = findByMeetsIdAndStatus(meetsId);

        return new MeetsResponseDto(meets);
    }

    /**
     * 그룹 전체 조회
     */
    public List<MeetsResponseDto> getAllMeets() {
        List<Meets> meetsList = meetsRepository.findAllByStatus(CommonStatus.ACTIVE);
        return meetsList.stream()
                .map(MeetsResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 그룹 업데이트
     */
    @Transactional
    public MeetsResponseDto update(Long meetsId, MeetsRequestDto requestDto, User user) {
        Meets meets = findByMeetsIdAndStatus(meetsId);

        // 로그인된 유저와 그룹 생성자가 일치하는지 확인
        if (!user.getId().equals(meets.getCreatedBy().getId())) {
            throw new CustomException(ErrorType.UNAPPROVED_USER);
        }

        Meets updatedMeets = meets.update(requestDto);

        return new MeetsResponseDto(updatedMeets);
    }

    /**
     * 그룹 삭제
     */
    @Transactional
    public void delete(Long meetsId, User user) {
        Meets meets = findByMeetsIdAndStatus(meetsId);

        // 로그인된 유저와 그룹 생성자가 일치하는지 확인
        if (!user.getId().equals(meets.getCreatedBy().getId())) {
            throw new CustomException(ErrorType.UNAPPROVED_USER);
        }

        meets.delete();
    }

    /**
     * 그룹 ID 와 상태 검사
     */
    public Meets findByMeetsIdAndStatus(Long meetsId) {
        return meetsRepository.findByIdAndStatus(meetsId, CommonStatus.ACTIVE).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_GROUP)
        );
    }

}
