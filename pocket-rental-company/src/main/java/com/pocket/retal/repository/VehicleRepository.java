package com.pocket.retal.repository;

import com.pocket.retal.model.dto.VehicleDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface VehicleRepository {
    @Select({"<script> " +
            " SELECT " +
            "   t_vehicle.id, " +
            "   t_vehicle.name, " +
            "   t_vehicle.model_year, " +
            "   t_description.description " +
            " FROM [pocket].[pocket_vehicle] AS t_vehicle " +
            " LEFT JOIN [pocket].[pocket_vehicle_description] AS t_description ON " +
            "   t_vehicle.id = t_description.vehicle_id" +
            " ORDER BY t_vehicle.id" +
            " offset #{pageSize} * #{offset} rows FETCH NEXT #{pageSize} rows ONLY" +
            "</script>"})
    List<VehicleDTO> selectAllVehicles(int offset, int pageSize);

    @Select({"<script> " +
            " SELECT " +
            "   t_vehicle.id, " +
            "   t_vehicle.name, " +
            "   t_vehicle.model_year, " +
            "   t_description.description " +
            " FROM [pocket].[pocket_vehicle] AS t_vehicle " +
            " LEFT JOIN [pocket].[pocket_vehicle_description] AS t_description ON " +
            "   t_vehicle.id = t_description.vehicle_id " +
            "<where> " +
            "   <if test='availableVehicleIdList!=null'>" +
            "       t_vehicle.id IN" +
            "       <foreach collection='availableVehicleIdList' item='availableVehicleId' open='(' separator=',' close=')'> " +
            "        #{availableVehicleId} " +
            "      </foreach>" +
            "  </if>" +
            "</where> " +
            "</script>"})
    List<VehicleDTO> selectVehiclesByVehicleIdList(@Param("availableVehicleIdList") List<Integer> availableVehicleIdList);
}
