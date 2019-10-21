package com.thoughtworks.parking_lot.controllers;

import com.thoughtworks.parking_lot.entities.ParkingLot;
import com.thoughtworks.parking_lot.services.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/parking-lots")
public class ParkingLotController {
    @Autowired
    private ParkingLotService parkingLotService;

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ParkingLot> saveParkingLot(@RequestBody ParkingLot parkingLot) {
        return new ResponseEntity<>(parkingLotService.save(parkingLot), HttpStatus.CREATED);
    }

    @DeleteMapping(produces = {"application/json"})
    @RequestMapping("/{name}")
    public ResponseEntity<String> deleteParkingLot(@PathVariable String name){
        boolean wasDeleted = parkingLotService.delete(name);

        if(wasDeleted){
            return new ResponseEntity<>("Deleted parkingLot " + name, HttpStatus.OK);
        }
        return new ResponseEntity<>("Cannot delete parkingLot " + name, HttpStatus.BAD_REQUEST);
    }
}
