package com.brt.device.entity;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Proxy(lazy = false)
@Table(name = "T_BUSINESS_DEVICE")
public class BusinessDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    private String reserve;
    private String detail;
    private String deviceType;
    private Date createTime;
    private Date updateTime;

    @OneToOne(mappedBy = "businessDevice")
    private LocationDevice locationDevice;

    @OneToOne(mappedBy = "businessDevice")
    private ReportDevice reportDevice;

    @OneToMany(mappedBy = "businessDevice",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<CameraDevice> cameraDevices;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReserve() {
        return reserve;
    }

    public void setReserve(String reserve) {
        this.reserve = reserve;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public LocationDevice getLocationDevice() {
        return locationDevice;
    }

    public void setLocationDevice(LocationDevice locationDevice) {
        this.locationDevice = locationDevice;
    }

    public ReportDevice getReportDevice() {
        return reportDevice;
    }

    public void setReportDevice(ReportDevice reportDevice) {
        this.reportDevice = reportDevice;
    }

    public Set<CameraDevice> getCameraDevices() {
        return cameraDevices;
    }

    public void setCameraDevices(Set<CameraDevice> cameraDevices) {
        this.cameraDevices = cameraDevices;
    }

    @Override
    public String toString() {
        return "BusinessDevice{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", reserve='" + reserve + '\'' +
                ", detail='" + detail + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", locationDevice=" + locationDevice +
                ", reportDevice=" + reportDevice +
                ", cameraDevices=" + cameraDevices +
                '}';
    }
}
