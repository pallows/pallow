package com.pallow.pallow.global.region;

import com.pallow.pallow.global.enums.ErrorType;
import com.pallow.pallow.global.exception.CustomException;

public interface District {

    int getDistrictCode();

    static <T extends Enum<T> & District> T findByDistrictCode(Class<T> enumType, int code) {
        for (T district : enumType.getEnumConstants()) {
            if (district.getDistrictCode() == code) {
                return district;
            }
        }
        throw new CustomException(ErrorType.NOT_FOUND_REGION);
    }
}
