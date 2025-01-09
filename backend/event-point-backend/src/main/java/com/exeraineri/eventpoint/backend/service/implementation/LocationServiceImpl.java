/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.exeraineri.eventpoint.backend.service.implementation;

import com.exeraineri.eventpoint.backend.entity.Location;
import com.exeraineri.eventpoint.backend.exception.CustomException;
import com.exeraineri.eventpoint.backend.exception.CustomException.ResourceNotFoundException;
import com.exeraineri.eventpoint.backend.repository.ILocationRepository;
import com.exeraineri.eventpoint.backend.service.interfaces.ILocationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements ILocationService {

    private final ILocationRepository locationRepository;

    @Override
    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    @Override
    public Location findById(Long id) {
        return locationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ubicacion con ID " + id + " no existe"));
    }

    @Override
    public Location save(Location location) {
        return locationRepository.save(location);
    }

    @Override
    public void deleteById(Long id) {
        if (!locationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ubicacion con ID " + id + " no existe");
        }
        locationRepository.deleteById(id);
    }

    @Override
    public Location update(Long id, Location location) {
        Location locationBD = locationRepository.findById(id)
                .orElseThrow(() -> new CustomException.ResourceNotFoundException("Ubicacion conn ID " + id + " no encontrada"));
        locationBD.setAddress(location.getAddress());
        locationBD.setLatitude(location.getLatitude());
        locationBD.setLongitude(location.getLongitude());
        locationBD.setPostalCode(location.getPostalCode());
        return locationRepository.save(location);
    }

}
