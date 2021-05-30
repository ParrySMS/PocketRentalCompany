package com.pocket.retal.service;

import com.pocket.retal.model.SkuPrice;
import com.pocket.retal.model.dto.InsertOrderDTO;
import com.pocket.retal.repository.RentalOrderRepository;
import com.pocket.retal.repository.RentalScheduleRepository;
import com.pocket.retal.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RentalOrderService {

    private VehicleService vehicleService;
    private RentalOrderRepository rentalOrderRepo;
    private RentalScheduleRepository rentalScheduleRepo;

    @Autowired
    public RentalOrderService(VehicleService vehicleService,
                              RentalOrderRepository rentalOrderRepo,
                              RentalScheduleRepository rentalScheduleRepo) {
        this.vehicleService = vehicleService;
        this.rentalOrderRepo = rentalOrderRepo;
        this.rentalScheduleRepo = rentalScheduleRepo;
    }

    public InsertOrderDTO createOrder(int clientId, String skuGuid, Date startDate, Date endDate) {
        SkuPrice skuPrice = vehicleService.getSkuPriceInSelectedPeriod(skuGuid, startDate, endDate);
        String skuPriceString = skuPrice.getSkuPrice().toString();
        InsertOrderDTO insertOrderDTO = InsertOrderDTO.builder()
                .clientId(clientId)
                .totalPriceString(skuPriceString)
                .build();
        int effectRowsOnRentalOrder = rentalOrderRepo.insertOrder(insertOrderDTO);
        int orderId = insertOrderDTO.getOrderId();
        int effectRowsOnRentalSchedule = rentalScheduleRepo.insertSchedule(skuGuid, startDate, endDate, skuPriceString, orderId);
        ValidateUtil.effectRowsAssert(1, effectRowsOnRentalOrder, "rentalOrderRepo");
        ValidateUtil.effectRowsAssert(1, effectRowsOnRentalSchedule, "rentalScheduleRepo");
        return insertOrderDTO;
    }

}
