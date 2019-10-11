package com.brt.device.core.service.bussinessDevice;

import com.brt.device.entity.BusinessDevice;
import com.brt.device.entity.CameraDevice;
import com.brt.device.entity.LocationDevice;
import com.brt.device.entity.ReportDevice;
import com.brt.device.repository.BusinessDeviceRepository;
import com.brt.device.repository.CameraDeviceRepository;
import com.brt.device.repository.LocationDeviceRepository;
import com.brt.device.repository.ReportDeviceRepository;
import com.brt.device.rest.vo.ViewBusinessDevice;
import com.brt.device.rest.vo.ViewCameraDevice;
import com.brt.device.rest.vo.ViewLocationDevice;
import com.brt.device.rest.vo.ViewReportDevice;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.swing.text.View;
import java.util.*;

@Service
public class BusinessDeviceServiceImpl implements BusinessDeviceService {
    @Autowired
    BusinessDeviceRepository businessDeviceRepository;
    @Autowired
    LocationDeviceRepository locationDeviceRepository;
    @Autowired
    ReportDeviceRepository reportDeviceRepository;
    @Autowired
    CameraDeviceRepository cameraDeviceRepository;

    /**
     * 根据id删除业务设备及其关联设备
     * @param id 业务设备id
     * @return true：功能实现成功，false：功能实现失败。
     */
    @Override
    public boolean deleteBusinessDevice(Long id) {
        Optional<BusinessDevice> businessDeviceOpt = businessDeviceRepository.findById(id);
        if (!businessDeviceOpt.isPresent()) {
            return false;
        }
        BusinessDevice businessDevice = businessDeviceOpt.get();
        LocationDevice locationDevice = businessDevice.getLocationDevice();
        ReportDevice reportDevice = businessDevice.getReportDevice();
        Set<CameraDevice> cameraDeviceSet = businessDevice.getCameraDevices();
        if (!(locationDevice == null) || !(reportDevice == null) || !(cameraDeviceSet.isEmpty())) {
            //该业务设备有关联设备
            return false;
        }
        businessDeviceRepository.deleteById(id);
        Optional<BusinessDevice> businessDeviceRes = businessDeviceRepository.findById(id);
        if (!businessDeviceRes.isPresent()) {
            return true;
        }
        return false;
    }

    /**
     * 新增业务设备及其关联设备
     * @param viewBusinessDevice 业务设备及其关联设备信息
     * @return 业务设备及其关联设备信息
     */
    @Override
    public ViewBusinessDevice createBusinessDevice(ViewBusinessDevice viewBusinessDevice) {
        if(viewBusinessDevice == null){
            return null;
        }
        BusinessDevice businessDevice = new BusinessDevice();
        BeanUtils.copyProperties(viewBusinessDevice, businessDevice);
        Date date = new Date();
        businessDevice.setCreateTime(date);
        businessDevice.setUpdateTime(date);
        businessDeviceRepository.save(businessDevice);

        //该业务设备关联定位设备
        LocationDevice locationDevice = new LocationDevice();
        ViewLocationDevice viewLocationDevice = viewBusinessDevice.getViewLocationDevice();
        BeanUtils.copyProperties(viewLocationDevice, locationDevice);
        locationDevice.setCreateTime(date);
        locationDevice.setUpdateTime(date);
        locationDevice.setBusinessDevice(businessDevice);
        locationDeviceRepository.save(locationDevice);
        //更新vo中id相关数据
        viewLocationDevice.setId(locationDevice.getId());
        viewLocationDevice.setBusinessDeviceId(businessDevice.getId());

        //该业务设备关联报警设备
        ReportDevice reportDevice = new ReportDevice();
        ViewReportDevice viewReportDevice = viewBusinessDevice.getViewReportDevice();
        BeanUtils.copyProperties(viewReportDevice, reportDevice);
        reportDevice.setCreateTime(date);
        reportDevice.setUpdateTime(date);
        reportDevice.setBusinessDevice(businessDevice);
        reportDeviceRepository.save(reportDevice);
        //更新vo中id相关数据
        viewReportDevice.setId(reportDevice.getId());
        viewReportDevice.setBusinessDeviceId(businessDevice.getId());

        //该业务设备关联摄像头设备
        Set<ViewCameraDevice> viewCameraDeviceSet = viewBusinessDevice.getViewCameraDevices();
        for (ViewCameraDevice viewCameraDevice : viewCameraDeviceSet) {
            CameraDevice cameraDevice = new CameraDevice();
            BeanUtils.copyProperties(viewCameraDevice, cameraDevice);
            cameraDevice.setCreateTime(date);
            cameraDevice.setUpdateTime(date);
            cameraDevice.setBusinessDevice(businessDevice);
            cameraDeviceRepository.save(cameraDevice);
            //更新vo中id相关数据
            viewCameraDevice.setId(cameraDevice.getId());
            viewCameraDevice.setBusinessDeviceId(businessDevice.getId());
        }
        viewBusinessDevice.setId(businessDevice.getId());
        return viewBusinessDevice;
    }

    /**
     * 根据id查询业务设备及其关联设备信息
     * @param businessDeviceId 业务设备id
     * @return 业务设备及其关联设备信息
     */
    @Override
    public ViewBusinessDevice getBusinessDeviceById(Long businessDeviceId) {
        Optional<BusinessDevice> businessDeviceOpt = businessDeviceRepository.findById(businessDeviceId);
        if (!businessDeviceOpt.isPresent()) {
            return null;
        }
        BusinessDevice businessDevice = businessDeviceOpt.get();

        System.err.println(businessDevice);

        ViewBusinessDevice viewBusinessDevice = new ViewBusinessDevice();
        BeanUtils.copyProperties(businessDevice, viewBusinessDevice);
        //查询关联定位设备
        LocationDevice locationDevice = locationDeviceRepository.findByBusinessDeviceId(businessDeviceId);
        if (!(locationDevice == null)) {
            ViewLocationDevice viewLocationDevice = new ViewLocationDevice();
            BeanUtils.copyProperties(locationDevice, viewLocationDevice);
            viewLocationDevice.setBusinessDeviceId(businessDeviceId);
            viewBusinessDevice.setViewLocationDevice(viewLocationDevice);
        }
        //查询关联报警设备
        ReportDevice reportDevice = reportDeviceRepository.findByBusinessDeviceId(businessDeviceId);
        if (!(reportDevice == null)) {
            ViewReportDevice viewReportDevice = new ViewReportDevice();
            BeanUtils.copyProperties(reportDevice, viewReportDevice);
            viewReportDevice.setBusinessDeviceId(businessDeviceId);
            viewBusinessDevice.setViewReportDevice(viewReportDevice);
        }
        //查询关联摄像头设备组
        Set<CameraDevice> cameraDeviceSet = cameraDeviceRepository.findByBusinessDeviceId(businessDeviceId);
        if (!(cameraDeviceSet == null) && !cameraDeviceSet.isEmpty()) {
            Set<ViewCameraDevice> viewCameraDeviceSet = new HashSet<>();
            for (CameraDevice cameraDevice : cameraDeviceSet) {
                ViewCameraDevice viewCameraDevice = new ViewCameraDevice();
                BeanUtils.copyProperties(cameraDevice, viewCameraDevice);
                viewCameraDevice.setBusinessDeviceId(businessDeviceId);
                viewCameraDeviceSet.add(viewCameraDevice);
            }
            viewBusinessDevice.setViewCameraDevices(viewCameraDeviceSet);
        }
        return viewBusinessDevice;
    }

    /**
     * 查询所有业务设备信息
     * @return 业务信息列表
     */
    @Override
    public List<ViewBusinessDevice> getAllBusinessDevices(Integer pageSize,Integer pageNum) {
        if(pageSize == null || pageNum == null){
            return null;
        }
        Integer offset = pageSize*(pageNum-1);
        List<BusinessDevice> businessDeviceList = businessDeviceRepository.findAllBusinessDevicesByPage(offset,pageSize);
        List<ViewBusinessDevice> viewBusinessDeviceList = new ArrayList<>();
        for (BusinessDevice businessDevice : businessDeviceList) {
            ViewBusinessDevice viewBusinessDevice = new ViewBusinessDevice();
            BeanUtils.copyProperties(businessDevice, viewBusinessDevice);
            viewBusinessDeviceList.add(viewBusinessDevice);
        }
        return viewBusinessDeviceList;
    }

    /**
     * 更新业务设备
     * @param viewBusinessDevice 业务设备
     * @return
     */
    @Override
    public ViewBusinessDevice updateBusinessDevice(ViewBusinessDevice viewBusinessDevice) {
        BusinessDevice businessDevice = businessDeviceRepository.getOne(viewBusinessDevice.getId());
        if (businessDevice == null) {
            return null;
        }
        Date date = new Date();
        //更新业务设备
        System.err.println(viewBusinessDevice);
        BeanUtils.copyProperties(viewBusinessDevice, businessDevice);
        businessDevice.setUpdateTime(date);
        viewBusinessDevice.setUpdateTime(date);
        System.err.println(businessDevice);
        businessDeviceRepository.save(businessDevice);
        //更新关联定位设备
        ViewLocationDevice viewLocationDevice = viewBusinessDevice.getViewLocationDevice();
        if(!(viewLocationDevice == null)){
            System.err.println(viewLocationDevice);
            LocationDevice locationDevice = new LocationDevice();
            BeanUtils.copyProperties(viewLocationDevice, locationDevice);
            locationDevice.setUpdateTime(date);
            locationDevice.setBusinessDevice(businessDevice);
            System.err.println(locationDevice);
            viewLocationDevice.setUpdateTime(date);
            locationDeviceRepository.save(locationDevice);
        }
        //更新关联报警设备信息
        ViewReportDevice viewReportDevice = viewBusinessDevice.getViewReportDevice();
        if(!(viewReportDevice == null)){
            ReportDevice reportDevice = new ReportDevice();
            BeanUtils.copyProperties(viewReportDevice,reportDevice);
            reportDevice.setUpdateTime(date);
            reportDevice.setBusinessDevice(businessDevice);
            viewReportDevice.setUpdateTime(date);
            reportDeviceRepository.save(reportDevice);
        }
        //更新关联摄像头组信息
        Set<ViewCameraDevice> viewCameraDeviceSet = viewBusinessDevice.getViewCameraDevices();
        if(!viewCameraDeviceSet.isEmpty()){
            for(ViewCameraDevice viewCameraDevice:viewCameraDeviceSet){
                CameraDevice cameraDevice = new CameraDevice();
                BeanUtils.copyProperties(viewCameraDevice, cameraDevice);
                cameraDevice.setUpdateTime(date);
                cameraDevice.setBusinessDevice(businessDevice);
                viewCameraDevice.setUpdateTime(date);
                cameraDeviceRepository.save(cameraDevice);
            }
        }
        return viewBusinessDevice;
    }

}
