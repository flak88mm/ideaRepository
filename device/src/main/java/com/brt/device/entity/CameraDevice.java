package com.brt.device.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @ClassName: CameraDevice 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author Zeng Nan
 * @date 2019年10月14日 下午10:17:16
 */
@Entity
@Table(name = "T_CAMERA_DEVICE", schema = "BRTKPI")
public class CameraDevice {
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
    private Timestamp createTime;
    private Timestamp updateTime;

    @ManyToOne
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
