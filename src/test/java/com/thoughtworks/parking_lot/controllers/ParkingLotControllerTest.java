package com.thoughtworks.parking_lot.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.parking_lot.entities.ParkingLot;
import com.thoughtworks.parking_lot.services.ParkingLotService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.AbstractPageRequest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(ParkingLotController.class)
@ActiveProfiles(profiles = "test")
public class ParkingLotControllerTest {
    private static final String MY_PARKING_LOT = "my parking lot";
    private static final String INVALID_PARKING_LOT = "invalid parking lot";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ParkingLotService parkingLotService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void should_save_a_new_parkingLot() throws Exception {
        ParkingLot parkingLot = new ParkingLot(MY_PARKING_LOT);
        parkingLot.setCapacity(50);
        parkingLot.setLocation("Manila");

        when(parkingLotService.save(eq(parkingLot))).thenReturn(parkingLot);

        ResultActions result = mvc.perform(post("/parking-lots")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(parkingLot))
        );

        result.andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(MY_PARKING_LOT)))
                .andExpect(jsonPath("$.capacity", is(50)))
                .andExpect(jsonPath("$.location", is("Manila")))
        ;
    }

    @Test
    public void should_delete_parking_lot() throws Exception {
        when(parkingLotService.delete(MY_PARKING_LOT)).thenReturn(true);

        ResultActions result = mvc.perform(delete("/parking-lots/"+MY_PARKING_LOT));

        result.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void should_note_delete_non_existing_parking_lot() throws Exception {
        when(parkingLotService.delete(INVALID_PARKING_LOT)).thenReturn(false);

        ResultActions result = mvc.perform(delete("/parking-lots/"+INVALID_PARKING_LOT));

        result.andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void should_be_able_to_view_multiple_parking_lots() throws Exception {
        List<ParkingLot> givenParkingLotList = Arrays.asList(
                new ParkingLot("Parking lot 1"),
                new ParkingLot("Parking lot 2")
        );

        when(parkingLotService.listMultipleParkingLots(any(PageRequest.class))).thenReturn(
                new PageImpl<>(givenParkingLotList)
        );

        ResultActions result = mvc.perform(get("/parking-lots?page=0"));

        result.andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$.totalElements", is(2)))
            .andExpect(jsonPath("$.content[0].name", is("Parking lot 1")))
            .andExpect(jsonPath("$.content[1].name", is("Parking lot 2")))
        ;
    }

    @Test
    public void should_not_be_able_to_view_parking_lots_if_outside_page_range() throws Exception {
        List<ParkingLot> givenParkingLotList = Arrays.asList(
                new ParkingLot("Parking lot 1"),
                new ParkingLot("Parking lot 2")
        );

        when(parkingLotService.listMultipleParkingLots(any(PageRequest.class))).thenReturn(
                new PageImpl<>(givenParkingLotList, PageRequest.of(1, 15), 2)
        );

        ResultActions result = mvc.perform(get("/parking-lots?page=1"));

        result.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.totalElements", is(17)))
                .andExpect(jsonPath("$.content[0].name", is("Parking lot 1")))
                .andExpect(jsonPath("$.content[1].name", is("Parking lot 2")))
        ;
    }
}
