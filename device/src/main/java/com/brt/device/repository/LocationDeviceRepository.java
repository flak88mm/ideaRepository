package com.brt.device.repository;

import com.brt.device.entity.LocationDevice;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName: LocationDeviceRepository 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author Zeng Nan
 * @date 2019年10月14日 下午10:18:59
 */
public interface LocationDeviceRepository extends JpaRepository<LocationDevice,Long> {
    /**
     * @author Zeng Nan   
     * @date 2019年10月14日 下午10:18:25 
     * @Title: findByBusinessDeviceId 
     * @Description: TODO(这里用一句话描述这个方法的作用) 
     * @param @param businessDeviceId
     * @param @return    设定文件 
     * @return LocationDevice    返回类型 
     * @throws
     */
	LocationDevice findByBusinessDeviceId(Long businessDeviceId);
}
