package com.thoughtworks.parking_lot.services;

import com.thoughtworks.parking_lot.entities.ParkingLot;
import com.thoughtworks.parking_lot.repositories.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParkingLotService {
    @Autowired
    private ParkingLotRepository parkingLotRepository;

    public ParkingLot save(ParkingLot parkingLot) {
        return parkingLotRepository.save(parkingLot);
    }

    public boolean delete(String parkingLotName) {
        Optional<ParkingLot> optionalParkingLot = parkingLotRepository.findById(parkingLotName);

        if (optionalParkingLot.isPresent()) {
            ParkingLot foundParkingLot = optionalParkingLot.get();
            parkingLotRepository.delete(foundParkingLot);
            return true;
        }
        return false;
    }
}
