package com.brt.device.core.service.bussinessDevice;

import com.brt.device.entity.BusinessDevice;
import com.brt.device.entity.CameraDevice;
import com.brt.device.entity.LocationDevice;
import com.brt.device.entity.ReportDevice;
import com.brt.device.exception.CustomBusinessException;
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

/**
 * @ClassName: BusinessDeviceServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author Zeng Nan
 * @date 2019年10月14日 下午10:15:44
 */
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

    @Override
    public List<ViewBusinessDevice> getAllBusinessDevices(Integer pageSize, Integer pageNum) {
        if (pageSize == null || pageNum == null){
            return null;
        }
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.Direction.ASC, "id");
        Page<BusinessDevice> businessDevicePage = businessDeviceRepository.findAll(pageable);
        if (businessDevicePage == null){
            throw new CustomBusinessException("业务设备列表为空");
        }
        List<BusinessDevice> businessDeviceList = businessDevicePage.getContent();
        if(businessDeviceList == null || businessDeviceList.isEmpty()){
            throw new CustomBusinessException("业务设备列表为空");
        }
        List<ViewBusinessDevice> viewBusinessDeviceList = new ArrayList<>();
        for (BusinessDevice businessDevice:businessDeviceList){
            ViewBusinessDevice viewBusinessDevice = new ViewBusinessDevice();
            BeanUtils.copyProperties(businessDevice,viewBusinessDevice);
            viewBusinessDeviceList.add(viewBusinessDevice);
        }
        return viewBusinessDeviceList;
    }

    @Override
    public ViewBusinessDevice getBusinessDeviceByBusinessDeviceId(Long businessDeviceId) {
        if(businessDeviceId == null){
            return null;
        }
        Optional<BusinessDevice> businessDeviceOpt = businessDeviceRepository.findById(businessDeviceId);
        if (!businessDeviceOpt.isPresent()) {
            throw new CustomBusinessException("该业务设备不存在");
        }
        BusinessDevice businessDevice = businessDeviceOpt.get();
        ViewBusinessDevice viewBusinessDevice = new ViewBusinessDevice();
        BeanUtils.copyProperties(businessDevice, viewBusinessDevice);
        //Query associated location device
        LocationDevice locationDevice = locationDeviceRepository.findByBusinessDeviceId(businessDeviceId);
        if (!(locationDevice == null)) {
            ViewLocationDevice viewLocationDevice = new ViewLocationDevice();
            BeanUtils.copyProperties(locationDevice, viewLocationDevice);
            viewLocationDevice.setBusinessDeviceId(businessDeviceId);
            viewLocationDevice.setBusinessDeviceName(businessDevice.getName());
            viewBusinessDevice.setViewLocationDevice(viewLocationDevice);
        }
        //Query associated report device
        ReportDevice reportDevice = reportDeviceRepository.findByBusinessDeviceId(businessDeviceId);
        if (!(reportDevice == null)) {
            ViewReportDevice viewReportDevice = new ViewReportDevice();
            BeanUtils.copyProperties(reportDevice, viewReportDevice);
            viewReportDevice.setBusinessDeviceId(businessDeviceId);
            viewReportDevice.setBusinessDeviceName(businessDevice.getName());
            viewBusinessDevice.setViewReportDevice(viewReportDevice);
        }
        //Query associated camera device group
        List<CameraDevice> cameraDeviceList = cameraDeviceRepository.findByBusinessDeviceId(businessDeviceId);
        if (!(cameraDeviceList == null) && !cameraDeviceList.isEmpty()) {
            List<ViewCameraDevice> viewCameraDeviceSet = new ArrayList<>();
            for (CameraDevice cameraDevice : cameraDeviceList) {
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

    @Override
    @Transactional
    public ViewBusinessDevice createBusinessDevice(ViewBusinessDevice viewBusinessDevice) {
        if(viewBusinessDevice == null){
            return null;
        }
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        BusinessDevice businessDevice = new BusinessDevice();
        if(businessDeviceRepository.findByCode(viewBusinessDevice.getCode()) != null){
            //Business device code already exists, update business device information and other device information
            viewBusinessDevice.setId(businessDeviceRepository.findByCode(viewBusinessDevice.getCode()).getId());
            return updateBusinessDeviceByBusinessDeviceId(viewBusinessDevice);
        }else{
            viewBusinessDevice.setCreateTime(timestamp);
            BeanUtils.copyProperties(viewBusinessDevice, businessDevice);
            businessDevice = businessDeviceRepository.save(businessDevice);
            viewBusinessDevice.setId(businessDevice.getId());
            ViewLocationDevice viewLocationDevice = viewBusinessDevice.getViewLocationDevice();
            if(viewLocationDevice != null){
                //The business device associated location device
                LocationDevice locationDevice = new LocationDevice();
                viewLocationDevice.setCreateTime(timestamp);
                BeanUtils.copyProperties(viewLocationDevice, locationDevice);
                locationDevice.setBusinessDevice(businessDevice);
                locationDeviceRepository.save(locationDevice);
                //Update data in VO
                viewLocationDevice.setId(locationDevice.getId());
                viewLocationDevice.setBusinessDeviceId(businessDevice.getId());
                viewLocationDevice.setBusinessDeviceName(businessDevice.getName());
            }
            ViewReportDevice viewReportDevice = viewBusinessDevice.getViewReportDevice();
            if(viewReportDevice != null){
                //The business device associated report device
                ReportDevice reportDevice = new ReportDevice();
                viewReportDevice.setCreateTime(timestamp);
                BeanUtils.copyProperties(viewReportDevice, reportDevice);
                reportDevice.setBusinessDevice(businessDevice);
                reportDeviceRepository.save(reportDevice);
                //Update data in VO
                viewReportDevice.setId(reportDevice.getId());
                viewReportDevice.setBusinessDeviceId(businessDevice.getId());
                viewReportDevice.setBusinessDeviceName(businessDevice.getName());
            }
            List<ViewCameraDevice> viewCameraDeviceList = viewBusinessDevice.getViewCameraDevices();
            if(viewCameraDeviceList !=null && !viewCameraDeviceList.isEmpty()){
                //The business device associated camera device
                for (ViewCameraDevice viewCameraDevice : viewCameraDeviceList) {
                    CameraDevice cameraDevice = new CameraDevice();
                    viewCameraDevice.setCreateTime(timestamp);
                    BeanUtils.copyProperties(viewCameraDevice, cameraDevice);
                    cameraDevice.setBusinessDevice(businessDevice);
                    cameraDeviceRepository.save(cameraDevice);
                    //Update data in VO
                    viewCameraDevice.setId(cameraDevice.getId());
                    viewCameraDevice.setBusinessDeviceId(businessDevice.getId());
                    viewCameraDevice.setBusinessDeviceName(businessDevice.getName());
                }
            }
            return viewBusinessDevice;
        }
    }

    @Override
    @Transactional
    public ViewBusinessDevice updateBusinessDeviceByBusinessDeviceId(ViewBusinessDevice viewBusinessDevice) {
        if(viewBusinessDevice == null){
            return null;
        }
        BusinessDevice businessDevice = businessDeviceRepository.getOne(viewBusinessDevice.getId());
        if (businessDevice == null) {
            throw new CustomBusinessException("该业务设备不存在");
        }
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        //Update business device
        viewBusinessDevice.setUpdateTime(timestamp);
        BeanUtils.copyProperties(viewBusinessDevice, businessDevice);
        businessDeviceRepository.save(businessDevice);
        //Update associated location device
        ViewLocationDevice viewLocationDevice = viewBusinessDevice.getViewLocationDevice();
        if(viewLocationDevice == null && businessDevice.getLocationDevice() != null){
            //The current associated location device is empty, the original associated location device exists
            locationDeviceRepository.delete(businessDevice.getLocationDevice());
        }
        if(viewLocationDevice != null){
            if(businessDevice.getLocationDevice() != null &&
                    viewLocationDevice.getId() != businessDevice.getLocationDevice().getId()){
                //The original associated location device ID is different from the current associated location device ID
                locationDeviceRepository.delete(businessDevice.getLocationDevice());
            }
            LocationDevice locationDevice = new LocationDevice();
            if (viewLocationDevice.getId() == null){
                viewLocationDevice.setCreateTime(timestamp);
            }else{
                viewLocationDevice.setUpdateTime(timestamp);
            }
            BeanUtils.copyProperties(viewLocationDevice, locationDevice);
            locationDevice.setBusinessDevice(businessDevice);
            locationDevice = locationDeviceRepository.save(locationDevice);
            //Update data in VO
            viewLocationDevice.setId(locationDevice.getId());
            viewLocationDevice.setBusinessDeviceId(businessDevice.getId());
            viewLocationDevice.setBusinessDeviceName(businessDevice.getName());
            viewBusinessDevice.setViewLocationDevice(viewLocationDevice);
        }
        //Update associated report device
        ViewReportDevice viewReportDevice = viewBusinessDevice.getViewReportDevice();
        if(viewReportDevice == null && businessDevice.getReportDevice() != null){
            //The current associated report device is empty, the original associated report device exists
            reportDeviceRepository.delete(businessDevice.getReportDevice());
        }
        if(viewReportDevice != null) {
            if(businessDevice.getLocationDevice() != null &&
                    viewReportDevice.getId() != businessDevice.getReportDevice().getId()){
                //The original associated report device ID is different from the current associated report device ID
                reportDeviceRepository.delete(businessDevice.getReportDevice());
            }
            ReportDevice reportDevice = new ReportDevice();
            if(viewReportDevice.getId() == null){
                viewReportDevice.setCreateTime(timestamp);
            }else{
                viewReportDevice.setUpdateTime(timestamp);
            }
            BeanUtils.copyProperties(viewReportDevice, reportDevice);
            reportDevice.setBusinessDevice(businessDevice);
            reportDevice = reportDeviceRepository.save(reportDevice);
            //Update data in VO
            viewReportDevice.setId(reportDevice.getId());
            viewReportDevice.setBusinessDeviceId(businessDevice.getId());
            viewReportDevice.setBusinessDeviceName(businessDevice.getName());
            viewBusinessDevice.setViewReportDevice(viewReportDevice);
        }
        //Update associated camera device group
        List<ViewCameraDevice> viewCameraDeviceList = viewBusinessDevice.getViewCameraDevices();
        List<CameraDevice> oldCameraDeviceList = cameraDeviceRepository.findByBusinessDeviceId(businessDevice.getId());
        Set<Long> newCameraDeviceIds = new HashSet<>();
        if(viewCameraDeviceList != null && !viewCameraDeviceList.isEmpty()){
            for(ViewCameraDevice viewCameraDevice:viewCameraDeviceList) {
                CameraDevice cameraDevice = new CameraDevice();
                if (viewCameraDevice.getId() == null){
                    viewCameraDevice.setCreateTime(timestamp);
                }else{
                    viewCameraDevice.setUpdateTime(timestamp);
                }
                BeanUtils.copyProperties(viewCameraDevice, cameraDevice);
                cameraDevice.setBusinessDevice(businessDevice);
                cameraDevice = cameraDeviceRepository.save(cameraDevice);
                //Update data in VO
                viewCameraDevice.setId(cameraDevice.getId());
                viewCameraDevice.setBusinessDeviceId(businessDevice.getId());
                viewCameraDevice.setBusinessDeviceName(businessDevice.getName());
                newCameraDeviceIds.add(cameraDevice.getId());
            }
        }
        //Remove redundant associated camera devices
        for(CameraDevice oldCameraDevice:oldCameraDeviceList){
            if(!newCameraDeviceIds.contains(oldCameraDevice.getId())){
                cameraDeviceRepository.deleteById(oldCameraDevice.getId());
            }
        }
        return viewBusinessDevice;
    }

    @Override
//    @Transactional
    public boolean deleteBusinessDevice(Long businessDeviceId) {
        if(businessDeviceId == null){
            return false;
        }
        Optional<BusinessDevice> businessDeviceOpt = businessDeviceRepository.findById(businessDeviceId);
        if (!businessDeviceOpt.isPresent()) {
            throw new CustomBusinessException("该业务设备不存在");
        }
        BusinessDevice businessDevice = businessDeviceOpt.get();
        LocationDevice locationDevice = businessDevice.getLocationDevice();
        if (locationDevice != null){
            locationDeviceRepository.deleteById(locationDevice.getId());
        }
        ReportDevice reportDevice = businessDevice.getReportDevice();
        if(reportDevice != null){
            reportDeviceRepository.deleteById(reportDevice.getId());
        }
        Set<CameraDevice> cameraDeviceSet = businessDevice.getCameraDevices();
        if (cameraDeviceSet != null && !cameraDeviceSet.isEmpty()){
            for (CameraDevice cameraDevice:cameraDeviceSet){
                cameraDeviceRepository.deleteById(cameraDevice.getId());
            }
        }
        businessDeviceRepository.deleteById(businessDeviceId);
        Optional<BusinessDevice> businessDeviceRes = businessDeviceRepository.findById(businessDeviceId);
        if (!businessDeviceRes.isPresent()) {
            return false;
        }
        return true;
    }

}
