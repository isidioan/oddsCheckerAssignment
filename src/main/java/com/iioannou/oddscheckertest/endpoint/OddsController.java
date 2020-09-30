package com.iioannou.oddscheckertest.endpoint;

import com.iioannou.oddscheckertest.exception.OddsInvalidValueException;
import com.iioannou.oddscheckertest.model.dto.OddsDto;
import com.iioannou.oddscheckertest.service.OddsService;
import com.iioannou.oddscheckertest.service.ValidationService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ioannou
 */
@RestController
@RequestMapping("/odds")
public class OddsController {

    private final OddsService oddsService;

    private final ValidationService validationService;

    public OddsController(OddsService oddsService, ValidationService validationService) {
        this.oddsService = oddsService;
        this.validationService = validationService;
    }

    @GetMapping(value = "/{betId}")
    @ResponseStatus(HttpStatus.OK)
    public List<OddsDto> getOdds(@PathVariable Long betId) {

        return oddsService.getAllOddsByBetId(betId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOdd(@Valid @RequestBody OddsDto oddsDto) {

        if (validationService.isInvalidOddValue(oddsDto.getOdds())) {
            throw new OddsInvalidValueException("Illegal odds value");
        }

        oddsService.addOdd(oddsDto);
    }

}
