package com.brt.device.core.service.bussinessDevice;

import com.brt.device.rest.vo.ViewBusinessDevice;

import java.util.List;

/**
 * @ClassName: BusinessDeviceService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author Zeng Nan
 * @date 2019年10月14日 下午10:10:59
 */
public interface BusinessDeviceService {
	/**
	 * @author Zeng Nan   
	 * @date 2019年10月14日 下午10:11:07 
	 * @Title: deleteBusinessDevice 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param businessDeviceid
	 * @param @return    设定文件 
	 * @return boolean    返回类型 
	 * @throws
	 */
    boolean deleteBusinessDevice(Long businessDeviceid);

    /**
     * @author Zeng Nan   
     * @date 2019年10月14日 下午10:12:54 
     * @Title: createBusinessDevice 
     * @Description: TODO(这里用一句话描述这个方法的作用) 
     * @param @param viewBusinessDevice
     * @param @return    设定文件 
     * @return ViewBusinessDevice    返回类型 
     * @throws
     */
    ViewBusinessDevice createBusinessDevice(ViewBusinessDevice viewBusinessDevice);

    /**
     * @author Zeng Nan   
     * @date 2019年10月14日 下午10:13:07 
     * @Title: getBusinessDeviceByBusinessDeviceId 
     * @Description: TODO(这里用一句话描述这个方法的作用) 
     * @param @param businessDeviceId
     * @param @return    设定文件 
     * @return ViewBusinessDevice    返回类型 
     * @throws
     */
    ViewBusinessDevice getBusinessDeviceByBusinessDeviceId(Long businessDeviceId);

    /**
     * @author Zeng Nan   
     * @date 2019年10月14日 下午10:13:12 
     * @Title: getAllBusinessDevices 
     * @Description: TODO(这里用一句话描述这个方法的作用) 
     * @param @param pageSize
     * @param @param pageNum
     * @param @return    设定文件 
     * @return List<ViewBusinessDevice>    返回类型 
     * @throws
     */
    List<ViewBusinessDevice> getAllBusinessDevices(Integer pageSize, Integer pageNum);

    /**
     * @author Zeng Nan   
     * @date 2019年10月14日 下午10:13:17 
     * @Title: updateBusinessDeviceByBusinessDeviceId 
     * @Description: TODO(这里用一句话描述这个方法的作用) 
     * @param @param viewBusinessDevice
     * @param @return    设定文件 
     * @return ViewBusinessDevice    返回类型 
     * @throws
     */
    ViewBusinessDevice updateBusinessDeviceByBusinessDeviceId(ViewBusinessDevice viewBusinessDevice);
}
