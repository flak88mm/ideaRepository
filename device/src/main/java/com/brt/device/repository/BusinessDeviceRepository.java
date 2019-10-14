package com.brt.device.repository;

import com.brt.device.entity.BusinessDevice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessDeviceRepository extends JpaRepository<BusinessDevice,Long> {
    BusinessDevice findByCode(String businessDeviceCode);
}
