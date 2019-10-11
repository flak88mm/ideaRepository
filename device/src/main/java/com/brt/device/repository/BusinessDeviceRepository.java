package com.brt.device.repository;

import com.brt.device.entity.BusinessDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BusinessDeviceRepository extends JpaRepository<BusinessDevice,Long> {
    @Query(value = "select id,code,name,detail,device_type,create_time,update_time,reserve " +
            "from t_business_device limit ?1,?2",nativeQuery = true)
    List<BusinessDevice> findAllBusinessDevicesByPage(int offset, int size);
}
