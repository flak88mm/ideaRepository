package com.brt.device.repository;

import com.brt.device.entity.CameraDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @ClassName: CameraDeviceRepository 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author Zeng Nan
 * @date 2019年10月14日 下午10:17:53
 */
public interface CameraDeviceRepository extends JpaRepository<CameraDevice,Long> {
    /**
     * @author Zeng Nan   
     * @date 2019年10月14日 下午10:18:13 
     * @Title: findByBusinessDeviceId 
     * @Description: TODO(这里用一句话描述这个方法的作用) 
     * @param @param businessDeviceId
     * @param @return    设定文件 
     * @return List<CameraDevice>    返回类型 
     * @throws
     */
	List<CameraDevice> findByBusinessDeviceId(Long businessDeviceId);

	/**
	 * @author Zeng Nan   
	 * @date 2019年10月14日 下午10:18:18 
	 * @Title: deleteById 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param cameraDeviceId    设定文件 
	 * @return     返回类型 
	 * @throws
	 */
    @Modifying
    @Query(value = "delete from t_camera_device where id=?1",nativeQuery = true)
    void deleteById(Long cameraDeviceId);
}
