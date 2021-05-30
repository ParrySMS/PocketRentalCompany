package com.pocket.retal.repository;

import com.pocket.retal.model.dto.InsertOrderDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RentalOrderRepository {
    @Insert({"<script> " +
            " INSERT [pocket].[pocket_rental_order] " +
            "   (client_id, signing_time, total_price, create_on, last_modified) " +
            " VALUES " +
            "   (#{insertOrder.clientId}, GETDATE(), #{insertOrder.totalPriceString}, GETDATE(), GETDATE())" +
            "</script>"})
    @Options(useGeneratedKeys = true, keyProperty = "insertOrder.orderId", keyColumn = "id")
    int insertOrder(@Param("insertOrder") InsertOrderDTO insertOrder);
}
