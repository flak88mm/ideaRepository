package com.brt.device.repository;

import com.brt.device.entity.BusinessDevice;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName: BusinessDeviceRepository 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author Zeng Nan
 * @date 2019年10月14日 下午10:17:48
 */
public interface BusinessDeviceRepository extends JpaRepository<BusinessDevice,Long> {
    /**
     * @author Zeng Nan   
     * @date 2019年10月14日 下午10:18:48 
     * @Title: findByCode 
     * @Description: TODO(这里用一句话描述这个方法的作用) 
     * @param @param businessDeviceCode
     * @param @return    设定文件 
     * @return BusinessDevice    返回类型 
     * @throws
     */
	BusinessDevice findByCode(String businessDeviceCode);
}
