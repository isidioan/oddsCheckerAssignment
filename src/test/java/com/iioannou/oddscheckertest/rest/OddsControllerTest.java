package com.iioannou.oddscheckertest.rest;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.iioannou.oddscheckertest.model.dto.OddsDto;
import com.iioannou.oddscheckertest.repository.BetRepository;
import com.iioannou.oddscheckertest.service.OddsService;
import com.iioannou.oddscheckertest.service.ValidationService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author ioannou
 */
@WebMvcTest
public class OddsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OddsService oddsService;

    @MockBean
    private ValidationService validationService;

    @MockBean
    private BetRepository betRepository;

    @BeforeEach
    public void setUp() {
        Mockito.reset(oddsService, validationService, betRepository);
    }


    @Test
    public void shouldReturnAvailableOdds() throws Exception {


        List<OddsDto> odds = new ArrayList<>();
        OddsDto odd1 = OddsDto.of("1", "1/2", 1L);
        odds.add(odd1);



        when(oddsService.getAllOddsByBetId(1L)).thenReturn(odds);

        mockMvc.perform(get("/odds/1").accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(1)))
               .andExpect(jsonPath("$[0].userId", is("1")))
               .andExpect(jsonPath("$[0].odds", is("1/2")))
               .andExpect(jsonPath("$[0].betId", is(1)));


    }


    @Test
    public void shouldAddOdds() throws Exception {


        List<OddsDto> odds = new ArrayList<>();
        OddsDto odd1 = OddsDto.of("1", "1/2", 1L);
        odds.add(odd1);



        when(oddsService.addOdd(odd1)).thenReturn(1L);

        when(validationService.isInvalidOddValue(odd1.getOdds())).thenReturn(false);

        String contentBody = "{\"userId\":\"2\", \"odds\":\"1/2\", \"betId\":\"1\"}";

        mockMvc.perform(post("/odds")
                .content(contentBody)
                .contentType(APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isCreated());


    }



    @Test
    public void shouldNotAddOdds() throws Exception {

        List<OddsDto> odds = new ArrayList<>();
        OddsDto odd1 = OddsDto.of("1", "1/2", 1L);
        odds.add(odd1);



        when(oddsService.addOdd(odd1)).thenReturn(1L);

        when(validationService.isInvalidOddValue(odd1.getOdds())).thenReturn(true);

        String contentBody = "{\"userId\":\"2\", \"odds\":\"1/2\", \"betId\":\"1\"}";

        mockMvc.perform(post("/odds")
                .content(contentBody)
                .contentType(APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().is4xxClientError());


    }


    @Test
    public void shouldRejectIfMissingFields() throws Exception {

        List<OddsDto> odds = new ArrayList<>();
        OddsDto odd1 = OddsDto.of("1", "1/2", 1L);
        odds.add(odd1);

        when(oddsService.addOdd(odd1)).thenReturn(1L);

        when(validationService.isInvalidOddValue(odd1.getOdds())).thenReturn(false);

        String contentBody = "{\"userId\":\"2\", \"odds\":\"\", \"betId\":\"1\"}";

        mockMvc.perform(post("/odds")
                .content(contentBody)
                .contentType(APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().is4xxClientError());


    }


}
