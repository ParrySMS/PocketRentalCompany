package com.pocket.retal.service;

import com.pocket.retal.exception.PocketApiException;
import com.pocket.retal.model.*;
import com.pocket.retal.model.dto.RentalScheduleVehicleSkuDTO;
import com.pocket.retal.model.dto.VehicleDTO;
import com.pocket.retal.model.dto.VehicleSkuDTO;
import com.pocket.retal.repository.RentalScheduleRepository;
import com.pocket.retal.repository.SkuRepository;
import com.pocket.retal.repository.VehicleRepository;
import com.pocket.retal.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VehicleService {

    private VehicleRepository vehicleRepository;
    private SkuRepository skuRepository;
    private RentalScheduleRepository rentalScheduleRepository;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository,
                          SkuRepository skuRepository,
                          RentalScheduleRepository rentalScheduleRepository) {
        this.vehicleRepository = vehicleRepository;
        this.skuRepository = skuRepository;
        this.rentalScheduleRepository = rentalScheduleRepository;
    }

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public VehicleService(SkuRepository skuRepository) {
        this.skuRepository = skuRepository;
    }

    public List<VehicleDTO> getVehicles(Optional<Date> optionalStartDate, Optional<Date> optionalEndDate) {
        if (optionalStartDate.isEmpty() && optionalEndDate.isEmpty()) {
            return getVehicles();
        }

        if (optionalStartDate.isPresent() && optionalEndDate.isPresent()) {
            return getAvailableVehicles(
                    optionalStartDate.get(),
                    optionalEndDate.get());
        }

        if (optionalStartDate.isPresent()) {
            return getAvailableVehicles(
                    optionalStartDate.get(),
                    null);
        }

        // optionalEndDate.isPresent()
        return getAvailableVehicles(
                null,
                optionalEndDate.get());
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
            log.error("fixSkuAvailableDate but skuAvailableDate is null");
            throw new PocketApiException(PocketResponseStatus.SYSTEM_INTERNAL_ERROR);
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
            TimeInterval nextAvailableTimeInterval =
                    TimeInterval.builder()
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
}
