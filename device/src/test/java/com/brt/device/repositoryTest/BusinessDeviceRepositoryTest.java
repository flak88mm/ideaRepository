package com.brt.device.repositoryTest;

import com.brt.device.core.service.bussinessDevice.BusinessDeviceService;
import com.brt.device.entity.BusinessDevice;
import com.brt.device.entity.CameraDevice;
import com.brt.device.entity.LocationDevice;
import com.brt.device.entity.ReportDevice;
import com.brt.device.repository.BusinessDeviceRepository;
import com.brt.device.repository.CameraDeviceRepository;
import com.brt.device.repository.LocationDeviceRepository;
import com.brt.device.repository.ReportDeviceRepository;
import com.brt.device.rest.vo.ViewBusinessDevice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BusinessDeviceRepositoryTest {
    @Autowired
    BusinessDeviceRepository businessDeviceRepository;
    @Autowired
    CameraDeviceRepository cameraDeviceRepository;
    @Autowired
    LocationDeviceRepository locationDeviceRepository;
    @Autowired
    ReportDeviceRepository reportDeviceRepository;

    @Autowired
    BusinessDeviceService businessDeviceService;

    @Test
    public void testForGit(){
        System.err.println("原始版本 1701");
    }

    @Test
    @Transactional
    public void test(){
        List<ViewBusinessDevice> list = businessDeviceService.getAllBusinessDevices(1,1);
        for(ViewBusinessDevice viewBusinessDevice:list){
            System.err.println(viewBusinessDevice);
        }
    }

    @Test
    public void createBusinessDevice(){
        BusinessDevice businessDevice = new BusinessDevice();
        businessDevice.setCode("10002");
        businessDevice.setName("厌战式业务设备");
        businessDevice.setDetail("什么都有！");
        businessDevice.setDeviceType("1");
        businessDevice.setCreateTime(new Timestamp(System.currentTimeMillis()));
        businessDevice.setUpdateTime(new Timestamp(System.currentTimeMillis()));

        businessDeviceRepository.save(businessDevice);
        System.err.println("success!");
    }

    @Test
    @Transactional
    public void findBusinessDevice(){
        BusinessDevice businessDevice = new BusinessDevice();
        businessDevice = businessDeviceRepository.getOne(1L);
        System.err.println(businessDevice);

    }

    @Test
    public void createLocationDevice(){
        LocationDevice locationDevice = new LocationDevice();
        locationDevice.setCode("200011");
        locationDevice.setName("定位设备1号");
        locationDevice.setDetail("无描述");
        locationDevice.setCreateTime(new Timestamp(System.currentTimeMillis()));
        locationDevice.setUpdateTime(new Timestamp(System.currentTimeMillis()));

        BusinessDevice businessDevice = businessDeviceRepository.findById(1L).get();
        locationDevice.setBusinessDevice(businessDevice);
        locationDeviceRepository.save(locationDevice);
        System.err.println("success!");
    }

    @Test
    public void createReportDevice(){
        ReportDevice reportDevice = new ReportDevice();
        reportDevice.setCode("300021");
        reportDevice.setName("报警设备1号");
        reportDevice.setDetail("报警了！");
        reportDevice.setCreateTime(new Timestamp(System.currentTimeMillis()));
        reportDevice.setUpdateTime(new Timestamp(System.currentTimeMillis()));

        BusinessDevice businessDevice = businessDeviceRepository.findById(21L).get();
        reportDevice.setBusinessDevice(businessDevice);
        reportDeviceRepository.save(reportDevice);
        System.err.println("success!");
    }

    @Test
    public void createCameraDevice(){
        CameraDevice cameraDevice = new CameraDevice();
        cameraDevice.setCode("40011");
        cameraDevice.setName("tora式摄像头1号");
        cameraDevice.setDetail("什么都有！");
        cameraDevice.setCreateTime(new Timestamp(System.currentTimeMillis()));
        cameraDevice.setUpdateTime(new Timestamp(System.currentTimeMillis()));

        BusinessDevice businessDevice = businessDeviceRepository.findById(1L).get();
        cameraDevice.setBusinessDevice(businessDevice);
        cameraDeviceRepository.save(cameraDevice);
        System.err.println("success!");
    }

    @Test
    public void delete(){
        cameraDeviceRepository.deleteById(47L);
    }

    @Test
    public void optionalTest(){
        Optional<Integer> opt = Optional.of(1);
        System.err.println(opt.get());
    }
}
