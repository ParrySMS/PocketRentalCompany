package com.pocket.retal.repository;

import com.pocket.retal.model.dto.VehicleSkuDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SkuRepository {
    @Select({"<script> " +
            " SELECT " +
            "   sku_guid, " +
            "   vehicle_id, " +
            "   color " +
            " FROM [pocket].[pocket_sku] " +
            " WHERE vehicle_id = #{vehicleId} " +
            " ORDER BY id" +
            " offset #{pageSize} * #{offset} rows FETCH NEXT #{pageSize} rows ONLY" +
            "</script>"})
    List<VehicleSkuDTO> selectAllSkusFromOneVehicle(int vehicleId, int offset, int pageSize);
}
