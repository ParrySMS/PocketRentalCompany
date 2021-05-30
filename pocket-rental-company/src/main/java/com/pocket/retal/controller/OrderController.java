package com.pocket.retal.controller;


import com.pocket.retal.model.ApiResult;
import com.pocket.retal.model.SkuOrder;
import com.pocket.retal.model.dto.InsertOrderDTO;
import com.pocket.retal.service.RentalOrderService;
import com.pocket.retal.util.ValidateUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;

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

    @PostMapping("")
    public ResponseEntity<ApiResult<InsertOrderDTO>> createOrder(
            @RequestBody SkuOrder skuOrder
    ) throws ParseException {
        int mockClientId = 999;
        Date startDate = ValidateUtil.parseDate(skuOrder.getStartDateStr(), null, true);
        Date endDate = ValidateUtil.parseDate(skuOrder.getEndDateStr(), null, true);
        Date today = new Date();
        ValidateUtil.Friendly.assertFalse(startDate.before(today),
                "startDate should not before today");
        ValidateUtil.Friendly.assertFalse(endDate.before(today),
                "endDate should not before today");
        ValidateUtil.Friendly.assertFalse(endDate.before(startDate),
                "endDate should not before startDate");
        ValidateUtil.Friendly.assertFalse(endDate.equals(startDate),
                "endDate should not be the same as startDate");
        ValidateUtil.notNullOrEmptyOrBlank(skuOrder.getSkuGuid(), "skuGuid");
        return ApiResult.ok(rentalOrderService.createOrder(mockClientId, skuOrder.getSkuGuid(), startDate, endDate));
    }

}
