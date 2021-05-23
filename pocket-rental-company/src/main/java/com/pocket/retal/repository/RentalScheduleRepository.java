package com.pocket.retal.repository;

import com.pocket.retal.model.dto.RentalScheduleVehicleSkuDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

@Mapper
public interface RentalScheduleRepository {
    @Select({"<script> " +
            " SELECT " +
            "   t_schedule.sku_guid," +
            "   t_schedule.start_time," +
            "   t_schedule.end_time," +
            "   t_schedule.schedule_price," +
            "   t_schedule.rental_order_id," +
            "   t_sku.vehicle_id," +
            "   t_vehicle.name AS vehicle_name," +
            "   t_vehicle.model_year AS vehicle_model_year " +
            " FROM [pocket].[pocket_rental_schedule] AS t_schedule " +
            " INNER JOIN [pocket].[pocket_sku] AS t_sku " +
            "   ON t_schedule.sku_guid = t_sku.sku_guid " +
            " INNER JOIN [pocket].[pocket_vehicle] AS t_vehicle " +
            "   ON t_sku.vehicle_id = t_vehicle.id " +
            "<where>" +
            "  <if test='startDate!=null and endDate!=null'>" +
            "    t_schedule.start_time >= #{startDate} AND t_schedule.end_time &lt;= #{endDate} " +
            "  </if>" +
            "  <if test='startDate!=null and endDate==null'>" +
            "    t_schedule.start_time >= #{startDate} " +
            "  </if>" +
            "  <if test='startDate==null and endDate!=null'>" +
            "    t_schedule.end_time &lt;= #{endDate} " +
            "  </if>" +
            "</where>" +
            "</script>"})
    List<RentalScheduleVehicleSkuDTO> getRentalScheduleVehicleSkus(Date startDate, Date endDate);
}
