package com.pocket.retal.service;

import com.pocket.retal.model.ParamsConst;
import com.pocket.retal.model.dto.VehicleDTO;
import com.pocket.retal.model.dto.VehicleSkuDTO;
import com.pocket.retal.repository.SkuRepository;
import com.pocket.retal.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleService {

    private VehicleRepository vehicleRepository;
    private SkuRepository skuRepository;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository,
                          SkuRepository skuRepository) {
        this.vehicleRepository = vehicleRepository;
        this.skuRepository = skuRepository;
    }

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public VehicleService(SkuRepository skuRepository) {
        this.skuRepository = skuRepository;
    }

    public List<VehicleDTO> getVehicles() {
        return getVehicles(0, ParamsConst.DB_DEFAULT_SELECT_PAGE_SIZE);
    }

    public List<VehicleDTO> getVehicles(int offset, int pageSize) {
        List<VehicleDTO> vehicleList = vehicleRepository.selectAllVehicles(offset, pageSize);
        if (vehicleList == null) {
            vehicleList = new ArrayList<>();
        }
        return vehicleList;
    }

    public List<VehicleSkuDTO> getAllSkusFromOneVehicle(int vehicleId) {
        return getAllSkusFromOneVehicle(vehicleId, 0, ParamsConst.DB_DEFAULT_SELECT_PAGE_SIZE);
    }

    public List<VehicleSkuDTO> getAllSkusFromOneVehicle(int vehicleId, int offset, int pageSize) {
        List<VehicleSkuDTO> skuList = skuRepository.selectAllSkusFromOneVehicle(vehicleId, offset, pageSize);
        if (skuList == null) {
            skuList = new ArrayList<>();
        }
        return skuList;
    }
}
