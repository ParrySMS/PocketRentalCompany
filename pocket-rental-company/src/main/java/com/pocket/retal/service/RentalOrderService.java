package com.pocket.retal.service;

import com.pocket.retal.model.SkuPrice;
import com.pocket.retal.model.dto.RentalOrderDTO;
import com.pocket.retal.model.dto.RentalScheduleDTO;
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

    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private RentalOrderRepository rentalOrderRepo;
    @Autowired
    private RentalScheduleRepository rentalScheduleRepo;

    public RentalOrderDTO createOrder(int clientId, String skuGuid, Date startDate, Date endDate) {
        SkuPrice skuPrice = vehicleService.getSkuPriceInSelectedPeriod(skuGuid, startDate, endDate);
        String skuPriceString = skuPrice.getSkuPrice().toString();
        RentalOrderDTO rentalOrderDTO = RentalOrderDTO.builder()
                .clientId(clientId)
                .totalPrice(skuPriceString)
                .build();
        int effectRowsOnRentalOrder = rentalOrderRepo.insertOrder(rentalOrderDTO);
        int newOrderId = rentalOrderDTO.getOrderId();
        //TODO check available time before insertSchedule
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
        List<RentalScheduleDTO> rentalScheduleList = rentalOrderRepo.selectAllSchedulesInOneOrderByClient(clientId, orderId);
        RentalOrderDTO rentalOrderDTO = rentalOrderRepo.selectOneOrderByClient(clientId, orderId);
        rentalOrderDTO.setRentalScheduleDTO(rentalScheduleList);
        return rentalOrderDTO;
    }

    public RentalOrderDTO addOneSchduleInExistingOrder(int clientId, int orderId, String skuGuid, Date startDate, Date endDate) {
        SkuPrice skuPrice = vehicleService.getSkuPriceInSelectedPeriod(skuGuid, startDate, endDate);
        String skuPriceString = skuPrice.getSkuPrice().toString();
        //TODO check available time before insertSchedule
        int effectRowsOnRentalSchedule = rentalScheduleRepo.insertSchedule(skuGuid, startDate, endDate, skuPriceString, orderId);
        ValidateUtil.effectRowsAssert(1, effectRowsOnRentalSchedule, "rentalScheduleRepo");
        // TODO: S1: get total price , lock, add , then update
        //  S2: handle in SQL to update by self ..
        int effectRowsOnRentalOrder = rentalOrderRepo.updateTotalPrice(orderId, skuPriceString);
//        ValidateUtil.effectRowsAssert(1, effectRowsOnRentalOrder, "rentalOrderRepo");

        return selectOneOrderByClient(clientId, orderId);
    }
}
