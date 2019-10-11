package com.brt.device.repository;

import com.brt.device.entity.ReportDevice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportDeviceRepository extends JpaRepository<ReportDevice,Long> {
    ReportDevice findByBusinessDeviceId(Long businessDeviceId);
}
