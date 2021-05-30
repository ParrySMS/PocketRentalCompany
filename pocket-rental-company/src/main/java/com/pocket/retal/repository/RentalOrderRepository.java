package com.pocket.retal.repository;

import com.pocket.retal.model.dto.RentalOrderDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
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
            "   rentalOrder.id AS orderId," +
            "   rentalOrder.client_id, " +
            "   rentalOrder.signing_time, " +
            "   rentalOrder.total_price, " +
            "   schedule.sku_guid, " +
            "   schedule.start_time, " +
            "   schedule.end_time, " +
            "   schedule.schedule_price " +
            " FROM [pocket].[pocket_rental_order] AS rentalOrder " +
            " LEFT JOIN [pocket].[pocket_rental_schedule] AS schedule " +
            "   ON rentalOrder.id = schedule.rental_order_id" +
            " WHERE rentalOrder.client_id = #{clientId} " +
            "   AND rentalOrder.id = #{orderId}" +
            "</script>"})
    @Results(
            value = {
                    @Result(property = "rentalScheduleDTO.skuGuid", column = "sku_guid"),
                    @Result(property = "rentalScheduleDTO.startTime", column = "start_time"),
                    @Result(property = "rentalScheduleDTO.endTime", column = "end_time"),
                    @Result(property = "rentalScheduleDTO.schedulePrice", column = "schedule_price")
            })
    RentalOrderDTO selectOneOrderByClient(int clientId, int orderId);
}
