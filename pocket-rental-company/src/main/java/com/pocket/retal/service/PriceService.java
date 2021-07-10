package com.pocket.retal.service;

import com.pocket.retal.model.dto.PriceFrequencyDTO;
import com.pocket.retal.repository.PriceFrequencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceService {

    @Autowired
    private PriceFrequencyRepository priceFreqRepo;

    public List<PriceFrequencyDTO> getPriceFrequencies() {
        return priceFreqRepo.selectAllPriceFrequencies();
    }
}
