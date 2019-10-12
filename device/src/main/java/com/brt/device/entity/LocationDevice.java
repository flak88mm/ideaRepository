package com.brt.device.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "T_LOCATION_DEVICE")
public class LocationDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    private String detail;
    private Date createTime;
    private Date updateTime;

    @OneToOne
    @JoinColumn(name = "BUSINESS_DEVICE_ID", nullable = false)
    private BusinessDevice businessDevice;

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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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

    public BusinessDevice getBusinessDevice() {
        return businessDevice;
    }

    public void setBusinessDevice(BusinessDevice businessDevice) {
        this.businessDevice = businessDevice;
    }

    @Override
    public String toString() {
        return "LocationDevice{" +
                "id=" + id +
                ", code=" + code +
                ", name='" + name + '\'' +
                ", detail='" + detail + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                /*", businessDevice=" + businessDevice +*/
                '}';
    }
}
