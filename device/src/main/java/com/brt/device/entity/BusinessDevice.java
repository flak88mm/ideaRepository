package com.brt.device.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

/**
 * @ClassName: BusinessDevice 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author Zeng Nan
 * @date 2019年10月14日 下午10:17:09
 */
@Entity
@Table(name = "T_BUSINESS_DEVICE", schema = "BRTKPI")
@Proxy(lazy = false)
public class BusinessDevice {
    @Id
/*    @GeneratedValue(generator = "sequenceGenerator")
    @GenericGenerator(
            name = "sequenceGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {@Parameter(name = "sequence_name", value = "SEQ_T_BUSINESS_DEVICE")}
    )*/
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    private String reserve;
    private String detail;
    private String deviceType;
    private Timestamp createTime;
    private Timestamp updateTime;

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

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
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

}
