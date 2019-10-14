package com.brt.device.core.service.bussinessDevice;

import com.brt.device.rest.vo.ViewBusinessDevice;

import java.util.List;

public interface BusinessDeviceService {
    boolean deleteBusinessDevice(Long id);

    ViewBusinessDevice createBusinessDevice(ViewBusinessDevice viewBusinessDevice);

    ViewBusinessDevice getBusinessDeviceByBusinessDeviceId(Long businessDeviceId);

    List<ViewBusinessDevice> getAllBusinessDevices(Integer pageSize, Integer pageNum);

    ViewBusinessDevice updateBusinessDeviceByBusinessDeviceId(ViewBusinessDevice viewBusinessDevice);
}
