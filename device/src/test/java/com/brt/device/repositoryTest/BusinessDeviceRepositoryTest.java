package com.brt.device.repositoryTest;

import com.brt.device.entity.BusinessDevice;
import com.brt.device.entity.CameraDevice;
import com.brt.device.entity.LocationDevice;
import com.brt.device.entity.ReportDevice;
import com.brt.device.repository.BusinessDeviceRepository;
import com.brt.device.repository.CameraDeviceRepository;
import com.brt.device.repository.LocationDeviceRepository;
import com.brt.device.repository.ReportDeviceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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

    @Test
    public void testForGit(){
        System.err.println("hello git");
    }
    @Test
    public void page(){
        List<BusinessDevice> list = businessDeviceRepository.findAllBusinessDevicesByPage(2,2);
        for(BusinessDevice businessDevice:list){
            System.err.println(businessDevice.getId());
        }
    }
    @Test
    public void createBusinessDevice(){
        BusinessDevice businessDevice = new BusinessDevice();
        businessDevice.setId(1L);
        businessDevice.setCode("90001");
        businessDevice.setName("热式定位业务设备");
        businessDevice.setDetail("什么都有！");
        businessDevice.setDeviceType("1");
        businessDevice.setCreateTime(new Date());
        businessDevice.setUpdateTime(new Date());

//        System.err.println(businessDevice);
        businessDeviceRepository.save(businessDevice);
        System.err.println(businessDevice);
    }

    @Test
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
        locationDevice.setCreateTime(new Date());
        locationDevice.setUpdateTime(new Date());

        BusinessDevice businessDevice = businessDeviceRepository.findById(36L).get();
        locationDevice.setBusinessDevice(businessDevice);
        locationDeviceRepository.save(locationDevice);
        System.err.println("success!");
    }
    @Test
/*    @Transactional*/
    public void createCameraDevice(){
        CameraDevice cameraDevice = new CameraDevice();
        cameraDevice.setCode("10003");
        cameraDevice.setName("东热式摄像头1号");
        cameraDevice.setDetail("什么都有！");
        cameraDevice.setCreateTime(new Date());
        cameraDevice.setUpdateTime(new Date());

        BusinessDevice businessDevice = businessDeviceRepository.findById(1L).get();
        cameraDevice.setBusinessDevice(businessDevice);
        cameraDeviceRepository.save(cameraDevice);
        System.err.println(cameraDevice);
    }

    @Test
    public void optionalTest(){
        Optional<Integer> opt = Optional.of(1);
        System.err.println(opt.get());
    }
}
