package com.brt.device.repository;

import com.brt.device.entity.LocationDevice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationDeviceRepository extends JpaRepository<LocationDevice,Long> {
    LocationDevice findByBusinessDeviceId(Long businessDeviceId);
}
