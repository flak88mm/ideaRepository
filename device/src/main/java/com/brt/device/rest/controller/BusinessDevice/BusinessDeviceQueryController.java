package com.brt.device.rest.controller.BusinessDevice;

import com.brt.device.core.service.bussinessDevice.BusinessDeviceService;
import com.brt.device.rest.vo.ResEntity;
import com.brt.device.rest.vo.ViewBusinessDevice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName: BusinessDeviceQueryController 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author Zeng Nan
 * @date 2019年10月14日 下午10:20:25
 */
@RestController
@Api(tags = "BusinessDeviceQueryController-业务设备查询接口")
public class BusinessDeviceQueryController {
    @Autowired
    BusinessDeviceService businessDeviceService;

    /**
     * @author Zeng Nan   
     * @date 2019年10月14日 下午10:20:21 
     * @Title: getAllBusinessDevices 
     * @Description: TODO(这里用一句话描述这个方法的作用) 
     * @param @param pageSize
     * @param @param pageNum
     * @param @return    设定文件 
     * @return ResEntity    返回类型 
     * @throws
     */
    @RequestMapping(value = "/businessDevices", method = RequestMethod.GET)
    @ApiOperation(value = "查询所有业务设备", notes = "查询所有业务设备")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页返回数据条数", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "页码", required = true, dataType = "Integer")
    })
    public ResEntity getAllBusinessDevices (@RequestParam Integer pageSize, @RequestParam Integer pageNum){
        if(pageSize == null || pageNum == null){
            return new ResEntity(null, false, "参数为空");
        }
        List<ViewBusinessDevice> resVo = businessDeviceService.getAllBusinessDevices(pageSize,pageNum);
        if (resVo == null){
            return new ResEntity(resVo, false, "查询业务设备失败");
        }
        return new ResEntity(resVo, true, "ok");
    }

    /**
     * @author Zeng Nan   
     * @date 2019年10月14日 下午10:20:33 
     * @Title: getBusinessDeviceById 
     * @Description: TODO(这里用一句话描述这个方法的作用) 
     * @param @param businessDeviceId
     * @param @return    设定文件 
     * @return ResEntity    返回类型 
     * @throws
     */
    @RequestMapping(value = "/businessDevice/{businessDeviceId}", method = RequestMethod.GET)
    @ApiOperation(value = "根据id查询业务设备", notes = "根据id查询业务设备")
    @ApiImplicitParam(paramType = "path", name = "businessDeviceId", value = "业务设备id", required = true, dataType = "Long")
    public ResEntity getBusinessDeviceById (@PathVariable("businessDeviceId") Long businessDeviceId){
        if (businessDeviceId == null){
            return new ResEntity(null, false, "参数为空");
        }
        ViewBusinessDevice resVo = businessDeviceService.getBusinessDeviceByBusinessDeviceId(businessDeviceId);
        if (resVo == null){
            return new ResEntity(resVo, false, "该设备不存在");
        }
        return new ResEntity(resVo, true, "ok");
    }
}
