package com.pocket.retal.repository;

import com.pocket.retal.model.dto.RentalOrderDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RentalOrderRepository {
    @Insert({"<script> " +
            " INSERT [pocket].[pocket_rental_order] " +
            "   (client_id, signing_time, total_price, create_on, last_modified) " +
            " VALUES " +
            "   (#{insertOrder.clientId}, GETDATE(), #{insertOrder.totalPriceString}, GETDATE(), GETDATE())" +
            "</script>"})
    @Options(useGeneratedKeys = true, keyProperty = "insertOrder.orderId", keyColumn = "id")
    int insertOrder(@Param("insertOrder") RentalOrderDTO insertOrder);

    @Select({"<script> " +
            " SELECT " +
            "   id AS orderId," +
            "   client_id, " +
            "   signing_time, " +
            "   total_price " +
            " FROM [pocket].[pocket_rental_order] " +
            " WHERE client_id = #{clientId}" +
            "</script>"})
    List<RentalOrderDTO> selectAllOrders(int clientId);

    @Select({"<script> " +
            " SELECT " +
            "   id AS orderId," +
            "   client_id, " +
            "   signing_time, " +
            "   total_price " +
            " FROM [pocket].[pocket_rental_order] " +
            " WHERE client_id = #{clientId} " +
            "   AND id = #{orderId}" +
            "</script>"})
    RentalOrderDTO selectOneOrderByClient(int clientId, int orderId);
}
