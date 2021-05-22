package com.pocket.retal.repository;

import com.pocket.retal.model.dto.PriceFrequencyDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PriceFrequencyRepository {
    @Select({"<script> " +
             "SELECT * FROM [pocket].[pocket_price_frequency]" +
            "</script>"})
    List<PriceFrequencyDTO> selectAllPriceFrequencies();
}
