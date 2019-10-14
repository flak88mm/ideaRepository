package com.brt.device.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "T_REPORT_DEVICE", schema = "BRTKPI")
public class ReportDevice {
    @Id
    @GeneratedValue(strategy =GenerationType.SEQUENCE , generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator", initialValue = 1, allocationSize = 1,
            sequenceName = "SEQ_REPORT_DEVICE")
    private Long id;
    private String code;
    private String name;
    private String reserve;
    private String detail;
    private Timestamp createTime;
    private Timestamp updateTime;

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

    public BusinessDevice getBusinessDevice() {
        return businessDevice;
    }

    public void setBusinessDevice(BusinessDevice businessDevice) {
        this.businessDevice = businessDevice;
    }

}
