package com.example.patitapp.backend.controller;

import com.example.patitapp.backend.entity.Location;
import com.example.patitapp.backend.repository.LocationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    private final LocationRepository locationRepository;

    public LocationController(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @GetMapping
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    @PostMapping
    public Location createLocation(@RequestBody Location location) {
        return locationRepository.save(location);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Location> updateLocation(@PathVariable Long id, @RequestBody Location locationDetails) {
        return locationRepository.findById(id)
                .map(location -> {
                    location.setName(locationDetails.getName());
                    location.setDescription(locationDetails.getDescription());
                    location.setPriceCLP(locationDetails.getPriceCLP());
                    location.setImageUrl(locationDetails.getImageUrl());
                    location.setLat(locationDetails.getLat());
                    location.setLon(locationDetails.getLon());
                    location.setCategory(locationDetails.getCategory());
                    Location updatedLocation = locationRepository.save(location);
                    return ResponseEntity.ok(updatedLocation);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        if (locationRepository.existsById(id)) {
            locationRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
