package com.flip.data.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Charles on 24/02/2022
 */
public enum IdentificationType {

    Drivers_License("Driver's License"),
    International_Passport("International Passport"),
    National_ID("National ID Card"),
    ;

    @Getter
    private String screenName;

    IdentificationType(String screenName){
        this.screenName = screenName;
    }

    public static List<String> screenNames() {
        return Arrays.stream(values()).map(IdentificationType::getScreenName).collect(Collectors.toList());
    }

}
