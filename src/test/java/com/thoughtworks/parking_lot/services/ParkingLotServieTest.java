package com.thoughtworks.parking_lot.services;

import com.thoughtworks.parking_lot.entities.ParkingLot;
import com.thoughtworks.parking_lot.repositories.ParkingLotRepository;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ParkingLotServieTest {
    @Autowired
    private ParkingLotService parkingLotService;

    @MockBean
    private ParkingLotRepository parkingLotRepository;

    @Test
    public void should_create_new_parkingLot() {
        ParkingLot parkingLot = new ParkingLot("my parking lot");
        parkingLot.setCapacity(30);
        parkingLot.setLocation("Manila");

        when(parkingLotRepository.save(any(ParkingLot.class))).thenReturn(parkingLot);

        ParkingLot savedParkingLot = parkingLotService.save(parkingLot);

        assertThat(savedParkingLot.getName(), is("my parking lot"));
        assertThat(savedParkingLot.getCapacity(), is(30));
        assertThat(savedParkingLot.getLocation(), is("Manila"));
    }
}