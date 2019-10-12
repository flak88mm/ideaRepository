package com.brt.device.repository;

import com.brt.device.entity.CameraDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface CameraDeviceRepository extends JpaRepository<CameraDevice,Long> {
    Set<CameraDevice> findByBusinessDeviceId(Long businessDeviceId);

    @Modifying
    @Query(value = "delete from t_camera_device where id=?1",nativeQuery = true)
    void deleteById(Long cameraDeviceId);
}
