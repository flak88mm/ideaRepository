package com.brt.device.rest.controller.BusinessDevice;

import com.brt.device.core.service.bussinessDevice.BusinessDeviceService;
import com.brt.device.entity.BusinessDevice;
import com.brt.device.entity.CameraDevice;
import com.brt.device.repository.BusinessDeviceRepository;
import com.brt.device.repository.CameraDeviceRepository;
import com.brt.device.rest.vo.ResEntity;
import com.brt.device.rest.vo.ViewBusinessDevice;
import com.brt.device.rest.vo.ViewCameraDevice;
import com.fasterxml.jackson.databind.util.BeanUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import sun.misc.Request;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@Api(tags = "BusinessDeviceQueryController-业务设备管理接口")
public class BusinessDeviceCommandController {
    @Autowired
    BusinessDeviceService businessDeviceService;

    /**
     * 根据id删除业务设备
     * @param id
     * @return
     */
    @RequestMapping(value = "/businessDevice/{businessDeviceId}", method = RequestMethod.DELETE)
    @ApiOperation(value = "根据id删除业务设备", notes = "根据id删除业务设备")
    @ApiImplicitParam(paramType = "path", name = "businessDeviceId", value = "业务设备id", required = true, dataType = "Long")
    public ResEntity deleteBusinessDevice(@PathVariable("businessDeviceId") Long id) {
        if(id == null){
            return new ResEntity(null, false, "参数为空");
        }
        boolean resVo = businessDeviceService.deleteBusinessDevice(id);
        if (!resVo) {
            return new ResEntity(resVo, false, "删除业务设备失败");
        }
        return new ResEntity(resVo, true, "成功时该信息无效");
    }

    /**
     * 新增业务设备及其关联设备
     * @param viewBusinessDevice
     * @return
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
        return new ResEntity(resVo, true, "成功时该信息无效");
    }

    /**
     * 修改业务设备及其关联设备
     * @param viewBusinessDevice
     * @return
     */
    @RequestMapping(value = "/businessDevice/{businessDeviceId}", method = RequestMethod.PUT)
    @ApiOperation(value = "修改业务设备及其关联设备", notes = "修改业务设备及其关联设备")
    @ApiImplicitParam(paramType = "path", name = "businessDeviceId", value = "业务设备id", required = true, dataType = "Long")
    public ResEntity upadteBusinessDevice(@RequestBody ViewBusinessDevice viewBusinessDevice, @PathVariable Long businessDeviceId){
        if(viewBusinessDevice == null || businessDeviceId == null){
            return new ResEntity(null, false, "参数为空");
        }
        viewBusinessDevice.setId(businessDeviceId);
        ViewBusinessDevice resVo = businessDeviceService.updateBusinessDevice(viewBusinessDevice);
        if (resVo == null){
            return new ResEntity(null, false, "更新业务设备失败");
        }
        return new ResEntity(resVo, true, "成功时该信息无效");
    }

}
