package com.pocket.retal.controller;


import com.pocket.retal.model.ApiResult;
import com.pocket.retal.model.InsertSkuOrderRequestBody;
import com.pocket.retal.model.UpdateOneOrderRequestBody;
import com.pocket.retal.model.ValidDate;
import com.pocket.retal.model.dto.RentalOrderDTO;
import com.pocket.retal.service.RentalOrderService;
import com.pocket.retal.util.ValidateUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@Api
@RequestMapping("/orders")
public class OrderController {
    private RentalOrderService rentalOrderService;

    @Autowired
    public OrderController(RentalOrderService rentalOrderService) {
        this.rentalOrderService = rentalOrderService;
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<ApiResult<List<RentalOrderDTO>>> selectAllOrdersByClient(
            @PathVariable("clientId") int clientId
    ) {
        ValidateUtil.min(clientId, 1, "clientId");
        return ApiResult.ok(rentalOrderService.selectAllOrdersByClient(clientId));
    }

    @GetMapping("/{orderId}/client/{clientId}")
    public ResponseEntity<ApiResult<RentalOrderDTO>> selectOneOrderByClient(
            @PathVariable("clientId") int clientId,
            @PathVariable("orderId") int orderId
    ) {
        ValidateUtil.min(clientId, 1, "clientId");
        ValidateUtil.min(orderId, 1, "orderId");
        return ApiResult.ok(rentalOrderService.selectOneOrderByClient(clientId, orderId));
    }

    @PutMapping("{orderId}")
    public ResponseEntity<ApiResult<RentalOrderDTO>> addOneSchduleInExistingOrder(
            @PathVariable("orderId") int orderId,
            @RequestBody UpdateOneOrderRequestBody updateOneOrderRequestBody
    ) throws ParseException {
        ValidDate validDate = ValidateUtil.getValidDate(updateOneOrderRequestBody.getStartDateStr(), updateOneOrderRequestBody.getEndDateStr());
        Date startDate = validDate.getStartDate();
        Date endDate = validDate.getEndDate();
        int clientId = updateOneOrderRequestBody.getClientId();
        String skuGuid = updateOneOrderRequestBody.getSkuGuid();
        ValidateUtil.min(clientId, 1, "clientId");
        ValidateUtil.min(orderId, 1, "orderId");
        ValidateUtil.notNullOrEmptyOrBlank(skuGuid, "skuGuid");
        ValidateUtil.IsValidUUID(skuGuid, "skuGuid");
        return ApiResult.ok(rentalOrderService.addOneSchduleInExistingOrder(clientId, orderId, skuGuid, startDate, endDate));
    }

    @PostMapping("")
    public ResponseEntity<ApiResult<RentalOrderDTO>> insertSkuOrder(
            @RequestBody InsertSkuOrderRequestBody insertSkuOrderRequestBody
    ) throws ParseException {
        ValidDate validDate = ValidateUtil.getValidDate(insertSkuOrderRequestBody.getStartDateStr(), insertSkuOrderRequestBody.getEndDateStr());
        Date startDate = validDate.getStartDate();
        Date endDate = validDate.getEndDate();
        String skuGuid = insertSkuOrderRequestBody.getSkuGuid();
        int clientId = insertSkuOrderRequestBody.getClientId();
        ValidateUtil.min(clientId, 1, "clientId");
        ValidateUtil.notNullOrEmptyOrBlank(skuGuid, "skuGuid");
        ValidateUtil.IsValidUUID(skuGuid, "skuGuid");
        return ApiResult.ok(rentalOrderService.createOrder(clientId, skuGuid, startDate, endDate));
    }

}
