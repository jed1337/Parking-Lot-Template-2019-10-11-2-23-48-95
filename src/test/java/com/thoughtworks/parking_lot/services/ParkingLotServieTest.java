package com.thoughtworks.parking_lot.services;

import com.thoughtworks.parking_lot.entities.ParkingLot;
import com.thoughtworks.parking_lot.repositories.ParkingLotRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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

    @Test
    public void should_delete_existing_parkingLot() {
        String parkingLotName = "my parking lot";
        ParkingLot parkingLot = new ParkingLot(parkingLotName);
        when(parkingLotRepository.findById(parkingLotName)).thenReturn(Optional.of(parkingLot));

        boolean wasDeleted = parkingLotService.delete(parkingLotName);

        assertThat(wasDeleted, is(true));
    }

    @Test
    public void should_not_delete_non_existing_parkingLot() {
        boolean wasDeleted = parkingLotService.delete("invalid parking lot");

        assertThat(wasDeleted, is(false));
    }

    @Test
    public void should_get_multiple_parking_lots() {
        List<ParkingLot> givenParkingLotList = Arrays.asList(
                new ParkingLot("Parking lot 1"),
                new ParkingLot("Parking lot 2")
        );

        PageRequest pageRequest = PageRequest.of(0, 15);

        when(parkingLotRepository.findAll(eq(pageRequest))).thenReturn(
                new PageImpl<>(givenParkingLotList)
        );

        Page<ParkingLot> actualParkingLotList = parkingLotService.listMultipleParkingLots(pageRequest);
        assertThat(actualParkingLotList.getTotalElements(), is(2L));
    }

    @Test
    public void should_get_one_parking_lot() {
        String parkingLotName = "my parking lot";
        when(parkingLotRepository.findById(parkingLotName))
                .thenReturn(Optional.of(new ParkingLot(parkingLotName)));

        Optional<ParkingLot> foundParkingLot = parkingLotService.getParkingLot(parkingLotName);

        assertThat(foundParkingLot.isPresent(), is(true));
        foundParkingLot.ifPresent(parkingLot->{
            assertThat(parkingLot.getName(), is(parkingLotName));
        });
    }
}
