package com.brt.device.repository;

import com.brt.device.entity.ReportDevice;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName: ReportDeviceRepository 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author Zeng Nan
 * @date 2019年10月14日 下午10:19:05
 */
public interface ReportDeviceRepository extends JpaRepository<ReportDevice,Long> {
    /**
     * @author Zeng Nan   
     * @date 2019年10月14日 下午10:18:32 
     * @Title: findByBusinessDeviceId 
     * @Description: TODO(这里用一句话描述这个方法的作用) 
     * @param @param businessDeviceId
     * @param @return    设定文件 
     * @return ReportDevice    返回类型 
     * @throws
     */
	ReportDevice findByBusinessDeviceId(Long businessDeviceId);
}
