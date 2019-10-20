package com.thoughtworks.parking_lot.services;

import com.thoughtworks.parking_lot.entities.ParkingLot;
import com.thoughtworks.parking_lot.repositories.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParkingLotService {
    @Autowired
    private ParkingLotRepository parkingLotRepository;

    public Page<ParkingLot> listMultipleParkingLots(PageRequest pageRequest){
        return parkingLotRepository.findAll(pageRequest);
    }

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
