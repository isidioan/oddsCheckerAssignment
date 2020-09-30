package com.iioannou.oddscheckertest.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author ioannou
 */
public class OddsDto {

    @NotBlank(message = "user must not be empty")
    private String userId;

    @NotBlank
    private String odds;

    @NotNull(message = "bet id can not be null")
    private Long betId;


    private OddsDto() {
        super();
    }

    private OddsDto(String userId, String odds, Long betId) {
        this.userId = userId;
        this.odds = odds;
        this.betId = betId;
    }


    public static OddsDto of(String userId, String odds, Long betId) {
        return new OddsDto(userId, odds, betId);
    }

    public String getUserId() {
        return userId;
    }

    public String getOdds() {
        return odds;
    }

    public Long getBetId() {
        return betId;
    }
}
