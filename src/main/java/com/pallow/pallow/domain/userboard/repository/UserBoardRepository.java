package com.pallow.pallow.domain.userboard.repository;

import com.pallow.pallow.domain.userboard.entity.UserBoard;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBoardRepository extends JpaRepository<UserBoard, Long>,
        UserBoardCustomRepository {

    List<UserBoard> findAllByUserId(long userId);


    @Query(value = "SELECT ub.* FROM user_board ub " +
            "JOIN user u ON ub.user_id = u.user_id " +
            "JOIN profile p ON u.user_id = p.user_id " +
            "WHERE p.position = :position " +
            "ORDER BY RAND() " +
            "LIMIT :limit",
            nativeQuery = true)
    List<UserBoard> findRandomByPosition(@Param("position") String position,
            @Param("limit") int limit);

}
