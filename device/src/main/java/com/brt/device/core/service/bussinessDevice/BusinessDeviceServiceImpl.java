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
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
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
     * 查询所有业务设备信息
     * @return 业务设备信息
     */
    @Override
    public List<ViewBusinessDevice> getAllBusinessDevices(Integer pageSize, Integer pageNum) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.Direction.ASC, "id");
                //(pageNum, pageSize, Sort.Direction.ASC, "id");
        Page<BusinessDevice> businessDevicePage = businessDeviceRepository.findAll(pageable);
        if (businessDevicePage == null){
            return null;
        }
        List<BusinessDevice> businessDeviceList = businessDevicePage.getContent();
        if(businessDeviceList == null || businessDeviceList.isEmpty()){
            return null;
        }
        List<ViewBusinessDevice> viewBusinessDeviceList = new ArrayList<>();
        for (BusinessDevice businessDevice:businessDeviceList){
            ViewBusinessDevice viewBusinessDevice = new ViewBusinessDevice();
            BeanUtils.copyProperties(businessDevice,viewBusinessDevice);
            viewBusinessDeviceList.add(viewBusinessDevice);
        }
        return viewBusinessDeviceList;
    }

    /**
     * 根据id查询业务设备及其关联设备信息
     * @param businessDeviceId 业务设备id
     * @return 业务设备及其关联设备信息
     */
    @Override
    public ViewBusinessDevice getBusinessDeviceByBusinessDeviceId(Long businessDeviceId) {
        Optional<BusinessDevice> businessDeviceOpt = businessDeviceRepository.findById(businessDeviceId);
        if (!businessDeviceOpt.isPresent()) {
            return null;
        }
        BusinessDevice businessDevice = businessDeviceOpt.get();
        ViewBusinessDevice viewBusinessDevice = new ViewBusinessDevice();
        BeanUtils.copyProperties(businessDevice, viewBusinessDevice);
        //查询关联定位设备
        LocationDevice locationDevice = locationDeviceRepository.findByBusinessDeviceId(businessDeviceId);
        if (!(locationDevice == null)) {
            ViewLocationDevice viewLocationDevice = new ViewLocationDevice();
            BeanUtils.copyProperties(locationDevice, viewLocationDevice);
            viewLocationDevice.setBusinessDeviceId(businessDeviceId);
            viewLocationDevice.setBusinessDeviceName(businessDevice.getName());
            viewBusinessDevice.setViewLocationDevice(viewLocationDevice);
        }
        //查询关联报警设备
        ReportDevice reportDevice = reportDeviceRepository.findByBusinessDeviceId(businessDeviceId);
        if (!(reportDevice == null)) {
            ViewReportDevice viewReportDevice = new ViewReportDevice();
            BeanUtils.copyProperties(reportDevice, viewReportDevice);
            viewReportDevice.setBusinessDeviceId(businessDeviceId);
            viewReportDevice.setBusinessDeviceName(businessDevice.getName());
            viewBusinessDevice.setViewReportDevice(viewReportDevice);
        }
        //查询关联摄像头设备组
        List<CameraDevice> cameraDeviceSet = cameraDeviceRepository.findByBusinessDeviceId(businessDeviceId);
        if (!(cameraDeviceSet == null) && !cameraDeviceSet.isEmpty()) {
            List<ViewCameraDevice> viewCameraDeviceSet = new ArrayList<>();
            for (CameraDevice cameraDevice : cameraDeviceSet) {
                ViewCameraDevice viewCameraDevice = new ViewCameraDevice();
                BeanUtils.copyProperties(cameraDevice, viewCameraDevice);
                viewCameraDevice.setBusinessDeviceId(businessDeviceId);
                viewCameraDevice.setBusinessDeviceName(businessDevice.getName());
                viewCameraDeviceSet.add(viewCameraDevice);
            }
            viewBusinessDevice.setViewCameraDevices(viewCameraDeviceSet);
        }
        return viewBusinessDevice;
    }

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
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        BusinessDevice businessDevice = new BusinessDevice();
        if(businessDeviceRepository.findByCode(viewBusinessDevice.getCode()) != null){
            viewBusinessDevice.setId(businessDeviceRepository.findByCode(viewBusinessDevice.getCode()).getId());
            return updateBusinessDeviceByBusinessDeviceId(viewBusinessDevice);
        }else{
            viewBusinessDevice.setCreateTime(timestamp);
            viewBusinessDevice.setUpdateTime(timestamp);
            BeanUtils.copyProperties(viewBusinessDevice, businessDevice);
            businessDeviceRepository.save(businessDevice);
            //该业务设备关联定位设备
            LocationDevice locationDevice = new LocationDevice();
            ViewLocationDevice viewLocationDevice = viewBusinessDevice.getViewLocationDevice();
            viewLocationDevice.setCreateTime(timestamp);
            viewLocationDevice.setUpdateTime(timestamp);
            BeanUtils.copyProperties(viewLocationDevice, locationDevice);
            locationDevice.setBusinessDevice(businessDevice);
            locationDeviceRepository.save(locationDevice);
            //更新vo中相关数据
            viewLocationDevice.setId(locationDevice.getId());
            viewLocationDevice.setBusinessDeviceId(businessDevice.getId());
            viewLocationDevice.setBusinessDeviceName(businessDevice.getName());
            //该业务设备关联报警设备
            ReportDevice reportDevice = new ReportDevice();
            ViewReportDevice viewReportDevice = viewBusinessDevice.getViewReportDevice();
            viewReportDevice.setCreateTime(new Timestamp(System.currentTimeMillis()));
            viewReportDevice.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            BeanUtils.copyProperties(viewReportDevice, reportDevice);
            reportDevice.setBusinessDevice(businessDevice);
            reportDeviceRepository.save(reportDevice);
            //更新vo中相关数据
            viewReportDevice.setId(reportDevice.getId());
            viewReportDevice.setBusinessDeviceId(businessDevice.getId());
            viewReportDevice.setBusinessDeviceName(businessDevice.getName());
            //该业务设备关联摄像头设备
            List<ViewCameraDevice> viewCameraDeviceSet = viewBusinessDevice.getViewCameraDevices();
            for (ViewCameraDevice viewCameraDevice : viewCameraDeviceSet) {
                CameraDevice cameraDevice = new CameraDevice();
                viewCameraDevice.setCreateTime(timestamp);
                viewCameraDevice.setUpdateTime(timestamp);
                BeanUtils.copyProperties(viewCameraDevice, cameraDevice);
                cameraDevice.setBusinessDevice(businessDevice);
                cameraDeviceRepository.save(cameraDevice);
                //更新vo中相关数据
                viewCameraDevice.setId(cameraDevice.getId());
                viewCameraDevice.setBusinessDeviceId(businessDevice.getId());
                viewCameraDevice.setBusinessDeviceName(businessDevice.getName());
            }
            viewBusinessDevice.setId(businessDevice.getId());
            return viewBusinessDevice;
        }
    }

    /**
     * 更新业务设备及其关联设备信息
     * @param viewBusinessDevice 业务设备及其关联设备信息
     * @return 业务设备信息
     */
    @Override
    @Transactional
    public ViewBusinessDevice updateBusinessDeviceByBusinessDeviceId(ViewBusinessDevice viewBusinessDevice) {
        BusinessDevice businessDevice = businessDeviceRepository.getOne(viewBusinessDevice.getId());
        if (businessDevice == null) {
            return null;
        }
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        //更新业务设备
        viewBusinessDevice.setUpdateTime(timestamp);
        BeanUtils.copyProperties(viewBusinessDevice, businessDevice);
        businessDeviceRepository.save(businessDevice);
        //更新关联定位设备
        ViewLocationDevice viewLocationDevice = viewBusinessDevice.getViewLocationDevice();
        if(viewLocationDevice == null){
            //现关联定位设备为空，删除原关联定位设备
            locationDeviceRepository.delete(businessDevice.getLocationDevice());
        }
        if(viewLocationDevice != null){
            if(businessDevice.getLocationDevice() != null &&
                    viewLocationDevice.getId() != businessDevice.getLocationDevice().getId()){
                //业务设备原关联定位设备id与现关联定位设备id不同，删除原关联定位设备
                locationDeviceRepository.delete(businessDevice.getLocationDevice());
            }
            LocationDevice locationDevice = new LocationDevice();
            viewLocationDevice.setUpdateTime(timestamp);
            if (viewLocationDevice.getId() == null){
                viewLocationDevice.setCreateTime(timestamp);
            }
            BeanUtils.copyProperties(viewLocationDevice, locationDevice);
            locationDevice.setBusinessDevice(businessDevice);
            locationDevice = locationDeviceRepository.save(locationDevice);
            //更新vo中相关数据
            viewLocationDevice.setId(locationDevice.getId());
            viewLocationDevice.setBusinessDeviceId(businessDevice.getId());
            viewLocationDevice.setBusinessDeviceName(businessDevice.getName());
            viewBusinessDevice.setViewLocationDevice(viewLocationDevice);
        }
        //更新关联报警设备信息
        ViewReportDevice viewReportDevice = viewBusinessDevice.getViewReportDevice();
        if(viewReportDevice == null){
            //现关联报警设备为空，删除原关联报警设备
            reportDeviceRepository.delete(businessDevice.getReportDevice());
        }
        if(viewReportDevice != null) {
            if(businessDevice.getLocationDevice() != null &&
                    viewReportDevice.getId() != businessDevice.getReportDevice().getId()){
                //业务设备原关联报警设备id与现关联报警设备id不同，删除原关联报警设备
                reportDeviceRepository.delete(businessDevice.getReportDevice());
            }
            ReportDevice reportDevice = new ReportDevice();
            viewReportDevice.setUpdateTime(timestamp);
            if(viewReportDevice.getId() == null){
                viewReportDevice.setCreateTime(timestamp);
            }
            BeanUtils.copyProperties(viewReportDevice, reportDevice);
            reportDevice.setBusinessDevice(businessDevice);
            reportDevice = reportDeviceRepository.save(reportDevice);
            //更新vo中相关数据
            viewReportDevice.setId(reportDevice.getId());
            viewReportDevice.setBusinessDeviceId(businessDevice.getId());
            viewReportDevice.setBusinessDeviceName(businessDevice.getName());
            viewBusinessDevice.setViewReportDevice(viewReportDevice);
        }
        //更新关联摄像头组信息
        List<ViewCameraDevice> viewCameraDeviceList = viewBusinessDevice.getViewCameraDevices();
        List<CameraDevice> oldCameraDeviceSet = cameraDeviceRepository.findByBusinessDeviceId(businessDevice.getId());
        Set<Long> newCameraDeviceIds = new HashSet<>();
        if(viewCameraDeviceList != null && !viewCameraDeviceList.isEmpty()){
            for(ViewCameraDevice viewCameraDevice:viewCameraDeviceList) {
                CameraDevice cameraDevice = new CameraDevice();
                viewCameraDevice.setUpdateTime(timestamp);
                if (viewCameraDevice.getId() == null){
                    viewCameraDevice.setCreateTime(timestamp);
                }
                BeanUtils.copyProperties(viewCameraDevice, cameraDevice);
                cameraDevice.setBusinessDevice(businessDevice);
                cameraDevice = cameraDeviceRepository.save(cameraDevice);
                //更新vo中相关数据
                viewCameraDevice.setId(cameraDevice.getId());
                viewCameraDevice.setBusinessDeviceId(businessDevice.getId());
                viewCameraDevice.setBusinessDeviceName(businessDevice.getName());
                newCameraDeviceIds.add(cameraDevice.getId());
            }
        }
        //删除多余的关联该业务设备的摄像头
        for(CameraDevice oldCameraDevice:oldCameraDeviceSet){
            if(!newCameraDeviceIds.contains(oldCameraDevice.getId())){
                cameraDeviceRepository.deleteById(oldCameraDevice.getId());
            }
        }
        return viewBusinessDevice;
    }

}
