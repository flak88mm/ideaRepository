package com.brt.device.rest.controller.BusinessDevice;

import com.brt.device.core.service.bussinessDevice.BusinessDeviceService;
import com.brt.device.rest.vo.ResEntity;
import com.brt.device.rest.vo.ViewBusinessDevice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: BusinessDeviceCommandController 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author Zeng Nan
 * @date 2019年10月14日 下午10:19:13
 */
@RestController
@Api(tags = "BusinessDeviceQueryController-业务设备管理接口")
public class BusinessDeviceCommandController {
    @Autowired
    BusinessDeviceService businessDeviceService;

    /**
     * @author Zeng Nan   
     * @date 2019年10月14日 下午10:19:52 
     * @Title: deleteBusinessDevice 
     * @Description: TODO(这里用一句话描述这个方法的作用) 
     * @param @param businessDeviceId
     * @param @return    设定文件 
     * @return ResEntity    返回类型 
     * @throws
     */
    @RequestMapping(value = "/businessDevice/{businessDeviceId}", method = RequestMethod.DELETE)
    @ApiOperation(value = "根据id删除业务设备", notes = "根据id删除业务设备")
    @ApiImplicitParam(paramType = "path", name = "businessDeviceId", value = "业务设备id", required = true, dataType = "Long")
    public ResEntity deleteBusinessDevice(@PathVariable("businessDeviceId") Long businessDeviceId) {
        if(businessDeviceId == null){
            return new ResEntity(null, false, "参数为空");
        }
        boolean resVo = businessDeviceService.deleteBusinessDevice(businessDeviceId);
        if (!resVo) {
            return new ResEntity(resVo, false, "删除业务设备失败");
        }
        return new ResEntity(resVo, true, "ok");
    }

    /**
     * @author Zeng Nan   
     * @date 2019年10月14日 下午10:20:00 
     * @Title: createBusinessDevice 
     * @Description: TODO(这里用一句话描述这个方法的作用) 
     * @param @param viewBusinessDevice
     * @param @return    设定文件 
     * @return ResEntity    返回类型 
     * @throws
     */
    @RequestMapping(value = "/businessDevice", method = RequestMethod.POST)
    @ApiOperation(value = "新增业务设备及其关联设备", notes = "新增业务设备及其关联设备")
    public ResEntity createBusinessDevice(@RequestBody ViewBusinessDevice viewBusinessDevice){
        if(viewBusinessDevice == null){
            return new ResEntity(null, false, "参数为空");
        }
        ViewBusinessDevice resVo = businessDeviceService.createBusinessDevice(viewBusinessDevice);
        if (resVo == null){
            return new ResEntity(null, false, "新增业务设备失败");
        }
        return new ResEntity(resVo, true, "ok");
    }

    /**
     * @author Zeng Nan   
     * @date 2019年10月14日 下午10:20:07 
     * @Title: upadteBusinessDevice 
     * @Description: TODO(这里用一句话描述这个方法的作用) 
     * @param @param viewBusinessDevice
     * @param @param businessDeviceId
     * @param @return    设定文件 
     * @return ResEntity    返回类型 
     * @throws
     */
    @RequestMapping(value = "/businessDevice/{businessDeviceId}", method = RequestMethod.PUT)
    @ApiOperation(value = "修改业务设备及其关联设备", notes = "修改业务设备及其关联设备")
    @ApiImplicitParam(paramType = "path", name = "businessDeviceId", value = "业务设备id", required = true, dataType = "Long")
    public ResEntity upadteBusinessDevice(@RequestBody ViewBusinessDevice viewBusinessDevice, @PathVariable Long businessDeviceId){
        if(viewBusinessDevice == null || businessDeviceId == null){
            return new ResEntity(null, false, "参数为空");
        }
        viewBusinessDevice.setId(businessDeviceId);
        ViewBusinessDevice resVo = businessDeviceService.updateBusinessDeviceByBusinessDeviceId(viewBusinessDevice);
        if (resVo == null){
            return new ResEntity(null, false, "更新业务设备失败");
        }
        return new ResEntity(resVo, true, "ok");
    }

}
