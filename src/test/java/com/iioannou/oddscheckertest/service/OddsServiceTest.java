package com.iioannou.oddscheckertest.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

import com.iioannou.oddscheckertest.model.dto.OddsDto;
import com.iioannou.oddscheckertest.model.entity.BetT;
import com.iioannou.oddscheckertest.repository.BetRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author ioannou
 */

@SpringBootTest
public class OddsServiceTest {

    @Autowired
    private OddsService oddsService;

    @Autowired
    private BetRepository betRepository;

    @Autowired
    private ValidationService validationService;



    private BetT createTestBet(Long betId) {
        BetT bet = new BetT();
        bet.setBetTrackingId(betId);
        betRepository.save(bet);
        return bet;
    }

    @Test
    public void shouldPersistOdd() {

        BetT bet = createTestBet(1L);

        OddsDto odd = OddsDto.of("1", "1/2", bet.getBetTrackingId());

        Long oddId = oddsService.addOdd(odd);

        assertThat("Comment id shouldn't be null", oddId, notNullValue());
    }


    @Test
    public void shouldRetrieveBet() {

        BetT bet = createTestBet(2L);


        Optional<BetT> retrievedBet = betRepository.findByBetTrackingId(2L);

        Assertions.assertTrue(retrievedBet.isPresent());
        assertThat(retrievedBet.get().getBetTrackingId(), equalTo(2L));

    }


    @Test
    public void shouldReturnAddedOdds() {
        BetT bet = createTestBet(3L);

        OddsDto odd1 = OddsDto.of("1", "1/2", bet.getBetTrackingId());

        Long oddId1 = oddsService.addOdd(odd1);

        OddsDto odd2 = OddsDto.of("2", "1/10", bet.getBetTrackingId());

        Long oddId2 = oddsService.addOdd(odd2);

        List<OddsDto> odds = oddsService.getAllOddsByBetId(bet.getBetTrackingId());

        assertThat("There should be two odds", odds, hasSize(2));
        assertThat(odds.get(0).getUserId(), equalTo("2"));
        assertThat(odds.get(1).getOdds(), equalTo("1/2"));
    }



    @Test
    public void shouldValidateOddValue() {

        Assertions.assertFalse(validationService.isInvalidOddValue("1/2"));
        Assertions.assertFalse(validationService.isInvalidOddValue("SP"));

        Assertions.assertTrue(validationService.isInvalidOddValue("SP/4"));
        Assertions.assertTrue(validationService.isInvalidOddValue("SP?4"));
        Assertions.assertTrue(validationService.isInvalidOddValue("1/3/5"));
        Assertions.assertTrue(validationService.isInvalidOddValue("1*5"));
    }



}
