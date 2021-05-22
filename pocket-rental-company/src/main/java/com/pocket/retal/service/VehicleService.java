package com.pocket.retal.service;

import com.pocket.retal.model.dto.VehicleDTO;
import com.pocket.retal.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleService {

    private VehicleRepository vehicleRepository;
    public static final int DEF_PAGE_SIZE = 500;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public List<VehicleDTO> getVehicles() {
        List<VehicleDTO> vehicleList = vehicleRepository.selectAllVehicles(0, DEF_PAGE_SIZE);
        if (vehicleList == null) {
            vehicleList = new ArrayList<>();
        }
        return vehicleList;
    }

    public List<VehicleDTO> getVehicles(int offset, int pageSize) {
        List<VehicleDTO> vehicleList = vehicleRepository.selectAllVehicles(offset, pageSize);
        if (vehicleList == null) {
            vehicleList = new ArrayList<>();
        }
        return vehicleList;
    }
}
