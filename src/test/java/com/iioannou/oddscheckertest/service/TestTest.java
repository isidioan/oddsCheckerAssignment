package com.iioannou.oddscheckertest.service;

import com.iioannou.oddscheckertest.model.entity.BetT;
import com.iioannou.oddscheckertest.repository.BetRepository;
import com.iioannou.oddscheckertest.repository.OddsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@ComponentScan(basePackages = "com.iioannou.oddscheckertest")
//@TestPropertySource(locations = "/application-test.yaml")
class TestTest {


    @Autowired
    OddsRepository oddsRepository;

    @Autowired
    private BetRepository betRepository;


    private BetT createTestBet(Long betId) {
        BetT bet = new BetT();
        bet.setBetTrackingId(betId);
        betRepository.save(bet);
        return bet;
    }

    @Test
    public void shouldPersistOdd() {

        BetT bet = createTestBet(1L);

    }


    @Test
    public void shouldRetrieveBet() {

        BetT bet = createTestBet(2L);


        Optional<BetT> retrievedBet = betRepository.findByBetTrackingId(2L);

        Assertions.assertTrue(retrievedBet.isPresent());
        assertThat(retrievedBet.get().getBetTrackingId(), equalTo(2L));
    }

}
