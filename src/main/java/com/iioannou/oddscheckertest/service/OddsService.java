package com.iioannou.oddscheckertest.service;

import com.iioannou.oddscheckertest.model.dto.OddsDto;
import com.iioannou.oddscheckertest.model.entity.BetT;
import com.iioannou.oddscheckertest.model.entity.OddT;
import com.iioannou.oddscheckertest.repository.BetRepository;
import com.iioannou.oddscheckertest.repository.OddsRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * @author ioannou
 */
@Service
public class OddsService {

    private final OddsRepository oddsRepository;

    private final BetRepository betRepository;

    public OddsService(OddsRepository oddsRepository, BetRepository betRepository) {
        this.oddsRepository = oddsRepository;
        this.betRepository = betRepository;
    }


    /**
     * Returns the odds given the bet Id
     *
     * @param betId id of bet
     * @return list of odds ordered by submission date desc
     */
    public List<OddsDto> getAllOddsByBetId(Long betId) {
        Optional<BetT> bet = betRepository.findByBetTrackingId(betId);

        if (!bet.isPresent()) {
            throw new NoSuchElementException("Bet not found for given ID");
        }

        return oddsRepository.findAllOddsByBetTrackingIdOrderBySubmittedDateDesc(betId).stream()
                             .map(o -> OddsDto.of(o.getUserId(), o.getValue(), o.getBet().getBetTrackingId())).collect(Collectors.toList());
    }


    /**
     * Creates a new odd
     *
     * @param oddsDto the to be added
     * @return the id of the generated odd
     */

    public Long addOdd(OddsDto oddsDto) {
        Optional<BetT> bet = betRepository.findByBetTrackingId(oddsDto.getBetId());

        if (!bet.isPresent()) {
            throw new IllegalArgumentException("Invalid Bet ID supplied");
        }

        OddT oddT = new OddT(oddsDto.getOdds(), oddsDto.getUserId());
        oddT.setBet(bet.get());

        return oddsRepository.save(oddT).getId();


    }


}
