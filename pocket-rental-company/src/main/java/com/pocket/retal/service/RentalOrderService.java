package com.pocket.retal.service;

import com.pocket.retal.model.SkuPrice;
import com.pocket.retal.model.dto.RentalOrderDTO;
import com.pocket.retal.repository.RentalOrderRepository;
import com.pocket.retal.repository.RentalScheduleRepository;
import com.pocket.retal.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public RentalOrderDTO createOrder(int clientId, String skuGuid, Date startDate, Date endDate) {
        SkuPrice skuPrice = vehicleService.getSkuPriceInSelectedPeriod(skuGuid, startDate, endDate);
        String skuPriceString = skuPrice.getSkuPrice().toString();
        RentalOrderDTO rentalOrderDTO = RentalOrderDTO.builder()
                .clientId(clientId)
                .totalPrice(skuPriceString)
                .build();
        int effectRowsOnRentalOrder = rentalOrderRepo.insertOrder(rentalOrderDTO);
        int newOrderId = rentalOrderDTO.getOrderId();
        int effectRowsOnRentalSchedule = rentalScheduleRepo.insertSchedule(skuGuid, startDate, endDate, skuPriceString, newOrderId);
        ValidateUtil.effectRowsAssert(1, effectRowsOnRentalOrder, "rentalOrderRepo");
        ValidateUtil.effectRowsAssert(1, effectRowsOnRentalSchedule, "rentalScheduleRepo");
        return rentalOrderDTO;
    }

    public List<RentalOrderDTO> selectAllOrdersByClient(int clientId) {
        List<RentalOrderDTO> rentalOrderDTOList = rentalOrderRepo.selectAllOrders(clientId);
        if (rentalOrderDTOList.isEmpty()) {
            rentalOrderDTOList = new ArrayList<>();
        }
        return rentalOrderDTOList;
    }

    public RentalOrderDTO selectOneOrderByClient(int clientId, int orderId) {
        return rentalOrderRepo.selectOneOrderByClient(clientId, orderId);
    }
}
