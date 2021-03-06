package com.pocket.retal.service;

import com.pocket.retal.exception.PocketApiException;
import com.pocket.retal.model.SkuAvailableDate;
import com.pocket.retal.model.SkuPrice;
import com.pocket.retal.model.SkuServiceDate;
import com.pocket.retal.model.TimeInterval;
import com.pocket.retal.model.VehicleSkuWithPrice;
import com.pocket.retal.model.constant.ParamsConst;
import com.pocket.retal.model.dto.RentalScheduleVehicleSkuDTO;
import com.pocket.retal.model.dto.SkuPriceDTO;
import com.pocket.retal.model.dto.VehicleDTO;
import com.pocket.retal.model.dto.VehicleSkuDTO;
import com.pocket.retal.model.enumeration.PocketResponseStatus;
import com.pocket.retal.model.enumeration.PriceFrequency;
import com.pocket.retal.repository.RentalScheduleRepository;
import com.pocket.retal.repository.SkuRepository;
import com.pocket.retal.repository.VehicleRepository;
import com.pocket.retal.util.DateUtil;
import com.pocket.retal.util.MathUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private SkuRepository skuRepository;
    @Autowired
    private RentalScheduleRepository rentalScheduleRepository;

    public List<VehicleDTO> getVehiclesWithDates(Date startDate, Date endDate) {
        if (startDate == null && endDate == null) {
            return getVehicles();
        }

        if (startDate != null && endDate != null) {
            return getAvailableVehicles(startDate, endDate);
        }

        if (startDate != null) {
            return getAvailableVehicles(startDate, null);
        }

        // endDate != null
        return getAvailableVehicles(null, endDate);
    }


    public List<VehicleDTO> getVehicles() {
        return getVehicles(0, ParamsConst.DB_DEFAULT_SELECT_PAGE_SIZE);
    }

    public List<VehicleDTO> getVehicles(int offset, int pageSize) {
        List<VehicleDTO> vehicleList = vehicleRepository.selectAllVehicles(offset, pageSize);
        if (vehicleList == null) {
            vehicleList = new ArrayList<>();
        }
        return vehicleList;
    }

    public List<VehicleDTO> getAvailableVehicles(Date startDate, Date endDate) {
        List<RentalScheduleVehicleSkuDTO> scheduleSkuList = getRentalScheduleVehicleSkus(startDate, endDate);
        if (scheduleSkuList == null || scheduleSkuList.isEmpty()) {
            return getVehicles();
        }

        Map<String, SkuAvailableDate> skuGuidMapSkuAvailableDate = new HashMap<>();

        //  init skus-freeDateList
        scheduleSkuList.stream()
                .map(scheduleVehicleSku -> buildFreeSkuAvailableDate(scheduleVehicleSku, startDate, endDate))
                .forEach(skuAvailableDate -> skuGuidMapSkuAvailableDate.put(skuAvailableDate.getSkuGuid(), skuAvailableDate));

        scheduleSkuList.stream()
                .map(this::buildSkuServiceDate)
                .forEach(skuServiceDate -> fixSkuAvailableDate(skuGuidMapSkuAvailableDate, skuServiceDate));

        List<Integer> availableVehicleIdList = skuGuidMapSkuAvailableDate.values().stream()
                .filter(skuAvailableDate -> skuAvailableDate.getAvailableDates() != null)
                .filter(skuAvailableDate -> !skuAvailableDate.getAvailableDates().isEmpty())
                .map(SkuAvailableDate::getVehicleId)
                .distinct()
                .collect(Collectors.toList());

        if (availableVehicleIdList.isEmpty()) {
            return new ArrayList<>();
        } else {
            return vehicleRepository.selectVehiclesByVehicleIdList(availableVehicleIdList);
        }
    }

    public List<VehicleSkuDTO> getAllSkusFromOneVehicle(int vehicleId) {
        return getAllSkusFromOneVehicle(vehicleId, 0, ParamsConst.DB_DEFAULT_SELECT_PAGE_SIZE);
    }

    public List<VehicleSkuDTO> getAllSkusFromOneVehicle(int vehicleId, int offset, int pageSize) {
        List<VehicleSkuDTO> skuList = skuRepository.selectAllSkusFromOneVehicle(vehicleId, offset, pageSize);
        if (skuList == null) {
            skuList = new ArrayList<>();
        }
        return skuList;
    }

    public VehicleSkuDTO getOneSku(int vehicleId, String skuGuid) {
        VehicleSkuDTO vehicleSkuDTO = skuRepository.selectOneSku(vehicleId, skuGuid);
        if (vehicleSkuDTO == null) {
            throw new PocketApiException(PocketResponseStatus.SYSTEM_INTERNAL_ERROR, "selectOneSku but vehicleSkuDTO null");
        }
        return vehicleSkuDTO;
    }

    public List<RentalScheduleVehicleSkuDTO> getRentalScheduleVehicleSkus(Date startDate, Date endDate) {
        List<RentalScheduleVehicleSkuDTO> rentalScheduleVehicleSkus = rentalScheduleRepository.getRentalScheduleVehicleSkus(startDate, endDate);
        if (rentalScheduleVehicleSkus == null) {
            rentalScheduleVehicleSkus = new ArrayList<>();
        }
        return rentalScheduleVehicleSkus;
    }

    public void fixSkuAvailableDate(Map<String, SkuAvailableDate> skuGuidMapSkuAvailableDate,
                                    SkuServiceDate skuServiceDate) {
        var skuGuid = skuServiceDate.getSkuGuid();
        var skuAvailableDate = skuGuidMapSkuAvailableDate.get(skuGuid);
        if (skuAvailableDate == null) {
            throw new PocketApiException(PocketResponseStatus.SYSTEM_INTERNAL_ERROR, "fixSkuAvailableDate but skuAvailableDate is null");
        }

        List<TimeInterval> finalAvailableTimeIntervalList = skuAvailableDate.getAvailableDates().stream()
                .map(availableTimeInterval -> {
                    var oneAvailableTimeIntervalList = skuServiceDate.getServiceDates().stream()
                            .map(serviceTimeInterval -> breakAvailableTimeInterval(serviceTimeInterval, availableTimeInterval))
                            .flatMap(Collection::stream)
                            .collect(Collectors.toList());
                    return oneAvailableTimeIntervalList;
                })
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        skuAvailableDate.setAvailableDates(finalAvailableTimeIntervalList);
    }

    public List<TimeInterval> breakAvailableTimeInterval(TimeInterval serviceTimeInterval,
                                                         TimeInterval availableTimeInterval) {
        // no intersection
        if (serviceTimeInterval.getEndDate().before(availableTimeInterval.getStartDate())
                || serviceTimeInterval.getStartDate().after(availableTimeInterval.getEndDate())) {
            return Collections.singletonList(availableTimeInterval);
        }

        boolean serviceStartBeforeOrEqualsAvailable =
                serviceTimeInterval.getStartDate().before(availableTimeInterval.getStartDate())
                        || serviceTimeInterval.getStartDate().equals(availableTimeInterval.getStartDate());

        boolean serviceEndAfterOrEqualsAvailable =
                serviceTimeInterval.getEndDate().after(availableTimeInterval.getEndDate())
                        || serviceTimeInterval.getEndDate().equals(availableTimeInterval.getEndDate());

        // service over available
        if (serviceStartBeforeOrEqualsAvailable && serviceEndAfterOrEqualsAvailable) {
            return new ArrayList<>();
        }

        // service take the front part from available
        if (serviceStartBeforeOrEqualsAvailable
                && serviceTimeInterval.getEndDate().after(availableTimeInterval.getStartDate())) {
            availableTimeInterval.setStartDate(
                    DateUtil.getNextDay(serviceTimeInterval.getEndDate()));
            return Collections.singletonList(availableTimeInterval);
        }

        // service take the mid part from available
        if (serviceTimeInterval.getStartDate().after(availableTimeInterval.getStartDate())
                && serviceTimeInterval.getEndDate().before(availableTimeInterval.getEndDate())) {
            TimeInterval nextAvailableTimeInterval = TimeInterval.builder()
                    .startDate(DateUtil.getNextDay(serviceTimeInterval.getEndDate()))
                    .endDate(availableTimeInterval.getEndDate())
                    .build();

            availableTimeInterval.setEndDate(
                    DateUtil.getLastDay(serviceTimeInterval.getStartDate()));

            return Arrays.asList(availableTimeInterval, nextAvailableTimeInterval);
        }

        // service take the back part from available
        if (serviceEndAfterOrEqualsAvailable
                && serviceTimeInterval.getStartDate().before(availableTimeInterval.getEndDate())) {
            availableTimeInterval.setEndDate(
                    DateUtil.getLastDay(serviceTimeInterval.getStartDate()));
            return Collections.singletonList(availableTimeInterval);
        }

        log.error("breakAvailableTimeInterval meet unknown case.");
        throw new PocketApiException(PocketResponseStatus.SYSTEM_INTERNAL_ERROR);
    }


    public SkuServiceDate buildSkuServiceDate(RentalScheduleVehicleSkuDTO scheduleVehicleSku) {
        TimeInterval serviceDates = TimeInterval.builder()
                .startDate(scheduleVehicleSku.getStartTime())
                .endDate(scheduleVehicleSku.getEndTime())
                .build();

        return SkuServiceDate.builder()
                .skuGuid(scheduleVehicleSku.getSkuGuid())
                .serviceDates(Collections.singletonList(serviceDates))
                .build();
    }

    public SkuAvailableDate buildFreeSkuAvailableDate(RentalScheduleVehicleSkuDTO scheduleVehicleSku,
                                                      Date timeIntervalStart,
                                                      Date timeIntervalEnd) {
        var availableDate = TimeInterval.builder()
                .startDate(timeIntervalStart)
                .endDate(timeIntervalEnd)
                .build();

        return SkuAvailableDate.builder()
                .skuGuid(scheduleVehicleSku.getSkuGuid())
                .vehicleId(scheduleVehicleSku.getVehicleId())
                .vehicleName(scheduleVehicleSku.getVehicleName())
                .vehicleModelYear(scheduleVehicleSku.getVehicleModelYear())
                .timeIntervalStart(timeIntervalStart)
                .timeIntervalEnd(timeIntervalEnd)
                .availableDates(Collections.singletonList(availableDate))
                .build();

    }

    public SkuPrice getSkuPriceInSelectedPeriod(String skuGuid, Date startDate, Date endDate) {
        int selectedPeriodDays = DateUtil.getDaysBetween(startDate, endDate);
        if (selectedPeriodDays == 0) {
            selectedPeriodDays = 1;
        }
        List<Integer> priceFrequencyIdList = initPriceFrequencyIdList(selectedPeriodDays);
        List<SkuPriceDTO> skuPriceDTOList = skuRepository.getSkuFrequencyPrice(skuGuid, priceFrequencyIdList);
        Map<Integer, String> priceFrequencyIdMapToPriceString = skuPriceDTOList.stream().collect(Collectors.toMap(
                SkuPriceDTO::getPriceFrequencyId,
                SkuPriceDTO::getPrice));

        Map<Integer, Integer> priceFrequencyIdMapToFactorUnit = initPriceFrequencyIdMapToFactorUnit(selectedPeriodDays);
        double skuPrice = getSkuPriceBySumAllFrequencyPrice(priceFrequencyIdList, priceFrequencyIdMapToPriceString, priceFrequencyIdMapToFactorUnit);
        double averageDailyPrice = MathUtil.div(skuPrice, selectedPeriodDays);
        return SkuPrice.builder()
                .skuPrice(skuPrice)
                .averageDailyPrice(averageDailyPrice)
                .build();
    }

    public VehicleSkuWithPrice getVehicleSkuWithPriceInSelectedPeriod(int vehicleId, String skuGuid, Date startDate, Date endDate) {
        SkuPrice skuPriceEntity = getSkuPriceInSelectedPeriod(skuGuid, startDate, endDate);
        VehicleSkuDTO vehicleSkuDTO = getOneSku(vehicleId, skuGuid);
        return VehicleSkuWithPrice.builder()
                .vehicleId(vehicleSkuDTO.getVehicleId())
                .skuGuid(vehicleSkuDTO.getSkuGuid())
                .color(vehicleSkuDTO.getColor())
                .skuPrice(Double.toString(skuPriceEntity.getSkuPrice()))
                .averageDailyPrice(Double.toString(skuPriceEntity.getAverageDailyPrice()))
                .build();
    }

    public double getSkuPriceBySumAllFrequencyPrice(List<Integer> priceFrequencyIdList, Map<Integer, String> priceFrequencyIdMapToPriceString, Map<Integer, Integer> priceFrequencyIdMapToFactorUnit) {
        double skuPrice = priceFrequencyIdList.stream()
                .mapToDouble(priceFrequencyId -> getOneFrequencyPrice(
                        priceFrequencyId,
                        priceFrequencyIdMapToPriceString,
                        priceFrequencyIdMapToFactorUnit))
                .sum();

        return MathUtil.round(skuPrice);
    }

    public double getOneFrequencyPrice(int keyPriceFrequencyId,
                                       Map<Integer, String> priceFrequencyIdMapToPriceString,
                                       Map<Integer, Integer> priceFrequencyIdMapToFactorUnit) {
        String priceString = priceFrequencyIdMapToPriceString.get(keyPriceFrequencyId);
        String factorUnitString = priceFrequencyIdMapToFactorUnit.get(keyPriceFrequencyId).toString();
        return MathUtil.multiplyWithScale(priceString, factorUnitString);

    }

    public void priceFrequencyIdMapIncrement(Map<Integer, Integer> priceFrequencyIdMapToFactorUnit, int key, int incrementUnit) {
        int value = priceFrequencyIdMapToFactorUnit.getOrDefault(key, 0);
        priceFrequencyIdMapToFactorUnit.put(key, value + incrementUnit);
    }

    private Map<Integer, Integer> initPriceFrequencyIdMapToFactorUnit(int selectedPeriodDays) {
        // <Weekly,5> mean 5 weeks
        Map<Integer, Integer> priceFrequencyIdMapToFactorUnit = PriceFrequency.getPriceFrequencyIdMapToFactorUnit(0);

        //split selectedPeriodDays into diff Frequency
        LinkedHashMap<Integer, Integer> dayUnitWithPriceFrequencyId = setUpDayUnitWithPriceFrequencyId();
        Set<Map.Entry<Integer, Integer>> entrySet = dayUnitWithPriceFrequencyId.entrySet();
        for (Map.Entry<Integer, Integer> entry : entrySet) {
            var dayUnit = entry.getKey();
            var priceFrequencyId = entry.getValue();
            while (selectedPeriodDays >= dayUnit) {
                selectedPeriodDays -= dayUnit;
                priceFrequencyIdMapIncrement(
                        priceFrequencyIdMapToFactorUnit,
                        priceFrequencyId,
                        1);
            }
        }
        return priceFrequencyIdMapToFactorUnit;
    }

    private LinkedHashMap<Integer, Integer> setUpDayUnitWithPriceFrequencyId() {
        // the order of dayUnitWithPriceFrequencyId should be max to min
        LinkedHashMap<Integer, Integer> dayUnitWithPriceFrequencyId = new LinkedHashMap();
        dayUnitWithPriceFrequencyId.put(DateUtil.YEARLY_DAY_UNIT, PriceFrequency.YEARLY.getPriceFrequencyId());
        dayUnitWithPriceFrequencyId.put(DateUtil.HALF_YEARLY_DAY_UNIT, PriceFrequency.HALF_YEARLY.getPriceFrequencyId());
        dayUnitWithPriceFrequencyId.put(DateUtil.MONTHLY_DAY_UNIT, PriceFrequency.MONTHLY.getPriceFrequencyId());
        dayUnitWithPriceFrequencyId.put(DateUtil.WEEKLY_DAY_UNIT, PriceFrequency.WEEKLY.getPriceFrequencyId());
        dayUnitWithPriceFrequencyId.put(DateUtil.DAILY_DAY_UNIT, PriceFrequency.DAILY.getPriceFrequencyId());
        return dayUnitWithPriceFrequencyId;
    }

    private List<Integer> initPriceFrequencyIdList(int selectedPeriodDays) {
        List<Integer> priceFrequencyIdList = new ArrayList<>();
        priceFrequencyIdList.add(PriceFrequency.DAILY.getPriceFrequencyId());
        if (selectedPeriodDays >= DateUtil.WEEKLY_DAY_UNIT) {
            priceFrequencyIdList.add(PriceFrequency.WEEKLY.getPriceFrequencyId());
        }
        if (selectedPeriodDays >= DateUtil.MONTHLY_DAY_UNIT) {
            priceFrequencyIdList.add(PriceFrequency.MONTHLY.getPriceFrequencyId());
        }
        if (selectedPeriodDays >= DateUtil.HALF_YEARLY_DAY_UNIT) {
            priceFrequencyIdList.add(PriceFrequency.HALF_YEARLY.getPriceFrequencyId());
        }
        if (selectedPeriodDays >= DateUtil.YEARLY_DAY_UNIT) {
            priceFrequencyIdList.add(PriceFrequency.YEARLY.getPriceFrequencyId());
        }
        return priceFrequencyIdList;
    }
}
