package com.pocket.retal.controller;


import com.pocket.retal.model.ApiResult;
import com.pocket.retal.model.InsertSkuOrderRequestBody;
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


    @PostMapping("")
    public ResponseEntity<ApiResult<RentalOrderDTO>> insertSkuOrder(
            @RequestBody InsertSkuOrderRequestBody insertSkuOrderRequestBody
    ) throws ParseException {
        Date startDate = ValidateUtil.parseDate(insertSkuOrderRequestBody.getStartDateStr(), null, true);
        Date endDate = ValidateUtil.parseDate(insertSkuOrderRequestBody.getEndDateStr(), null, true);
        Date today = new Date();
        ValidateUtil.Friendly.assertFalse(startDate.before(today),
                "startDate should not before today");
        ValidateUtil.Friendly.assertFalse(endDate.before(today),
                "endDate should not before today");
        ValidateUtil.Friendly.assertFalse(endDate.before(startDate),
                "endDate should not before startDate");
        ValidateUtil.Friendly.assertFalse(endDate.equals(startDate),
                "endDate should not be the same as startDate");
        String skuGuid = insertSkuOrderRequestBody.getSkuGuid();
        int clientId = insertSkuOrderRequestBody.getClientId();
        ValidateUtil.min(clientId, 1, "clientId");
        ValidateUtil.notNullOrEmptyOrBlank(skuGuid, "skuGuid");
        ValidateUtil.IsValidUUID(skuGuid, "skuGuid");
        return ApiResult.ok(rentalOrderService.createOrder(clientId, skuGuid, startDate, endDate));
    }

}
