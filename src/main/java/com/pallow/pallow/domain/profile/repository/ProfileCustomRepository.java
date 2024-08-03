package com.pallow.pallow.domain.profile.repository;

import com.pallow.pallow.domain.profile.entity.Profile;
import java.util.List;

public interface ProfileCustomRepository {

    List<Profile> findAllByPosition(String position);

}
