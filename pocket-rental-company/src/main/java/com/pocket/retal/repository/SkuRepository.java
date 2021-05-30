package com.pocket.retal.repository;

import com.pocket.retal.model.dto.SkuPriceDTO;
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

    @Select({"<script> " +
            " SELECT " +
            "   p.sku_guid, " +
            "   p.price_frequency_id, " +
            "   p.price, " +
            "   pf.frequency_type " +
            " FROM [pocket].[pocket_sku_price] AS p " +
            " LEFT JOIN [pocket].[pocket_price_frequency] AS pf " +
            "   ON p.price_frequency_id = pf.id " +
            " WHERE p.sku_guid = #{skuGuid} " +
            "   AND p.price_frequency_id IN " +
            "<where> " +
            "   p.sku_guid = #{skuGuid} " +
            "   <if test='priceFrequencyIdList != null'> " +
            "       AND p.price_frequency_id IN " +
            "       <foreach collection='priceFrequencyIdList' item='priceFrequencyId' open='(' separator=',' close=')'> " +
            "        #{priceFrequencyId} " +
            "      </foreach>" +
            "  </if>" +
            "</where> " +
            "</script>"})
    List<SkuPriceDTO> getSkuFrequencyPrice(String skuGuid, List<Integer> priceFrequencyIdList);

    @Select({"<script> " +
            " SELECT " +
            "   sku_guid, " +
            "   vehicle_id, " +
            "   color " +
            " FROM [pocket].[pocket_sku] " +
            " WHERE sku_guid = #{skuGuid} " +
            "   AND vehicle_id = #{vehicleId} " +
            "</script>"})
    VehicleSkuDTO selectOneSku(int vehicleId, String skuGuid);
}
