package com.brt.device.repository;

import com.brt.device.entity.CameraDevice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CameraDeviceRepository extends JpaRepository<CameraDevice,Long> {
    Set<CameraDevice> findByBusinessDeviceId(Long businessDeviceId);
}
