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
public class ParkingLotServiceTest {
    private static final String MY_PARKING_LOT = "my parking lot";
    private static final String INVALID_PARKING_LOT = "invalid parking lot";

    @Autowired
    private ParkingLotService parkingLotService;

    @MockBean
    private ParkingLotRepository parkingLotRepository;

    @Test
    public void should_create_new_parkingLot() {
        ParkingLot parkingLot = new ParkingLot(MY_PARKING_LOT);
        parkingLot.setCapacity(30);
        parkingLot.setLocation("Manila");

        when(parkingLotRepository.save(any(ParkingLot.class))).thenReturn(parkingLot);

        ParkingLot savedParkingLot = parkingLotService.save(parkingLot);

        assertThat(savedParkingLot.getName(), is(MY_PARKING_LOT));
        assertThat(savedParkingLot.getCapacity(), is(30));
        assertThat(savedParkingLot.getLocation(), is("Manila"));
    }

    @Test
    public void should_delete_existing_parkingLot() {
        ParkingLot parkingLot = new ParkingLot(MY_PARKING_LOT);
        when(parkingLotRepository.findById(MY_PARKING_LOT)).thenReturn(Optional.of(parkingLot));

        boolean wasDeleted = parkingLotService.delete(MY_PARKING_LOT);

        assertThat(wasDeleted, is(true));
    }

    @Test
    public void should_not_delete_non_existing_parkingLot() {
        boolean wasDeleted = parkingLotService.delete(INVALID_PARKING_LOT);

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
        when(parkingLotRepository.findById(MY_PARKING_LOT))
                .thenReturn(Optional.of(new ParkingLot(MY_PARKING_LOT)));

        Optional<ParkingLot> foundParkingLot = parkingLotService.getParkingLot(MY_PARKING_LOT);

        assertThat(foundParkingLot.isPresent(), is(true));
        foundParkingLot.ifPresent(parkingLot->{
            assertThat(parkingLot.getName(), is(MY_PARKING_LOT));
        });
    }

    @Test
    public void should_update_existing_parkingLot_capacity() {
        when(parkingLotRepository.findById(MY_PARKING_LOT))
                .thenReturn(Optional.of(new ParkingLot(MY_PARKING_LOT)));

        boolean wasUpdated = parkingLotService.updateCapacity(MY_PARKING_LOT, 50);

        assertThat(wasUpdated, is(true));
    }

    @Test
    public void should_not_update_existing_non_existing_parkingLot() {
        boolean wasUpdated = parkingLotService.updateCapacity(INVALID_PARKING_LOT, 50);

        assertThat(wasUpdated, is(false));
    }
}
