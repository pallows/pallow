package com.pallow.pallow.domain.meets.service;

import com.pallow.pallow.domain.invitedboard.entity.InvitedBoard;
import com.pallow.pallow.domain.invitedboard.repository.InvitedBoardRepository;
import com.pallow.pallow.domain.like.service.LikeService;
import com.pallow.pallow.domain.meets.dto.MeetsRequestDto;
import com.pallow.pallow.domain.meets.dto.MeetsResponseDto;
import com.pallow.pallow.domain.meets.entity.Meets;
import com.pallow.pallow.domain.meets.repository.MeetsCustomRepository;
import com.pallow.pallow.domain.meets.repository.MeetsRepository;
import com.pallow.pallow.domain.user.dto.UserResponseDto;
import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.domain.user.repository.UserCustomRepository;
import com.pallow.pallow.domain.user.repository.UserRepository;
import com.pallow.pallow.global.enums.CommonStatus;
import com.pallow.pallow.global.enums.ErrorType;
import com.pallow.pallow.global.enums.InviteStatus;
import com.pallow.pallow.global.exception.CustomException;
import com.pallow.pallow.global.s3.service.ImageService;

import java.io.IOException;
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
    private final LikeService likeService;
    private final ImageService imageService;
    private final InvitedBoardRepository invitedBoardRepository;
    private final UserCustomRepository userCustomRepository;
    private final MeetsCustomRepository meetsCustomRepository;

    /**
     * 그룹 생성
     */
    public MeetsResponseDto create(MeetsRequestDto requestDto, User user) {
        //userId가 존재하는지 확인
        User existUser = userCustomRepository.findById(user.getId());

        // 이미지 업로드
        String imageUrl = null;
        try {
            imageUrl = imageService.imageUpload(requestDto.getImage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // requestDto -> entity
        Meets meets = Meets.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .image(imageUrl)
                .maxMemberCount(requestDto.getMaxMemberCount())
                .position(requestDto.getPosition())
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
        Meets meets = meetsRepository.findByIdAndStatus(meetsId, CommonStatus.ACTIVE).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_GROUP));
        meets.updateMemberList();
        return new MeetsResponseDto(meets);
    }

    /**
     * 그룹 전체 조회
     */
    public List<MeetsResponseDto> getAllMeets() {
        List<Meets> meetsList = meetsCustomRepository.findAllByStatus(CommonStatus.ACTIVE);
        return meetsList.stream()
                .map(MeetsResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 그룹 업데이트
     */
    @Transactional
    public MeetsResponseDto update(Long meetsId, MeetsRequestDto requestDto, User user) {
        Meets meets = meetsCustomRepository.findByIdAndStatus(meetsId, CommonStatus.ACTIVE)
                .orElseThrow(
                        () -> new CustomException(ErrorType.NOT_FOUND_GROUP));

        // 로그인된 유저와 그룹 생성자가 일치하는지 확인
        if (!user.getId().equals(meets.getGroupCreator().getId())) {
            throw new CustomException(ErrorType.UNAPPROVED_USER);
        }

        // 이미지 업로드
        String imageUrl = null;
        if (requestDto.getImage() != null && !requestDto.getImage().isEmpty()) {
            try {
                imageService.deleteImage(meets.getImage());
                imageUrl = imageService.imageUpload(requestDto.getImage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            imageUrl = meets.getImage(); // 기존 이미지 URL 유지
        }

        meets.update(requestDto, imageUrl);

        return new MeetsResponseDto(meets);
    }

    /**
     * 그룹 삭제
     */
    @Transactional
    public void delete(Long meetsId, User user) {
        Meets meets = meetsCustomRepository.findByIdAndStatus(meetsId, CommonStatus.ACTIVE)
                .orElseThrow(
                        () -> new CustomException(ErrorType.NOT_FOUND_GROUP));

        // 로그인된 유저와 그룹 생성자가 일치하는지 확인
        if (!user.getId().equals(meets.getGroupCreator().getId())) {
            throw new CustomException(ErrorType.UNAPPROVED_USER);
        }

        // 이미지 삭제
        if (meets.getImage() != null && !meets.getImage().isEmpty()) {
            imageService.deleteImage(meets.getImage());
        }

        meets.delete();
    }

    /**
     * 그룹에 존재하는 회원 전체 조회 (그룹생성자 포함)
     *
     * @param meetsId
     * @return
     */
    @Transactional
    public List<UserResponseDto> getAllMeetsMembers(Long meetsId) {
        Meets meets = meetsCustomRepository.findByIdAndStatus(meetsId, CommonStatus.ACTIVE)
                .orElseThrow(
                        () -> new CustomException(ErrorType.NOT_FOUND_GROUP));

        List<InvitedBoard> invitedMemberList = invitedBoardRepository.findAllByMeetsAndStatus(
                meets, InviteStatus.ACCEPTED);

        return invitedMemberList.stream()
                .map(InvitedBoard::getUser)
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 맴버 강퇴
     *
     * @param meetsId
     * @param userId
     * @param user
     */
    public void withdrawMember(Long meetsId, Long userId, User user) {
        Meets meets = meetsCustomRepository.findByIdAndStatus(meetsId, CommonStatus.ACTIVE)
                .orElseThrow(
                        () -> new CustomException(ErrorType.NOT_FOUND_GROUP));

        // 로그인된 유저와 그룹 생성자가 일치하는지 확인
        if (!user.getId().equals(meets.getGroupCreator().getId())) {
            throw new CustomException(ErrorType.UNAPPROVED_USER);
        }

        // 그룹에 맴버가 존재하는지 확인
        InvitedBoard invitedMember = invitedBoardRepository.findByUserIdAndMeetsIdAndStatus(
                userId, meetsId, InviteStatus.ACCEPTED).orElseThrow(
                () -> new RuntimeException("회원이 존재하지 않습니다.")
        );

        invitedBoardRepository.delete(invitedMember);
    }

    /**
     * 좋아요 토글
     *
     * @param meetsId
     * @param user
     */
    @Transactional
    public void toggleLike(Long meetsId, User user) {
        likeService.toggleLike(meetsId, user, meetsRepository);
    }

    /**
     * 내 주변 모임 (무작위 선택)
     */
    @Transactional(readOnly = true)
    public List<MeetsResponseDto> getNearbyMeets(User user, int limit) {
        // 사용자의 위치 정보를 기반으로 주변 모임을 찾는 로직 구현
        // 여기서는 간단히 무작위로 선택하는 것으로 가정
        List<Meets> nearbyMeets = meetsRepository.findRandomNearbyMeets(
                user.getProfile().getPosition(), limit);
        return nearbyMeets.stream()
                .map(MeetsResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 인기 급 상승 모임 (좋아요 순)
     */
    @Transactional(readOnly = true)
    public List<MeetsResponseDto> getPopularMeets(int limit) {
        List<Meets> popularMeets = meetsCustomRepository.findTopByOrderByLikesCountDesc(limit);
        return popularMeets.stream()
                .map(MeetsResponseDto::new)
                .collect(Collectors.toList());
    }
}
